## Java Recipe Web Application README

![res1](https://user-images.githubusercontent.com/55871427/93521529-da0d2200-f938-11ea-8be0-579c0b8afa5e.JPG)

## About
NomNom is a recipe sharing web application that lets users view and create recipes and also comment and register on them.</br>
It supports user authentication too. </br>

## Technologies Used
The app is a SpringBoot app. It uses:
- Spring Data JPA for persistence
- Spring Security for user authentication
- Spring Web for controllers
- Thymeleaf for templating
- H2 database for development and testing
- Mockito and JUnit for testing
- HTML/CSS/JS for front-end
- Bootstrap and tinyMCE as front end libraries for specific styling

## Setup
To set up the application you need to have java 8 and maven dependency manager.
After resolving dependencies with Maven you need to build the application. Once built you can run it by running the NomNomApplication class.
We recommend doing it with IntellJ idea IDE as it was used to create the project.

## General package Structure
The app follows MVC pattern. We have packages than contain:
- controller which handles user requests returns templates
- service where the main logic of the program is in. It gets called by controllers when data handling is needed.
- repository where the logic of interaction with the database is stored. Generally called by service methods.
- entity where we define entities used by the application. And with JPA annotations how they will be represented in the database.
- security where security related classes and config is stored.

Also, we have a resource folder. Here we have:
- templates where the thymeleaf templates are kept.
- static where we keep our css and js used to style the page.
- yml files used to keep configuration settings
- sql fields used to populate the database for testing and development

The testing classes are in the test folder.
We have defined:
- controller tests where we test how our controllers work
- service tests where we test our service methods
- it tests where integration tests are stored

## Authors
The app was made by
- Cristina Rodriguez
- Kristers Nikitins
- Jevgenijs Konevs
- Agate Ezera
- Valerija Britikova

