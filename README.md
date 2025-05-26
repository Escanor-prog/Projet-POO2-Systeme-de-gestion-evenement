# Location du Projet 
le projet Java se trouve dans la branche Master le rapport se trouve dans la branche Main
# Projet-POO2-Systeme-de-gestion-evenement
c'est mon projet final de POO
# Gestion d'Événements - Projet Java Swing

## Présentation

Cette application Java permet de **gérer des événements** (Concerts, Conférences) avec une interface graphique moderne réalisée en **Swing**.  
Elle propose la création, la modification, la suppression, l’annulation, la recherche et la gestion des participants, intervenants et organisateurs.  
Les données sont **persistées en JSON** grâce à Jackson.

---

## Fonctionnalités principales

- **Ajout d’événements** (Concert ou Conférence)
- **Ajout de participants** à un événement
- **Ajout dynamique d’intervenants** (pour les conférences)
- **Ajout dynamique d’organisateurs** (pour tous les événements)
- **Affichage des détails** d’un événement (participants, intervenants, organisateurs, etc.)
- **Modification** et **suppression** d’un événement
- **Annulation** d’un événement (avec notification des participants)
- **Recherche avancée** (par nom, lieu, type, avec filtres et Streams)
- **Persistance des données** en JSON (lecture/écriture)
- **Interface graphique moderne** (FlatLaf, fond dégradé, boutons stylisés, icônes)
- **Barre de statut** pour retour utilisateur

---

## Structure du projet

```
src/
  main/
    java/
      Gui/                  # Interfaces graphiques (Swing)
      Tp_Poo2_final_v2/
        Models/             # Modèles métier (Evenement, Concert, Conference, Participant, etc.)
        Services/           # Services (GestionEvenement, SerialisationJson, Notification)
        Observers/          # Interfaces Observer/Observable
    resources/              # Images, icônes, fichiers JSON
  test/
    java/                   # Tests unitaires JUnit
evenements.json             # Fichier de persistance des événements
```

---

## Lancement de l’application

1. **Prérequis** :
   - Java 17 ou supérieur (Java 24 recommandé)
   - Maven (pour la gestion des dépendances)

2. **Installation des dépendances** :
   - Les dépendances sont gérées via le fichier [`pom.xml`](pom.xml) (Jackson, FlatLaf, JUnit...).

3. **Exécution** :
   - Depuis votre IDE ou en ligne de commande :
     ```sh
     mvn compile
     mvn exec:java -Dexec.mainClass="Gui.MainGUI"
     ```
   - Ou lancez la classe `Gui.MainGUI` depuis votre IDE, il vous suffira de cliquer sur le run java ou bien sur le run qui apparait generalement sur vscode lorsque nous souhaitons faire un main

---

## Utilisation

### Menu principal

- **Ajouter un événement** : Créez un concert ou une conférence, avec intervenants et organisateurs.
- **Ajouter un participant** : Inscrivez un participant à un événement (avec vérification de doublon et capacité).
- **Afficher détails événement** : Consultez, modifiez ou supprimez un événement.
- **Annuler événement** : Annule un événement et notifie tous les participants (pattern Observer).
- **Rechercher événement** : Recherche par nom, lieu, type (avec Streams).
- **Quitter** : Ferme l’application.

### Dialogues

- **Champs obligatoires** en gras, boutons avec icônes et info-bulles.
- **Ajout dynamique** d’intervenants et d’organisateurs via des champs dédiés.
- **Validation** des doublons, capacité maximale, événements annulés, etc.

---

## Modèles principaux

- **Evenement** (abstrait)
  - `Concert` (artiste, genre)
  - `Conference` (thème, intervenants)
- **Participant**
- **Intervenant**
- **Organisateur**
- **GestionEvenement** (singleton)
- **SerialisationJson** (lecture/écriture JSON)
- **Pattern Observer** pour la notification des participants lors d’une annulation

---

## Persistance

- Les événements sont enregistrés dans le fichier `evenements.json` à chaque modification.
- Les participants, intervenants et organisateurs sont également sauvegardés.

---

## Tests

- Des tests unitaires sont présents dans `src/test/java/` pour valider :
  - L’ajout et la suppression de participants
  - La gestion de la capacité maximale
  - La notification lors de l’annulation
  - La sérialisation/désérialisation JSON
  - La recherche avancée avec Streams

---

## Personnalisation graphique

- **FlatLaf** pour un graphique moderne
- **Fond dégradé** sur la fenêtre principale
- **Boutons stylisés** avec icônes Unicode 
- **Barre de statut** pour retour utilisateur
- **Labels et boutons en gras** pour une meilleure lisibilité

---

## Conseils d’utilisation

- **Ne pas modifier manuellement** le fichier `evenements.json` pour éviter les erreurs de format.
- Pour ajouter des icônes personnalisées, placez vos images dans `src/main/resources` et adaptez le code des boutons.
- Pour toute modification du modèle, pensez à adapter la sérialisation JSON.

---

## Auteurs

- Projet réalisé par Passo Denny dans le cadre du module de Programmation Orientée Objet Avancée.

---

## Licence

Ce projet est fourni à des fins pédagogiques.  
Vous pouvez le réutiliser et l’adapter librement dans un cadre non commercial.

---
