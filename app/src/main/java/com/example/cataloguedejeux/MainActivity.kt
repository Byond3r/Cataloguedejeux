package com.example.cataloguedejeux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cataloguedejeux.ui.NavGraph
import com.example.cataloguedejeux.ui.theme.CatalogueDeJeuxTheme
import kotlinx.coroutines.launch

/**
 * L'activité principale, le point d'entrée de l'application.
 * Elle configure la structure globale de l'interface utilisateur, y compris la barre de navigation
 * supérieure (TopAppBar), le tiroir de navigation latéral (Navigation Drawer) et le graphe de navigation
 * qui gère les différents écrans.
 */
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatalogueDeJeuxTheme {
                val navController = rememberNavController() // Contrôleur pour gérer la navigation
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // État du tiroir (ouvert/fermé)
                val scope = rememberCoroutineScope() // CoroutineScope pour lancer des actions asynchrones (ouvrir/fermer le tiroir)

                // Observe la pile de navigation pour connaître l'écran actuel.
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Condition pour n'afficher la structure principale (Scaffold, TopAppBar, Drawer) que
                // si l'utilisateur n'est pas sur l'écran d'authentification.
                if (currentRoute == "auth") {
                    // Affiche seulement le graphe de navigation (qui montrera AuthScreen)
                    NavGraph(navController = navController, paddingValues = PaddingValues())
                } else {
                    // Affiche la structure complète de l'application.
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            ModalDrawerSheet {
                                Spacer(modifier = Modifier.height(12.dp))
                                // Élément du tiroir pour accéder à l'écran "Compte"
                                NavigationDrawerItem(
                                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Compte") },
                                    label = { Text("Compte") },
                                    selected = currentRoute == "account", // Met l'élément en surbrillance s'il est actif
                                    onClick = { 
                                        navController.navigate("account") 
                                        scope.launch { drawerState.close() } // Ferme le tiroir après un clic
                                    },
                                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                )
                            }
                        }
                    ) {
                        // Scaffold est la structure de base d'un écran Material Design.
                        Scaffold(
                            topBar = {
                                // La barre de navigation en haut de l'écran.
                                TopAppBar(
                                    title = { Text("Catalogue de jeux") },
                                    navigationIcon = {
                                        // Icône pour ouvrir le tiroir de navigation.
                                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                                        }
                                    }
                                )
                            }
                        ) { innerPadding ->
                            // Le contenu principal de l'écran est le NavGraph.
                            // `innerPadding` est fourni par le Scaffold pour s'assurer que le contenu
                            // n'est pas caché par la TopAppBar.
                            NavGraph(navController = navController, paddingValues = innerPadding)
                        }
                    }
                }
            }
        }
    }
}