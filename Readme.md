# J.Cook

![J.Cook](https://github.com/mwegrzyn2311/J.Cook/blob/master/readmeImages/j_cook.jpeg)

*J.Cook* is a simple cookbook application build with *[mongoDB](https://www.mongodb.com)*, 
*[Java 11](https://openjdk.java.net/projects/jdk/11/)* and *[gradle](https://gradle.org)*. 

## Running project

Running project requires Java 11. To start project run ```./gradlew run``` (*NIX) or ```./gradlew.bat run```(WIN) in
 the command line. Gradle will download necessary dependencies.

## Project structure

Main package jcook consists of five subpackages:

1. ```jcook.controllers``` containing GUI controller classes. Each fxml file has a corresponding Controller in
 ```jcook.controllers``` package. This is where data to tables/lists/etc. is loaded, where Button handlers are registered, etc.

2. ```jcook.filters``` containing classes used to generate database queries. Queries are generated
in form of BSON objects with use of **Filters** class provided by Mongo Java Driver. Each query is wrapped for
 convenience in class implementing interface Filter. One special case is CombinedFilter class taking a collection 
 of Filters and function to combine them (for example Filters::and/Filters::or) to create a more complex filter.
3. ```jcook.models``` containing data model classes. These classes are directly serialized to BSON
objects via the POJO Codec.
4. ```jcook.providers``` containing classes responsible for connection with the database. 
    - Main class in this package
    is generic AbstractProvider<T> with methods:
        - ```getObjects(Filter filter)``` - Returns collection of objects of type T retrieved from database collection with a query
     from argument filter. Objects are quietly cast to type T by POJO Codec.
        - ```addObject(T object)``` - Adds a new object to database collection via the POJO Codec.
        - ```updateObject(T oldObject, T newObject)``` - uses reflection to find differences between oldObject and newObject, and 
    **Updates** class from Mongo Java Driver to save changes in the database.
        - ```updateObject(T object, V newElement, String arrayName)``` - Adds newElement to the object's array of name
         *arrayName* in the database.
        - ```deleteObjects(Filter filter)``` - Removes all objects matching the filter's query from the database.
    - Classes RecipeProvider and UserProvider use generic argument of AbstractProvider to create class-specific
      methods for interaction with the database. They are implemented as singletons to allow access to them from any 
      other part of the code.
5. ```jcook.authentication``` for user authentication. Its only class LoginManager is responsible for managing 
user session. It is implemented as a singleton to allow access to it from any other part of the code.

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
        "$binary":<image bytes>,
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
        "author":<object id>
        }],
    "categories":[
<"BEVERAGE"|"ALCOHOL"|"SOUP"|"DESSERT"|"BREAKFAST"|"MEAT"|"VEGAN"|"VEGETARIAN">
        ],
    "tags":[<tag>],
    "description":<description>
}
```
Example:

```
{
    "_id" : ObjectId("5ee87ac80014fe7bc76015b3"),
    "categories" : [ 
        "BEVERAGE", 
        "VEGETARIAN", 
        "VEGAN"
    ],
    "description" : "Slice lemons. Add 500 ml of water. Add 125g of sugar. Add 500ml of water. Mix for 30 seconds. If you'd like to have your drink colder, you might add some ice. Enjoy!",
    "image" : { 
        "$binary" : <image bytes>, 
        "$type":"00"
        },
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
      "_id":<object id>,
      "name": <name>,
      "image":{
            "$binary":<image bytes>,
            "$type":"00"
            },
      "password":{
            "$binary":<password hash bytes>,
            "$type":"00"
            },
      "salt":{
            "$binary":<salt bytes>,
            "$type":"00"
            },
      "rated_recipes":[<object id>],
      "uploaded_recipes":[<object id>]
}
```
Example:

```
{
    "_id": ObjectId("5ee87a320014fe7bc76015b2"),
	"image": {
        "$binary" : <image bytes>,
        "$type":"00"
        },
	"name" : "Geralt",
    "password" : { 
        "$binary" : "OAkxQG19L3LyA2dS0Z1jvA==", 
        "$type" : "00"
        },
    "rated_recipes" : [ 
        ObjectId("5ee87ac80014fe7bc76015b3"), 
        ObjectId("5ee87c580014fe7bc76015b4")
        ],
    "salt" : { 
        "$binary" : "AL6YKZXzOOUgIZaHSiOnMQ==", 
        "$type" : "00" 
        },
    "uploaded_recipes" : [ 
        ObjectId("5ee87ac80014fe7bc76015b3"), 
        ObjectId("5ee87c580014fe7bc76015b4")
        ]
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
