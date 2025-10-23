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

    // Référence à la base de données Firestore.
    private val db = Firebase.firestore

    // _games est une variable d'état privée qui contient la liste des jeux.
    private val _games = mutableStateOf<List<Game>>(emptyList())
    
    // `games` est une propriété publique qui expose la liste des jeux.
    val games: List<Game> get() = _games.value

    init {
        // `addSnapshotListener` est la clé de la mise à jour en temps réel.
        // Ce code s'exécute une première fois pour récupérer toutes les données, puis
        // se déclenche à chaque fois qu'une donnée est ajoutée, modifiée ou supprimée
        // dans la collection "games" de Firestore.
        db.collection("games").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("GameViewModel", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                // `toObjects<Game>()` convertit automatiquement les documents Firestore
                // en une liste d'objets `Game`.
                _games.value = snapshot.toObjects()
            }
        }
    }

    /**
     * Récupère un jeu spécifique de la liste par son identifiant (ID).
     * @param id L'identifiant unique du document Firestore.
     * @return Le jeu correspondant ou `null` s'il n'est pas trouvé.
     */
    fun getGameById(id: String): Game? {
        return games.find { it.id == id }
    }

    /**
     * Met à jour le statut d'un jeu à "Lu" directement dans Firestore.
     * @param gameId L'identifiant du document Firestore du jeu à mettre à jour.
     */
    fun markAsRead(gameId: String) {
        db.collection("games").document(gameId)
            .update("status", "Lu")
            .addOnSuccessListener { 
                Log.d("GameViewModel", "DocumentSnapshot successfully updated!") 
            }
            .addOnFailureListener { e -> 
                Log.w("GameViewModel", "Error updating document", e) 
            }
    }
}
