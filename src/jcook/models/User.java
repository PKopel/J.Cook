package jcook.models;

import javafx.scene.image.Image;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;

public class User implements Model{

    private ObjectId id;
    private String name;
    @BsonProperty("uploaded_recipes")
    private Collection<ObjectId> uploadedRecipes;
    @BsonProperty("rated_recipes")
    private Collection<ObjectId> ratedRecipes;
    private String password;
    private byte[] image;

    public User() {
    }

    @BsonIgnore
    public Image getRenderedImage() {
        return new Image(new ByteArrayInputStream(getImage()));
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public User(String name, Collection<ObjectId> uploadedRecipes, Collection<ObjectId> ratedRecipes, String password, byte[] image) {
        this.name = name;
        this.uploadedRecipes = uploadedRecipes;
        this.ratedRecipes = ratedRecipes;
        this.password = password;
        this.image = image;
    }

    @BsonIgnore
    public static User offlineUser() throws IOException {
        return new User("offline", new LinkedList<>(), new LinkedList<>(), "offline", User.class.getResourceAsStream("/images/j_cook.jpeg").readAllBytes());
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
