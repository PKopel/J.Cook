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

More about each package:

1. ```jcook.controllers``` Controllers for javafx gui. Each fxml file has a corresponding Controller in 	```jcook.controllers``` package. This is where data to tables/lists/etc. is loaded, where Button handlers are registered, etc.
2. ```jcook.filters``` Filters for database collections: 
   - Filter interface - it can be implemented, to get only those objects that fulfill BSON query defined in getQuery() method. 
   - CombinedFilter - Sometimes there is need for more than just one filter and this is where CombinedFilter shines - It takes a collection of Filters and a Function that Combine them (for example Filters::and/Filters::or), combines the filters and therefore it makes it a more complex filter.
   - NameFilter, IdFilter, etc. - the other filters are just implementations of the Filter interface which were used in the project
3. ```jcook.models``` Models for objects returned from the database - They represent objects taken from the database and they can be used to create corresponding providers
4. ```jcook.provider``` Database objects providers:
   - AbstractProvider - Abstraction achieved in this class enables us to make provider for each model in a very simple way. It has a few important methods:
     - getObjects(Filter filter) - Returns objects from database collection filtered with a filter passed as an argument
     - addObject(T object) - Adds a new object to database collection
     - public void updateObject(T oldObject, T newObject) - Finds oldObject in the database and updates it with differences from newObject
     - public <V> void updateObject(T object, V newElement, String arrayName) - Adds newElement to the object's array represented by arrayName in the database
     - public void deleteObjects(Filter filter) - Removes all objects matching the filter's requirements from the database
   - RecipeProvider, UserProvider - Acutal providers extending the AbstractProvider. By abstraction achieved in the AbstractProvider, theese classes are very simple and, if a new collection has to be added, another provider can be added in a very simple way. They are singleton classes.
5. ```jcook.authentication``` It consists one class: LoginManager - It is singleton so that any other class can get info about currently logged user. It also performs authentication (Which is important as the passwords are encrypted).

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
Example:

```JSON
{
    "_id" : ObjectId("5ee87ac80014fe7bc76015b3"),
    "categories" : [ 
        "BEVERAGE", 
        "VEGETARIAN", 
        "VEGAN"
    ],
    "description" : "Slice lemons. Add 500 ml of water. Add 125g of sugar. Add 500ml of water. Mix for 30 seconds. If you'd like to have your drink colder, you might add some ice. Enjoy!",
    "image" : { "$binary" : <image bytes>},
    "ingredients" : [ 
        {
            "name" : "Lemons",
            "quantity" : 6.0,
            "unit" : ""
        }, 
        {
            "name" : "Sugar",
            "quantity" : 125.0,
            "unit" : "g"
        }, 
        {
            "name" : "Water",
            "quantity" : 1.0,
            "unit" : "l"
        }, 
        {
            "name" : "Ice cubes (optional)",
            "quantity" : 8.0,
            "unit" : ""
        }
    ],
    "name" : "Lemonade",
    "ratings" : [ 
        {
            "author" : ObjectId("5ee87a320014fe7bc76015b2"),
            "date" : "16/06/2020",
            "description" : "You totally have to check it out!",
            "stars" : 5.0
        }, 
        {
            "author" : ObjectId("5ee87c860014fe7bc76015b5"),
            "date" : "16/06/2020",
            "description" : "Pretty good indeed :3",
            "stars" : 4.0
        }
    ],
    "tags" : [ 
        "Cold", 
        "Sugar included", 
        "Summer"
    ]
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
Example:

```JSON
{
    "_id": ObjectId("5ee87a320014fe7bc76015b2"),
	"image": {"$binary" : <image bytes>},
	"name" : "Geralt",
    "password" : { "$binary" : "OAkxQG19L3LyA2dS0Z1jvA==", "$type" : "00" },
    "rated_recipes" : [ 
        ObjectId("5ee87ac80014fe7bc76015b3"), 
        ObjectId("5ee87c580014fe7bc76015b4")
    ],
    "salt" : { "$binary" : "AL6YKZXzOOUgIZaHSiOnMQ==", "$type" : "00" },
    "uploaded_recipes" : [ 
        ObjectId("5ee87ac80014fe7bc76015b3"), 
        ObjectId("5ee87c580014fe7bc76015b4")
    ]
}
```

## Application views

![J.Cook](https://github.com/mwegrzyn2311/J.Cook/blob/master/readmeImages/LoginView.JPG)

After opening the app, user sees login panel - he can either log in, start new account creation using register button or join offline.

![J.Cook](https://github.com/mwegrzyn2311/J.Cook/blob/master/readmeImages/RegisterView.JPG)

During registration user can specify his username, password and profile image. Password has to contain at least one capital letter and at least one digit.

![J.Cook](https://github.com/mwegrzyn2311/J.Cook/blob/master/readmeImages/RecipeListView.JPG)

After logging in, user sees list of recipes from the database, he can add new filters to find what he is looking for using the left panel and check or remove them using the right panel. The header contains button "Add recipes" which is only there if user is logged with and actual account and can be used to start adding a new recipe. He can also double-click a recipe from the list to see its details.

![J.Cook](https://github.com/mwegrzyn2311/J.Cook/blob/master/readmeImages/NewRecipeView.JPG)

New recipe form

![J.Cook](https://github.com/mwegrzyn2311/J.Cook/blob/master/readmeImages/RecipeView.JPG)

After double-clicking the recipe a recipe view window will show where user can see all the ingredients, an actual recipe description and also review other users comments and ratings. He can also add his own comment if he haven't already done it and if he is logged in with an account. If user is reviewing a recipe added by him, two buttons will show: one to remove it from the database and another to edit it.

![J.Cook](https://github.com/mwegrzyn2311/J.Cook/blob/master/readmeImages/EditRecipeView.JPG)

Editing a recipe is almost the same as adding a new one but it will just update the recipe instead of adding a new one and all of the recipe details will already be loaded to the update view.

## Dependencies

- Mongo Java Driver 3.12.1
- JavaFX 11.0.2

### Authors

* **[Michał Węgrzyn](https://github.com/mwegrzyn2311)**
* **[Paweł Kopel](https://github.com/PKopel)**
