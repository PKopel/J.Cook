package models;

import org.bson.types.ObjectId;

import java.util.Collection;

public class User {
    ObjectId id;
    String name;
    Collection<Recipe> recipes;
    Collection<Rating> ratings;
}
