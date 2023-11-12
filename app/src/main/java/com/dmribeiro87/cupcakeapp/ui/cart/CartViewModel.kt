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
import kotlinx.coroutines.launch
import java.util.UUID

class CartViewModel(private val repository: CupcakeRepository) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>(emptyList())
    val orders: LiveData<List<Order>> = _orders
    private val selectedCupcakes = mutableListOf<Cupcake>()
    private val _currentSelection = MutableLiveData<List<Cupcake>>()



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

    fun removeCupcakes(cupcake: Cupcake, orderId: String){
        repository.removeCupcakeFromOrder(orderId, cupcake)
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