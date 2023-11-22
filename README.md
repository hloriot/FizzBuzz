# FizzBuzz

FizzBuzz is a technical assessment for Kreactive.

## Subject

The assessment was given as follow:

_**Attention le test peut sembler très simple mais l'objectif est de poser les bases d'une architecture clean pour que le projet puisse évoluer.
Vous êtes jugés sur la qualité du code produit et la bonne mise en place des composants pour un projet scalable.
Il doit être le reflet de votre savoir-faire ou à minima fournir un readme pour expliquer les choix et non choix.**_



_Dans le cadre du processus de recrutement, on te propose ce projet à réaliser, qui fera une base de discussion pour l’entretien technique qui pourra éventuellement avoir lieu par la suite.
L’objectif de ce projet est de réaliser une application Android qui implémente le jeu Fizz-Buzz.
L’application consiste à afficher un formulaire pour saisir 2 entiers : Entier1 et Entier2, et 2 Strings : mot1 et mot2 ainsi qu’une limite.
L’objectif est d’afficher ensuite une liste de résultats dans laquelle tous les multiples de l’entier1 seront remplacés par mot1, les multiples de l’entier2 par mot 2 et les multiples de entier1*entier2 remplacé par mot1mot2.
Exemple:
entier1 : 3
entier2 : 5
mot1 : fizz
mot2 : buzz
le résultat sera : 1,2,fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz, 16,…._

_L’application doit :_
* _afficher un formulaire avec gestion d’erreur_
* _un liste scrollable_
* _navigation entre 2 écrans_
* _prête à une mise en prod_
* _facile à maintenir par d’autres devs`_
* _code propre et bien architecturé_

### Requirements

From that subject the following requirements can be deduce:
* Display a Form with 5 inputs (3 numbers and 2 words)
* Handle Form input errors: Empty word, Invalid numbers, Negative numbers...
* Display a scrollable list of items
* Navigate between 2 pages (from the Form to the result list)
* Have a clean architecture
* Prevent any crash by strong testing and preventing from invalid usage of functions

## Architecture

### Domain

This package contains all business classes. You usually have repositories interfaces and models.

### Data

This package contains all implementation for the Domain package.

### Presentation

This package contains all UI elements for the app.

## Coding conventions

This repository uses the CoCo rule:
* Respect the **Co**nventions of the language (See [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html))
* Respect the **Co**herence inside the project (Keep the same architecture throughout all the source code)

## Dependencies

* [Material Components](https://github.com/material-components/material-components-android) for UI elements
* [MockK](https://mockk.io/) for mocking classes in tests

