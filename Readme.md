# J.Cook

![J.Cook](https://github.com/mwegrzyn2311/J.Cook/blob/master/readmeImages/j_cook.jpeg)

*J.Cook* is a simple cookbook application build with *[mongoDB](https://www.mongodb.com)* and 
*[Java 11](https://openjdk.java.net/projects/jdk/11/)*. 

## Project structure

Main package jcook consists of five subpackages:

1. ```jcook.controllers``` containing GUI controller classes
2. ```jcook.filters``` containing classes used to generate database queries
3. ```jcook.models``` containing data model classes
4. ```jcook.providers``` containing classes responsible for connection with database
5. ```jcook.authentication``` for user authentication

Package resources contains two subpackages essential for code to work:

1. ```resources.fxml``` containing fxml files for each of the application views
2. ```resources.stylesheets``` containing css files with styles for gui

## Database structure

Database used by J.Cook consists of two collections: *recipe* and *user*. Documents in collection *recipe* look
like this: 

```
{
    "name":<name>,
    "image":{
        "$binary":<binary>,
        "$type":"00"
        },
    "ingredients":[{
        "name":<name>,
         "quantity":<quantity>,
         "unit":<unit|blanc>
         }],
    "ratings":[{
        "stars":<integer>,
        "description":<description>,
        "date":<dd/mm/yyyy>,
        "author":{
            "$oid":<id>
            }
        }],
    "categories":[
<"BEVERAGE"|"ALCOHOL"|"SOUP"|"DESSERT"|"BREAKFAST"|"MEAT"|"VEGAN"|"VEGETARIAN">
        ],
    "tags":[<tag>],
    "description":<description>
}
```
Documents in collection *user* look like this one:


```
{
      "_id":{"$oid":<id>},
      "name": <name>,
      "image":{
            "$binary":<binary>,
            "$type":"00"
            },
      "password":{
            "$binary":<binary>,
            "$type":"00"
            },
      "salt":{
            "$binary":<binary>,
            "$type":"00"
            },
      "rated_recipes":[<object id>],
      "uploaded_recipes":[<object id>]
}
```
## Application views

![J.Cook](https://github.com/mwegrzyn2311/J.Cook/blob/master/readmeImages/LoginView.JPG)

After opening the app, user sees login panel - they can either log in, start new account creation using register button
 or join offline.

![J.Cook](https://github.com/mwegrzyn2311/J.Cook/blob/master/readmeImages/RegisterView.JPG)

During registration user can specify their username, password and profile image. Password has to contain at least one
 capital letter and at least one digit.

![J.Cook](https://github.com/mwegrzyn2311/J.Cook/blob/master/readmeImages/RecipeListView.JPG)

After logging in, user sees list of recipes from the database. They can add new filters to find recipes they are looking
 for using the left panel. Filters can be removed using the right panel. The header contains button "Add recipes" 
 which is only available if they are logged in with and actual account. User can
  also double-click a recipe from the list to see its details.

![J.Cook](https://github.com/mwegrzyn2311/J.Cook/blob/master/readmeImages/NewRecipeView.JPG)

New recipe form

![J.Cook](https://github.com/mwegrzyn2311/J.Cook/blob/master/readmeImages/RecipeView.JPG)

After double-clicking the recipe a recipe view window will show where user can see all the ingredients, recipe description and review other users comments and ratings. He can also add his own comment if he haven't already done it and if he is logged in with an account. If user is reviewing a recipe added by him, two buttons will show: one to remove it from the database and another to edit it.

![J.Cook](https://github.com/mwegrzyn2311/J.Cook/blob/master/readmeImages/EditRecipeView.JPG)

Editing a recipe is almost the same as adding a new one, but it will just update the recipe instead of adding a new
 one and all the recipe details will already be loaded to the update view.

## Dependencies

- Mongo Java Driver 3.12.1
- JavaFX 11.0.2

### Authors

* **[Michał Węgrzyn](https://github.com/mwegrzyn2311)**
* **[Paweł Kopel](https://github.com/PKopel)**
