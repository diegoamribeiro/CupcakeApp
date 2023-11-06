package com.dmribeiro87.cupcakeapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmribeiro87.cupcakeapp.CupcakeRepository
import com.dmribeiro87.model.Address
import com.dmribeiro87.model.Client
import com.dmribeiro87.model.Cupcake
import com.dmribeiro87.model.Order
import java.util.Date
import java.util.UUID

class DetailsViewModel(private val repository: CupcakeRepository): ViewModel() {


    // Lista temporária para armazenar cupcakes antes do checkout
    // Lista temporária para armazenar cupcakes antes do checkout
    private val selectedCupcakes = mutableListOf<Cupcake>()

    // LiveData para observação pela UI
    private val _currentSelection = MutableLiveData<List<Cupcake>>()
    val currentSelection: LiveData<List<Cupcake>> = _currentSelection

    // Inicializa ou adiciona um cupcake à seleção atual
    fun initializeOrAddCupcakeToSelection(cupcake: Cupcake) {
        selectedCupcakes.add(cupcake)
        _currentSelection.value = selectedCupcakes
    }

    // Função para criar um pedido com os cupcakes selecionados e um cliente vazio
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


    // Função para finalizar o pedido, que será chamada no checkout
//    fun finalizeOrder() {
//        currentOrder?.let { order ->
//            // Aqui você passaria o pedido para o repositório para ser salvo no Firebase
//            repository.createOrUpdateOrder(order)
//            // Após salvar, reset o pedido atual
//            currentOrder = null
//        }
    //}
}