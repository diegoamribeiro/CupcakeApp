package com.dmribeiro87.cupcakeapp

import android.util.Log
import com.dmribeiro87.model.Cupcake
import com.dmribeiro87.model.Order
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CupcakeRepository {

    private val db: FirebaseFirestore = Firebase.firestore

    suspend fun addCupcake(cupcake: Cupcake) {
        try {
            db.collection("cupcakes")
                .add(cupcake)
                .await()
            println("Cupcake adicionado com sucesso!")
        } catch (e: Exception) {
            println("Erro ao adicionar cupcake: ${e.message}")
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

    fun createNewOrder(userId: String, order: Order) {
        db.collection("orders")
            .document(userId)
            .set(order)
            .addOnSuccessListener {
                // Trate a criação bem-sucedida
            }
            .addOnFailureListener {
                // Trate o erro
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



}