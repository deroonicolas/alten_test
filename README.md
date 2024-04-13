# Alten Back-End

## Présentation

### <ins>Généralités</ins>

Le back-end est disponible à l'adresse [http://localhost:3000](http://localhost:3000) et gère les API REST suivantes :

1. **GET**

   - `products` : récupère l'ensemble des produits
   - `products/x` : récupère un produit où **x** est l'**id** du produit

2. **POST**

   - `products` : crée un nouveau produit

3. **PATCH**

   - `products/x` : modifie tout ou partie d'un produit où **x** est l'**id** du produit

4. **DELETE**

   - `products/x` : supprime un produit où **x** est l'**id** du produit

### <ins>Technologies</ins>

Les technologies suivantes ont été utilisées pour développer le back-end :

- Java 17
- Spring Boot 3.2
- Maven 3.9
- Mysql 8.0
- Git / Github

## Utilisation

### <ins>Installation</ins>

L'application est disponible sur Github. Afin de récupérer le projet, se placer dans le répertoire de votre choix puis exécuter la commande `git clone https://github.com/deroonicolas/alten_test.git back` : cela aura pour effet de créer un répertoire `back` dans lequel se trouvera les sources du projet back-end.

### <ins>Lancement</ins>

Tout d'abord, **Mysql doit être lancé** pour que l'application fonctionne.
Puis, il est possible de lancer l'application de plusieurs manières grâce à :

1. Maven

   - 1.1. Utiliser l'application packagée
     - Se placer à la racine du projet
     - Exécuter la commande `mvn package`
     - Exécuter la commande `java -jar target/back.api-1.0-SNAPSHOT.jar`
   - 1.2. Utiliser le plugin Maven
     - Se placer à la racine du projet
     - Exécuter la commande `mvn spring-boot:run`

2. Spring Tool Suite

   - Cliquer droit sur le projet puis _Run as > Spring Boot App_

**Quelque soit la méthode utilisée**, une base de données `alten_products` comportant une table `products` alimentée par le fichier [src/main/resources/json/products.json](src/main/resources/json/products.json) sera créée.

**Si la base de données et la table alimentée existent déjà**, aucune opération sur la base ne sera réalisée.

### <ins>Utilisation / Test</ins>

Les endpoints de l'API sont disponible à l'adresse [http://localhost:3000/api-docs](http://localhost:3000/api-docs)
L'API peut être utilisée / testée de plusieurs manières :

1. **Postman**

   - Le fichier [alten_products.postman_collection.json](Documentation/alten_products.postman_collection.json) présent dans le répertoire [/Documentation](/Documentation/) regroupe l'ensemble des requêtes évoquées dans la section précédente.
     Sous <ins>Postman</ins>, clquer sur _Menu > File > Import_ puis sélectionner [ce fichier JSON](Documentation/alten_products.postman_collection.json)

2. **Swagger**

   - Il est possible de tester l'API avec Swagger en se rendant sur [Swagger UI](http://localhost:3000/alten-products-api/swagger-ui.html)

3. **Exemple : Récupération des produits dans front-end Angular**

   - Pour tester la récupération des produits à partir du front-end, il faut se rendre dans le fichier _front/src/app/products/products.service.ts_ de l'application `front`.
     - <ins>A la ligne 19</ins> : remplacer `"assets/products.json"` par `"http://localhost:3000/products"`
     - <ins>A la ligne 20</ins> : remplacer `data.data` par `data`

4. **Test unitaires**

   - Des tests unitaires ont également été implémentés dans le fichier [BackApplicationTests.java](src/test/java/com/alten/back/BackApplicationTests.java). Ceux-ci peuvent être exécutés grâce à la commande `mvn test` en étant positionné à la racine de l'application.
     <ins>NB</ins> : Les tests sont également exécutés lors du lancement de l'application (voir section `Lancement`)
