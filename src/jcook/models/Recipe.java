package jcook.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Collection;
import java.util.OptionalDouble;

public class Recipe {
    private String name;
    private String description;

    @BsonProperty(value = "image_path")
    private String imagePath;
    private Collection<Ingredient> ingredients;
    private Collection<Rating> ratings;
    private Collection<String> tags;
    private Collection<Category> categories;

    public Recipe() {
    }

    public Recipe(
            String name,
            String description,
            String imagePath,
            Collection<Ingredient> ingredients,
            Collection<String> tags,
            Collection<Category> categories) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
        this.tags = tags;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return this.name;
    }
    @BsonIgnore
    public Image getImage() {
        return new Image(getClass().getResourceAsStream(imagePath));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Collection<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Collection<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Collection<Rating> ratings) {
        this.ratings = ratings;
    }

    public void addRating(Rating rating){
        ratings.add(rating);
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public Collection<Category> getCategories() {
        return categories;
    }

    public void setCategories(Collection<Category> categories) {
        this.categories = categories;
    }

    public DoubleProperty avgRating() {
        OptionalDouble sum = ratings.stream().mapToDouble(Rating::getStars).average();
        return new SimpleDoubleProperty(sum.isPresent() ? sum.getAsDouble() : 0.0);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
