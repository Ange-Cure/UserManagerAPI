# UserAPI

## Introduction

Ce projet est une API REST développée avec Spring Boot. Elle permet de gérer des utilisateurs avec les fonctionnalités suivantes :
- Récupérer un utilisateur par son ID.
- Enregistrer un nouvel utilisateur.

## Prérequis

- Java 23
- Maven
- Postman (pour tester les endpoints)

## Installation

1. **Cloner le projet** :
   ```sh
   git clone https://github.com/Ange-Cure/UserManagerAPI.git
   cd UserManagerAPI
   ```

2. **Construire le projet avec Maven** :
   ```sh
   mvn clean install
   ```

3. **Lancer l'application** :
   ```sh
   java -jar target/Api_Users-0.0.1-SNAPSHOT.jar
   ```

## Endpoints

### 1. Récupérer un utilisateur par son ID

- **URL** : `http://localhost:8080/getUserById`
- **Méthode** : `GET`
- **Paramètres** :
   - `id` (int) : L'identifiant de l'utilisateur.

- **Exemple de requête** :
  `http://localhost:8080/getUserById?id=1`

### 2. Enregistrer un nouvel utilisateur

- **URL** : `http://localhost:8080/registerUser`
- **Méthode** : `GET`
- **Paramètres** :
   - `username` (string) : Le nom d'utilisateur.
   - `birthdate` (string) : La date de naissance (format `DD/MM/YYYY`).
   - `countryOfResidence` (string) : Le pays de résidence.
   - `phoneNumber` (string, optionnel) : Le numéro de téléphone.
   - `gender` (string, optionnel) : Le genre.

- **Exemple de requête** :
  `http://localhost:8080/registerUser?username=Thomas&birthdate=11/02/2005&countryOfResidence=France`

## Test des Endpoints avec Postman

Une collection Postman est disponible pour tester les endpoints de l'API. Vous pouvez importer cette collection dans Postman pour faciliter les tests.

### Importer la collection Postman

1. Ouvrez Postman.
2. Cliquez sur l'icône "Import" en haut à gauche.
3. Sélectionnez "Upload Files" et choisissez le fichier JSON de la collection (`UserAPI.postman_collection.json`).
4. Cliquez sur "Import".