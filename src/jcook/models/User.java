package jcook.models;

import org.bson.types.ObjectId;

import java.util.Collection;

public class User {
    private String name;
    private Collection<ObjectId> recipes;
    private Collection<ObjectId> ratings;

    public User() {
    }

    public User(String name, Collection<ObjectId> recipes, Collection<ObjectId> ratings) {
        this.name = name;
        this.recipes = recipes;
        this.ratings = ratings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<ObjectId> getRecipes() {
        return recipes;
    }

    public void setRecipes(Collection<ObjectId> recipes) {
        this.recipes = recipes;
    }

    public Collection<ObjectId> getRatings() {
        return ratings;
    }

    public void setRatings(Collection<ObjectId> ratings) {
        this.ratings = ratings;
    }
}
