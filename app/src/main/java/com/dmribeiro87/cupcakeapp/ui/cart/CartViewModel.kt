package com.dmribeiro87.cupcakeapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmribeiro87.cupcakeapp.CupcakeRepository
import com.dmribeiro87.model.Cupcake
import com.dmribeiro87.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CupcakeRepository) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>(emptyList())
    val orders: LiveData<List<Order>> = _orders



    // Chama o repositório para carregar as orders
    fun loadOrders() {
        viewModelScope.launch {
            repository.getAllOrders { ordersList ->
                _orders.value = ordersList
            }
        }
    }
}