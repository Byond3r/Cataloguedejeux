# Catalogue de Jeux - Application Android

Ce projet est une application mobile Android développée en Kotlin avec Jetpack Compose. Elle permet aux utilisateurs de parcourir un catalogue de jeux vidéo, de consulter les détails de chaque jeu et de gérer leur compte utilisateur. L'application est connectée à Firebase pour l'authentification et utilise Firestore comme base de données en temps réel.

## 🚀 Fonctionnalités

- **Authentification Utilisateur :**
  - Inscription et connexion par e-mail et mot de passe.
  - Connexion simplifiée via Google Sign-In.
- **Catalogue de Jeux Dynamique :**
  - La liste des jeux est chargée en temps réel depuis la base de données Firestore.
  - Affiche le titre, le développeur, l'éditeur et une image pour chaque jeu.
  - Le statut de lecture ("Lu"/"Nouveau") est affiché avec un code couleur (Vert/Rouge).
- **Détails des Jeux :**
  - Affichage d'un écran de détails pour chaque jeu avec une grande image, le titre et une description complète.
- **Gestion de Statut Manuelle :**
  - Une checkbox sur l'écran de détails permet à l'utilisateur de marquer manuellement un jeu comme "Lu".
  - Le statut est sauvegardé et synchronisé en temps réel avec Firestore.
- **Gestion de Compte :**
  - Un écran "Compte" accessible depuis une barre de navigation latérale.
  - Affiche l'adresse e-mail de l'utilisateur connecté.
  - Permet la déconnexion.
- **Interface Moderne :**
  - Interface utilisateur entièrement construite avec Jetpack Compose, suivant les principes de Material Design 3.

## 🛠️ Technologies et Bibliothèques

- **Langage :** [Kotlin](https://kotlinlang.org/)
- **UI :** [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Architecture :** MVVM (Model-View-ViewModel)
- **Base de Données :** [Firebase Firestore](https://firebase.google.com/docs/firestore)
- **Authentification :** [Firebase Authentication](https://firebase.google.com/docs/auth)
- **Navigation :** [Jetpack Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
- **Chargement d'images :** [Coil](https://coil-kt.github.io/coil/)

## ⚙️ Installation et Configuration

Pour faire fonctionner ce projet sur votre machine, suivez ces étapes :

1.  **Cloner le dépôt**
    ```bash
    git clone https://votre-url-de-depot.git
    ```

2.  **Ouvrir dans Android Studio**
    - Lancez Android Studio et sélectionnez `Open an existing project`.
    - Naviguez jusqu'au dossier du projet cloné et ouvrez-le.

3.  **Configuration de Firebase**
    Ce projet nécessite une configuration Firebase pour fonctionner. Vous devez créer votre propre projet Firebase.

    - **Créer un projet Firebase :** Allez sur la [console Firebase](https://console.firebase.google.com/) et créez un nouveau projet.

    - **Ajouter une application Android :**
      - Dans le tableau de bord de votre projet, ajoutez une nouvelle application Android.
      - Utilisez le nom de package : `com.example.cataloguedejeux`.
      - Suivez les instructions pour générer et télécharger votre propre fichier **`google-services.json`**. Placez ce fichier dans le dossier **`app/`** de votre projet Android.

    - **Activer l'Authentification :**
      - Dans la console Firebase, allez dans la section `Authentication`.
      - Activez les fournisseurs de connexion suivants :
        - **E-mail/Mot de passe**
        - **Google** (assurez-vous de fournir l'empreinte de certificat SHA-1 de votre machine de développement).

    - **Mettre à jour l'ID Client Web :**
      - Dans les paramètres de l'authentification Google, vous trouverez un "ID client Web".
      - Copiez cet ID et collez-le dans le fichier `app/src/main/java/com/example/cataloguedejeux/ui/screens/AuthScreen.kt` à la place de la chaîne de caractères existante dans la méthode `.requestIdToken()`.

    - **Configurer Firestore :**
      - Allez dans la section `Firestore Database` et créez une nouvelle base de données en **mode test**.
      - **Important :** Allez dans l'onglet **Règles** et remplacez le contenu par ce qui suit pour permettre la lecture des jeux et l'écriture authentifiée :
        ```javascript
        rules_version = '2';
        service cloud.firestore {
          match /databases/{database}/documents {
            match /games/{gameId} {
              allow read: if true;
              allow write: if request.auth != null;
            }
          }
        }
        ```

4.  **Synchroniser et Lancer**
    - Après avoir effectué ces étapes, synchronisez votre projet avec Gradle dans Android Studio.
    - Lancez l'application sur un émulateur ou un appareil physique.

## 🗂️ Structure des Données Firestore

Pour que l'application fonctionne, vous devez créer une collection nommée `games` dans Firestore. Chaque document dans cette collection doit avoir la structure suivante :

- **Collection :** `games`
  - **Document :** (ID automatique)
    - **Champs :**
      - `title` (string)
      - `description` (string)
      - `imageUrl` (string)
      - `developer` (string)
      - `editor` (string)
      - `read` (boolean) - *valeur initiale : `false`*

---
*Ce README a été généré pour le projet Catalogue de Jeux.*
