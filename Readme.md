# J.Cook

![J.Cook](https://github.com/PKopel/J.Cook/blob/master/j_cook.jpeg)

*J.Cook* is a simple cookbook application build with *[mongoDB](https://www.mongodb.com)* and 
*[Java 11](https://openjdk.java.net/projects/jdk/11/)*. 

## Project structure

Main package jcook consists of five subpackages:

1. ```jcook.controllers``` containing GUI controller classes
2. ```jcook.filters``` containing classes used to generate database queries
3. ```jcook.models``` containing data model classes
4. ```jcook.providers``` containing classes responsible for connection with database
5. ```jcook.authentication``` for user authentication

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
## Dependencies

- Mongo Java Driver 3.12.1
- JavaFX 11.0.2

### Authors

* **[Michał Węgrzyn](https://github.com/mwegrzyn2311)**
* **[Paweł Kopel](https://github.com/PKopel)**
