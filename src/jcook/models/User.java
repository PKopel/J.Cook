package jcook.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Collection;

public class User implements Model{
    private ObjectId id;
    private String name;
    @BsonProperty("uploaded_recipes")
    private Collection<ObjectId> uploadedRecipes;
    @BsonProperty("rated_recipes")
    private Collection<ObjectId> ratedRecipes;
    private String password;

    public User() {
    }

    public User(String name, Collection<ObjectId> uploadedRecipes, Collection<ObjectId> ratedRecipes, String password) {
        this.name = name;
        this.uploadedRecipes = uploadedRecipes;
        this.ratedRecipes = ratedRecipes;
        this.password = password;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public Collection<ObjectId> getUploadedRecipes() {
        return uploadedRecipes;
    }
    public void setUploadedRecipes(Collection<ObjectId> uploadedRecipes) {
        this.uploadedRecipes = uploadedRecipes;
    }

    public Collection<ObjectId> getRatedRecipes() {
        return ratedRecipes;
    }
    public void setRatedRecipes(Collection<ObjectId> ratedRecipes) {
        this.ratedRecipes = ratedRecipes;
    }

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
}
