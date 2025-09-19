# FillGrid – Jeu de logique Android

## Description

**FillGrid** est un jeu de logique simple pour Android.  

**Objectif :** Remplir la grille dans le temps imparti en suivant cette règle :  
- Cliquer sur une case change sa couleur.  
- Les cases adjacentes changent également de couleur, si une case est déjà colorée elle redevient vierge.  
- Le but est de remplir toutes les cases de la grille.

**Fonctionnalités principales :**  
- Plusieurs niveaux de difficulté : Easy, Normal, Hard.  
- Jauge de temps pour chaque puzzle.  
- Possibilité de modifier la taille de la grille (dim x dim).  
- Import de grilles depuis un serveur PHP distant.  
- Stockage du **HighScore** et du nom du joueur via SharedPreferences.  
- Interface simple et responsive, avec images PNG pour les grilles ou boutons.

---

## Structure du projet

- `MainActivity.java` : logique du jeu, gestion du timer, HighScore et interaction des cases.  
- `Menu.java` : menu principal, choix de dimension et niveau, lancement du jeu.  
- `List.java` : affichage des grilles importées depuis le serveur.  
- `ImportGrille.java` / `ImportMatrix.java` : récupération des grilles via HTTP.  
- `HttpsTrustManager.java` : gestion SSL pour l’import des grilles.  
- `res/layout/` : fichiers XML pour les layouts des activités et fragments.  
- `build/` : contient les fichiers générés lors de la compilation (APK, classes compilées).

---

## Pré-requis

- Android Studio compatible avec **Gradle 8.3 / 8.5**.  
- SDK Android installé (API 21+ recommandé).   

> ⚠️ Le projet a été porté depuis une version Android de 2021. Certaines dépendances ou layouts peuvent nécessiter un **rebuild complet** lors de l’ouverture dans Android Studio récent.
> ⚠️ a partie import via le server web n'est plus disponible.

---

## Installation et utilisation

1. Ouvrir le projet dans Android Studio.  
2. Branche un appareil Android ou lance un émulateur.  
3. Clique sur **Run → Run 'app'**.  
4. L’application démarre avec le menu principal, où tu peux choisir la dimension et le niveau.

## Build Apk

- Un APK de l'application à été généré dans le dossier /generated pour tester l'application directement sur un appareil Android.

## Licence

- Projet pedagogique – usage libre pour consultation, tests et apprentissage.