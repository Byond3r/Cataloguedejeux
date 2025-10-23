package com.example.cataloguedejeux

import com.google.firebase.firestore.DocumentId

/**
 * Une classe de données (`data class`) qui représente le modèle d'un jeu vidéo dans l'application.
 * Les `data class` sont une fonctionnalité de Kotlin conçue pour contenir des données.
 * Elles génèrent automatiquement des méthodes utiles comme `equals()`, `hashCode()`, `toString()` et `copy()`.
 * Le constructeur sans arguments est nécessaire pour que Firestore puisse désérialiser les documents
 * en objets `Game`.
 *
 * @property id L'identifiant unique du document Firestore.
 * @property title Le titre du jeu.
 * @property description Une courte description du jeu.
 * @property imageUrl L'URL de l'image de couverture du jeu.
 * @property status Le statut de lecture du jeu (par exemple, "Nouveau" ou "Lu").
 */
data class Game(
    @DocumentId val id: String = "", // L'ID vient du document Firestore
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val status: String = "Nouveau" // Valeur par défaut
)
