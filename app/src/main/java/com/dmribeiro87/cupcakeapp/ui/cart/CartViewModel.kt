package com.dmribeiro87.cupcakeapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmribeiro87.cupcakeapp.CupcakeRepository
import com.dmribeiro87.model.Address
import com.dmribeiro87.model.Client
import com.dmribeiro87.model.Cupcake
import com.dmribeiro87.model.Order
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import java.util.UUID

class CartViewModel(private val repository: CupcakeRepository) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>(emptyList())
    val orders: LiveData<List<Order>> = _orders
    private val selectedCupcakes = mutableListOf<Cupcake>()
    private val _currentSelection = MutableLiveData<List<Cupcake>>()
    private var currentOrderId: String? = null


    fun loadOrders() {
        viewModelScope.launch {
            repository.getAllOrders { ordersList ->
                _orders.value = ordersList
            }
        }
    }

    fun initializeOrAddCupcakeToSelection(cupcake: Cupcake) {
        selectedCupcakes.add(cupcake)
        _currentSelection.value = selectedCupcakes
    }

    fun addCupcakeToOrder(cupcake: Cupcake) {
        val orderId = "unique-order-of-the-galaxy"

        viewModelScope.launch {
            try {
                val existingOrder = repository.getOrderById(orderId)
                val orderCupcakes = existingOrder?.list?.toMutableList() ?: mutableListOf()
                orderCupcakes.add(cupcake)

                val newOrder = Order(
                    orderId = orderId,
                    list = orderCupcakes,
                    date = existingOrder?.date ?: Timestamp.now(),
                    client = existingOrder?.client ?: Client("", Address("", "", "", "", "", ""))
                )
                repository.createOrUpdateOrder(newOrder)
                loadOrders() // Recarrega os pedidos para refletir as mudanças
            } catch (e: Exception) {
                // Trate a exceção
            }
        }
    }

    fun updateOrderTotal(orderId: String, newTotal: Double) {
        viewModelScope.launch {
            try {
                val existingOrder = repository.getOrderById(orderId)
                existingOrder?.let { order ->
                    val updatedOrder = order.copy(total = newTotal)
                    repository.createOrUpdateOrder(updatedOrder)
                }
            } catch (e: Exception) {
                // Trate exceções
            }
        }
    }


    fun removeCupcakeFromOrder(cupcake: Cupcake) {
        val orderId = "unique-order-of-the-galaxy"

        viewModelScope.launch {
            try {
                val existingOrder = repository.getOrderById(orderId)
                existingOrder?.let {
                    // Encontra o índice do primeiro cupcake que corresponde ao productId
                    val cupcakeIndex = it.list.indexOfFirst { it.productId == cupcake.productId }
                    if (cupcakeIndex != -1) {
                        // Cria uma nova lista removendo o cupcake encontrado
                        val updatedCupcakes = ArrayList(it.list)
                        updatedCupcakes.removeAt(cupcakeIndex)
                        val updatedOrder = it.copy(list = updatedCupcakes)
                        repository.createOrUpdateOrder(updatedOrder)
                        loadOrders() // Recarregar os pedidos para refletir as mudanças
                    }
                }
            } catch (e: Exception) {
                // Trate a exceção
            }
        }
    }

    fun createOrderForCheckout() {
        // Se nenhum cupcake foi selecionado, não criamos um pedido
        if (selectedCupcakes.isNotEmpty()) {
            val newOrder = Order(
                orderId = UUID.randomUUID().toString(),
                list = selectedCupcakes.toList(),
                date = null,
                client = Client("", Address("", "", "", "", "", "")) // Cliente vazio por enquanto
            )
            repository.createOrUpdateOrder(newOrder)
            // Limpa a seleção após a criação do pedido
            selectedCupcakes.clear()
            _currentSelection.value = selectedCupcakes
        }
    }
}