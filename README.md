# 🌾 AgriCore

**AgriCore** est une application fullstack développée dans le cadre
d'une formation POEI.\
Le projet vise à proposer une **simulation de gestion agricole**
combinant :

-   une logique **ERP (gestion de ferme)**
-   une approche **gamifiée inspirée de Stardew Valley**

L'objectif est de produire une **démo fonctionnelle riche**, mettant en
avant des compétences en développement backend, frontend et DevOps.

------------------------------------------------------------------------

## 🚀 Objectifs du projet

-   Construire une application **fullstack complète**
-   Implémenter une **logique métier réaliste mais simplifiée**
-   Démontrer la maîtrise de :
    -   Java / Spring
    -   Angular
    -   Architecture REST
    -   CI/CD et conteneurisation

------------------------------------------------------------------------

## 🧠 Concept

Contrairement à un ERP classique basé sur de la saisie utilisateur,
AgriCore repose sur une **simulation métier dynamique** :

-   Les **animaux produisent automatiquement des ressources** (lait,
    œufs...)
-   L'**abattage** génère des ressources (viande)
-   Les **plantes évoluent dans le temps** (croissance + gestion de
    l'humidité)
-   L'utilisateur interagit avec des **objets métier concrets**, pas
    seulement des données

------------------------------------------------------------------------

## 🏗️ Architecture

### Backend

-   Java 21
-   Spring Boot
-   Spring Security
-   Hibernate / JPA
-   MySQL

Architecture en couches : Controller → Service → DAO → Base de données

------------------------------------------------------------------------

### Frontend

-   Angular

Interface hybride : - **ERP** : gestion des entités - **Jeu** :
représentation visuelle

------------------------------------------------------------------------

### DevOps

-   Docker
-   Jenkins
-   SonarQube
-   Kubernetes (optionnel)

------------------------------------------------------------------------

## 🧩 Modélisation métier

### 👤 Utilisateurs

-   Utilisateur (abstrait)
    -   Fermier
    -   Employé
    -   Client

### 🗺️ Zone

-   Entité centrale contenant :
    -   animaux
    -   plante (unique)
    -   véhicules
    -   ressources
    -   position (x, y, taille)

### 🌱 Plantes

-   Croissance en jours
-   Gestion de l'humidité (0--100)

### 🐄 Animaux

-   Production automatique de ressources
-   Abattage possible

### 📦 Ressources

-   Produits de la ferme (lait, œufs, viande...)
-   Quantité + unité

------------------------------------------------------------------------

## 🛒 Système de vente

-   Boutique simple
-   Mise en vente des ressources
-   Achat par des clients

------------------------------------------------------------------------

## 🔐 Sécurité

-   Spring Security
-   Rôles : Fermier, Employé, Client

------------------------------------------------------------------------

## 📡 API

-   REST
-   JSON uniquement

------------------------------------------------------------------------

## 📈 Évolutions possibles

-   Carte interactive
-   Système de temps (jours, saisons)
-   Transformation des ressources
-   Gestion économique avancée

------------------------------------------------------------------------

## 🧑‍💻 Auteurs

Projet réalisé dans le cadre d'une POEI.
