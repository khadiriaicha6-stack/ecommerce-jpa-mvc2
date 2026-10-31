# Application web e-commerce : MVC2 et JPA

Atelier JEE, Cycle Ingénieur LSI (LSI2)
Faculté des Sciences et Techniques de Tanger, Université Abdelmalek Essaâdi

Réalisé par : **Aicha EL KHADIRI**
Encadré par : Pr. ELAACHAK Lotfi

## Description

Application web qui simule un site e-commerce. Elle permet à un internaute de s'inscrire, de se connecter, de consulter la vitrine des produits, de gérer les produits (ajout, modification, suppression) et de remplir son panier.

Le contrôleur suit le patron **MVC 2** : une seule Servlet par gestion, avec plusieurs actions choisies par le paramètre `action`.

## Technologies

- Java 11, Maven (packaging war)
- Servlets 4.0 (MVC2), JSP, JSTL, jQuery
- JPA 2.2 avec EclipseLink 2.7.5
- MySQL / MariaDB (XAMPP)
- Apache Tomcat 9

## Architecture

```
JSP (vue) -> Servlet (contrôleur) -> DAO -> JPA / EclipseLink -> MySQL
```

| Package | Rôle |
|---|---|
| `model` | Entités JPA : Internaute, Vitrine, Produit, Panier, LignePanier |
| `dao` | Accès aux données et transactions JPA |
| `listener` | Création de l'EntityManagerFactory au démarrage |
| `controller` | VitrineServlet, InternauteServlet, PanierServlet |

## Servlets et actions

| Servlet | Actions |
|---|---|
| VitrineServlet | lister, formulaire, ajouter, modifier, supprimer |
| InternauteServlet | afficherConnexion, afficherInscription, inscrire, connecter, deconnecter |
| PanierServlet | voir, ajouter, retirer, vider |

## Lancer le projet

1. Démarrer MySQL (par exemple avec XAMPP).
2. Dans `src/main/resources/META-INF/persistence.xml`, vérifier l'utilisateur et le mot de passe de la base de données.
3. Lancer l'application sur **Tomcat 9** (pas Tomcat 10) avec le contexte `/ecommerce-jpa`.
4. Ouvrir http://localhost:8080/ecommerce-jpa/

La base `ecommerce_db` et ses tables sont générées automatiquement par JPA au premier démarrage.
