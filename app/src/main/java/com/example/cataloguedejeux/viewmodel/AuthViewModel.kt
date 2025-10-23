package com.example.cataloguedejeux.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel responsable de la gestion de l'état d'authentification de l'utilisateur.
 * Il expose l'utilisateur actuellement connecté et fournit une méthode pour se déconnecter.
 */
class AuthViewModel : ViewModel() {

    // _user est un `MutableStateFlow` qui contient l'utilisateur Firebase actuellement connecté.
    // Un StateFlow est un flux d'état qui émet la valeur actuelle à de nouveaux observateurs,
    // ce qui est parfait pour représenter un état qui change dans le temps (connecté/déconnecté).
    private val _user = MutableStateFlow(Firebase.auth.currentUser)
    
    // Expose le flux en tant que `StateFlow` en lecture seule pour que l'interface utilisateur l'observe.
    val user = _user.asStateFlow()

    /**
     * Déconnecte l'utilisateur de Firebase et met à jour l'état interne.
     * La mise à jour de la valeur de `_user` notifiera les observateurs (comme l'UI)
     * que l'utilisateur s'est déconnecté.
     */
    fun signOut() {
        Firebase.auth.signOut()
        _user.value = null
    }
}
