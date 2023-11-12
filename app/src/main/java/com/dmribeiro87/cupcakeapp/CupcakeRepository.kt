package com.dmribeiro87.cupcakeapp

import android.util.Log
import com.dmribeiro87.model.Cupcake
import com.dmribeiro87.model.Order
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CupcakeRepository {

    private val db: FirebaseFirestore = Firebase.firestore

    suspend fun addCupcakeMocked(cupcake: Cupcake) {
        try {
            db.collection("cupcakes")
                .add(cupcake)
                .await()
            println("Cupcake adicionado com sucesso!")
        } catch (e: Exception) {
            println("Erro ao adicionar cupcake: ${e.message}")
        }
    }

    fun addCupcakeToOrder(cupcake: Cupcake, orderId: String) {
        val orderRef = db.collection("orders").document(orderId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(orderRef)
            val order = snapshot.toObject(Order::class.java)

            order?.let {
                val updatedCupcakes = ArrayList(it.list)
                updatedCupcakes.add(cupcake)
                transaction.update(orderRef, "list", updatedCupcakes)
            } ?: run {
                // Se o pedido não existe, cria um novo com o cupcake
                val newOrder = Order(orderId, listOf(cupcake), Timestamp.now(), null)
                transaction.set(orderRef, newOrder)
            }
        }.addOnSuccessListener {
            Log.d("CupcakeRepository", "Cupcake added successfully to order.")
        }.addOnFailureListener { e ->
            Log.e("CupcakeRepository", "Error adding cupcake to order.", e)
        }
    }

    suspend fun getCupcakes(): List<Cupcake> {
        return try {
            val snapshot = db.collection("cupcakes").get().await()
            val cupcakes = snapshot.toObjects(Cupcake::class.java)
            cupcakes
        } catch (e: Exception) {
            println("Erro ao recuperar cupcakes: ${e.message}")
            emptyList()
        }
    }

    suspend fun getOrderById(orderId: String): Order? {
        return try {
            val snapshot = db.collection("orders").document(orderId).get().await()
            snapshot.toObject(Order::class.java)
        } catch (e: Exception) {
            Log.e("CupcakeRepository", "Erro ao obter o pedido: ${e.message}")
            null
        }
    }


    fun createOrUpdateOrder(order: Order) {
        // Aqui você pode usar o ID do pedido como o documento ID
        db.collection("orders")
            .document(order.orderId)
            .set(order)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
                // Tratar erros, por exemplo, falha de conexão
            }
    }

    fun getAllOrders(onComplete: (List<Order>) -> Unit) {
        db.collection("orders")
            .get()
            .addOnSuccessListener { result ->
                val ordersList = result.mapNotNull { it.toObject(Order::class.java) }
                onComplete(ordersList)
            }
            .addOnFailureListener { e ->
                // Tratar exceção, por exemplo, falha de conexão ou query vazia
                onComplete(emptyList())
            }
    }

    fun removeCupcakeFromOrder(orderId: String, cupcake: Cupcake) {
        val orderRef = db.collection("orders").document(orderId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(orderRef)
            val order = snapshot.toObject(Order::class.java)
            order?.let {
                val updatedCupcakes = it.list.filterNot { it.productId == cupcake.productId }
                transaction.update(orderRef, "list", updatedCupcakes)
            }
        }.addOnSuccessListener {
            Log.d("CupcakeRepository", "Cupcake removed successfully from order.")
        }.addOnFailureListener { e ->
            Log.e("CupcakeRepository", "Error removing cupcake from order.", e)
        }
    }



    fun getOrderForUser(userId: String, callback: (Order?) -> Unit) {
        db.collection("orders")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val order = document.toObject(Order::class.java)
                    callback(order)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                // Trate o erro conforme necessário
                callback(null)
            }
    }

    fun addToExistingOrder(userId: String, cupcake: Cupcake) {
        db.collection("orders")
            .document(userId)
            .update("list", FieldValue.arrayUnion(cupcake))
            .addOnSuccessListener {
                // Trate a adição bem-sucedida
            }
            .addOnFailureListener {
                // Trate o erro
            }
    }



}