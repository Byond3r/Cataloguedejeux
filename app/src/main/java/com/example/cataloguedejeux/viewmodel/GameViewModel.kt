package com.example.cataloguedejeux.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cataloguedejeux.Game
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

/**
 * ViewModel responsable de la gestion des données des jeux.
 * Il se connecte à Firestore pour récupérer la liste des jeux en temps réel.
 */
class GameViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val _games = mutableStateOf<List<Game>>(emptyList())
    val games: List<Game> get() = _games.value

    init {
        db.collection("games").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("GameViewModel", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                _games.value = snapshot.toObjects()
            }
        }
    }

    /**
     * Récupère un jeu spécifique de la liste par son identifiant (ID).
     */
    fun getGameById(id: String): Game? {
        return games.find { it.id == id }
    }

    /**
     * Inverse le statut de lecture (lu/non lu) d'un jeu dans Firestore.
     * @param game Le jeu dont le statut doit être basculé.
     */
    fun toggleReadStatus(game: Game) {
        db.collection("games").document(game.id)
            .update("read", !game.read) // Inverse la valeur actuelle
            .addOnFailureListener { e -> 
                Log.w("GameViewModel", "Error updating document", e) 
            }
    }
}
