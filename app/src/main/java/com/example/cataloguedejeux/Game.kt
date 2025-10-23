package com.example.cataloguedejeux

import com.google.firebase.firestore.DocumentId

/**
 * Classe de données représentant un jeu vidéo.
 * @property id L'identifiant unique du document Firestore.
 * @property title Le titre du jeu.
 * @property description La description du jeu.
 * @property imageUrl L'URL de l'image de couverture.
 * @property developer Le studio de développement du jeu.
 * @property editor L'éditeur du jeu.
 * @property read Un booléen indiquant si le jeu a été marqué comme lu.
 */
data class Game(
    @DocumentId val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val developer: String = "",
    val editor: String = "",
    val read: Boolean = false // Remplacement de status par un booléen
)
