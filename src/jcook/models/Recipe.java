package jcook.models;

import javafx.scene.image.Image;
import org.bson.types.ObjectId;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.OptionalDouble;

public class Recipe implements Model{
    private ObjectId id;
    private String name;
    private String description;

    private byte[] image;
    private Collection<Ingredient> ingredients;
    private Collection<Rating> ratings;
    private Collection<String> tags;
    private Collection<Category> categories;

    public Recipe() {
    }

    public Recipe(
            String name,
            String description,
            byte[] image,
            Collection<Ingredient> ingredients,
            Collection<String> tags,
            Collection<Category> categories) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.ingredients = ingredients;
        this.tags = tags;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getRenderedImage() {
        return new Image(new ByteArrayInputStream(getImage()));
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public BigDecimal avgRating() {
        if(ratings == null || ratings.isEmpty()) return BigDecimal.valueOf(0.0).setScale(1, RoundingMode.HALF_UP);
        OptionalDouble sum = ratings.stream().mapToDouble(Rating::getStars).average();
        return BigDecimal.valueOf(sum.isPresent() ? sum.getAsDouble() : 0.0).setScale(1, RoundingMode.HALF_UP);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
