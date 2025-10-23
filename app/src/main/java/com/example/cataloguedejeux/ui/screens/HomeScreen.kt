package com.example.cataloguedejeux.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cataloguedejeux.viewmodel.GameViewModel

/**
 * L'écran d'accueil, qui affiche le catalogue des jeux vidéo.
 * Il présente les jeux sous forme de liste de cartes cliquables.
 * @param navController Le contrôleur de navigation pour se déplacer vers d'autres écrans.
 * @param gameViewModel Le ViewModel qui fournit la liste des jeux et gère leur état.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, gameViewModel: GameViewModel = viewModel()) {
    // Récupère la liste des jeux depuis le ViewModel.
    // Comme cette liste est un état observé dans le ViewModel, cet écran se mettra à jour
    // automatiquement si la liste change (par exemple, si un statut est mis à jour).
    val games = gameViewModel.games

    // `LazyColumn` est l'équivalent de RecyclerView dans Compose. Il n'affiche que les éléments
    // visibles à l'écran, ce qui est très performant pour les longues listes.
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        // `items` est une fonction qui parcourt la liste et crée un composant pour chaque élément.
        items(games) { game ->
            // `Card` est un composant qui crée un conteneur avec une élévation (ombre) et des coins arrondis.
            Card(
                // L'action à effectuer lors d'un clic sur la carte.
                onClick = {
                    // 1. Marque le jeu comme "Lu" via le ViewModel.
                    gameViewModel.markAsRead(game.id)
                    // 2. Navigue vers l'écran de détails du jeu, en passant son ID dans la route.
                    navController.navigate("gameDetails/${game.id}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically // Centre les éléments verticalement
                ) {
                    // Affiche l'image du jeu en utilisant la bibliothèque Coil.
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(game.imageUrl)
                            .crossfade(true) // Effet de fondu lors du chargement
                            .build(),
                        contentDescription = game.title,
                        contentScale = ContentScale.Crop, // Recadre l'image pour remplir l'espace
                        modifier = Modifier
                            .size(60.dp) // Taille fixe pour l'image
                            .clip(RoundedCornerShape(8.dp)) // Coins arrondis pour l'image
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    // Colonne pour le titre et le statut.
                    Column(modifier = Modifier.weight(1f)) { // `weight(1f)` prend tout l'espace restant
                        Text(
                            text = game.title,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = game.status,
                            style = MaterialTheme.typography.bodyMedium,
                            // Change la couleur du statut s'il est "Lu".
                            color = if (game.status == "Lu") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}