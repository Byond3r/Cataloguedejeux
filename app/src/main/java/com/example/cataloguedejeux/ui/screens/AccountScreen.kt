package com.example.cataloguedejeux.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cataloguedejeux.viewmodel.AuthViewModel

/**
 * L'écran "Compte" qui affiche les informations de l'utilisateur connecté.
 * Il permet à l'utilisateur de voir son adresse e-mail, de se déconnecter ou de revenir en arrière.
 * @param navController Le contrôleur de navigation pour gérer les actions de retour et de déconnexion.
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AccountScreen(navController: NavController) {
    // Récupère l'instance du ViewModel d'authentification.
    val authViewModel: AuthViewModel = viewModel()
    // Récupère l'objet utilisateur depuis le ViewModel.
    val user = authViewModel.user.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center, // Centre le contenu verticalement
        horizontalAlignment = Alignment.CenterHorizontally // Centre le contenu horizontalement
    ) {
        if (user != null) {
            // Affiche l'adresse e-mail de l'utilisateur avec une icône.
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Email, contentDescription = "Email")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = user.email ?: "Email non disponible",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            // Bouton pour se déconnecter.
            Button(
                onClick = {
                    // Appelle la fonction de déconnexion du ViewModel.
                    authViewModel.signOut()
                    // Redirige l'utilisateur vers l'écran de connexion.
                    // `popUpTo("home") { inclusive = true }` efface l'historique de navigation
                    // jusqu'à l'écran d'accueil, pour que l'utilisateur ne puisse pas revenir en arrière.
                    navController.navigate("auth") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                // Utilise la couleur d'erreur du thème pour signaler une action importante.
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Déconnexion")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Se déconnecter")
            }
        } else {
            // Message affiché si aucun utilisateur n'est connecté.
            Text("Non connecté")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour revenir à l'écran précédent.
        Button(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Retour")
        }
    }
}