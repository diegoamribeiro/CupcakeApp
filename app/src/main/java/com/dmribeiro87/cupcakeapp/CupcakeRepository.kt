package com.dmribeiro87.cupcakeapp

import android.util.Log
import com.dmribeiro87.model.Cupcake
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
}