package jcook.models;

import javafx.scene.image.Image;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class Recipe implements Model {
    private ObjectId id;
    private String name;
    private String description;

    private byte[] image;
    private List<Ingredient> ingredients;
    private List<Rating> ratings = new ArrayList<>();
    private List<String> tags;
    private List<Category> categories;

    public Recipe() {
    }

    public Recipe(
            String name,
            String description,
            byte[] image,
            List<Ingredient> ingredients,
            List<String> tags,
            List<Category> categories) {
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

    @BsonIgnore
    public Image getRenderedImage() {
        if (image != null) {
            return new Image(new ByteArrayInputStream(image));
        } else {
            return null;
        }
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public BigDecimal avgRating() {
        if (ratings == null || ratings.isEmpty()) return BigDecimal.valueOf(0.0).setScale(1, RoundingMode.HALF_UP);
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
