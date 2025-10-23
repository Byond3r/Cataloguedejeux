package com.example.cataloguedejeux.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cataloguedejeux.Game

/**
 * L'écran qui affiche les détails d'un jeu spécifique.
 * Il présente une grande image du jeu, son titre, sa description complète et un bouton de retour.
 * @param game L'objet `Game` contenant les informations à afficher.
 * @param navController Le contrôleur de navigation pour gérer le retour à l'écran précédent.
 */
@Composable
fun GameDetailsScreen(game: Game, navController: NavController) {
    // `Surface` est un conteneur qui peut être stylisé (couleur, élévation, etc.).
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // La colonne principale est rendue déroulante grâce à `verticalScroll`.
        // Cela garantit que tout le contenu est accessible, même sur de petits écrans.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Conteneur pour l'image en en-tête.
            Box {
                // Affiche l'image du jeu depuis une URL en utilisant la bibliothèque Coil.
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(game.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = game.title,
                    contentScale = ContentScale.Crop, // Recadre l'image pour remplir la hauteur et la largeur
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp) // Hauteur fixe pour l'image d'en-tête
                )
            }
            // Colonne pour le contenu textuel, avec un padding pour ne pas coller aux bords.
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.headlineLarge, // Style de titre proéminent
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = game.description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify // Justifie le texte pour un aspect propre
                )
                Spacer(modifier = Modifier.height(32.dp))
                // Bouton pour revenir à l'écran précédent.
                Button(
                    onClick = { navController.popBackStack() }, // `popBackStack` revient en arrière dans la navigation
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Retour au catalogue")
                }
            }
        }
    }
}
