package com.dmribeiro87.model

import java.io.Serializable
import java.util.Date

import com.google.firebase.Timestamp

data class Order(
    val orderId: String = "",
    val list: List<Cupcake> = emptyList(), // Agora com um valor padrão
    val date: Timestamp? = null, // Usando Timestamp do Firebase
    val client: Client? = null
) : Serializable

data class Address(
    val city: String = "",
    val state: String = "",
    val neighborhood: String = "",
    val street: String = "",
    val homeNumber: String = "",
    val reference: String= ""
) : Serializable

data class Cupcake(
    val productId: String = "",
    val name: String = "",
    val flavor: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val image: String = "",
    val weight: Int = 0
) : Serializable


data class Client(
    val name: String = "",
    val address: Address? = null
) : Serializable