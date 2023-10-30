package com.dmribeiro87.cupcakeapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmribeiro87.cupcakeapp.CupcakeRepository
import com.dmribeiro87.model.Cupcake
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CupcakeRepository) : ViewModel(){

    private val _cupcakes = MutableLiveData<List<Cupcake>>()
    val cupcakes: LiveData<List<Cupcake>> = _cupcakes

    fun addCupcake(cupcake: Cupcake) {
        viewModelScope.launch {
            repository.addCupcake(cupcake)
        }
    }

    suspend fun populateFirebaseWithCupcakes() {
        Log.d("***Check", "Passou")
        val cupcakes = listOf(
            Cupcake("1", "Cupcake de Chocolate", "Chocolate", 2.50, "Delicioso cupcake de chocolate", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/chocolate_cupcake.jpeg?alt=media&token=ae5e73d6-b03d-4915-b7ff-e328afabed1f&_gl=1*pyefce*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg1NDguOS4wLjA.", weight = 120),
            Cupcake("2", "Cupcake de Baunilha", "Baunilha", 2.50, "Saboroso cupcake de baunilha", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/vanilla_cupcake.jpeg?alt=media&token=4c59ee19-45ff-49f8-8599-f986676bd90a&_gl=1*vjytr8*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg4MDkuMTMuMC4w", weight = 120),
            Cupcake("3", "Cupcake de Pistache", "Pistache", 3.00, "Saboroso cupcake de pistache", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/pistache_cupcake.jpeg?alt=media&token=ec11b31c-fbe4-49fa-a4ee-50c36a2244cb&_gl=1*yawwnc*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg3NjIuNjAuMC4w", weight = 120),
            Cupcake("4", "Cupcake de Brownie", "Brownie", 4.00, "Saboroso cupcake de brownie", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/brownie_cupcake.jpeg?alt=media&token=b8c7665c-669b-482f-89dc-526689133874&_gl=1*1kwgo2m*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg1MTIuNDUuMC4w", weight = 130),
            Cupcake("5", "Cupcake de Nutella", "Nutella", 5.00, "Saboroso cupcake de nutella", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/nutella_cupcake.jpeg?alt=media&token=ceb36c6f-15f1-4692-a4eb-18a3881bfc44&_gl=1*1u3homq*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg3NDQuMTQuMC4w", weight = 150),
            Cupcake("6", "Cupcake de Leite Ninho", "Leite Ninho", 4.00, "Saboroso cupcake de leite ninho", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/milk_cupcake.jpeg?alt=media&token=531b050c-54be-4dfd-9d82-e5e329dc2f1d&_gl=1*1p24xy2*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg3MTguNDAuMC4w", weight = 120),
            Cupcake("7", "Cupcake de Frutas Vermelhas", "Frutas Vermelhas", 5.00, "Saboroso cupcake de frutas vermelhas", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/red_fruit_cupcake.jpeg?alt=media&token=11b66f5c-ee5a-4a14-9797-d70f61582ed7&_gl=1*1cnj6gl*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg3NzQuNDguMC4w", weight = 140),
            Cupcake("8", "Cupcake de Cappuccino", "Cappuccino", 4.00, "Saboroso cupcake de frutas vermelhas", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/capuccino_cupcake.jpeg?alt=media&token=73344abd-629c-423f-93c8-58862a73f7c1&_gl=1*1mjy953*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg1MzQuMjMuMC4w", weight = 140),
        )

        cupcakes.forEach { cupcake ->
            repository.addCupcake(cupcake)
        }
    }

    fun getCupcakes() {
        viewModelScope.launch {
            val cupcakes = repository.getCupcakes()
            _cupcakes.value = cupcakes
        }
    }
}