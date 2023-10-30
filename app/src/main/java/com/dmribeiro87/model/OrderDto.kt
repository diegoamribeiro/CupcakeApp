package com.dmribeiro87.model

import java.io.Serializable
import java.util.Date

data class Order(
    val orderId: String,
    val list: List<Cupcake>,
    val date: Date,
    val client: Client
)

data class Address(
    val city: String,
    val state: String,
    val neighborhood: String,
    val street: String,
    val homeNumber: String,
    val reference: String
)

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
    val name: String,
    val address: Address
)