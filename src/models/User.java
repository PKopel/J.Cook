package models;

import java.util.Collection;

public class User {
    String id;
    String name;
    Collection<Recipe> recipes;
    Collection<Rating> ratings;
}
