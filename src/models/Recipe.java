package models;

import com.mongodb.BasicDBObject;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.LinkedList;

@BsonDiscriminator
public class Recipe extends BasicDBObject {
    private String name;

    private String imagePath;
    private LinkedList<Ingredient> ingredients;
    private LinkedList<ObjectId> ratingIds;
    private LinkedList<String> tags;
    private LinkedList<Category> categories;

    @BsonCreator
    public Recipe(
                  @BsonProperty("name") final String name,
                  @BsonProperty("image") final String imagePath,
                  @BsonProperty("ingredients") final LinkedList<Ingredient> ingredients,
                  @BsonProperty("ratings") final LinkedList<ObjectId> ratingIds,
                  @BsonProperty("tags") final LinkedList<String> tags,
                  @BsonProperty("categories") final LinkedList<Category> categories) {
        this.name = name;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
        this.ratingIds = ratingIds;
        this.tags = tags;
        this.categories = categories;
    }

    @BsonId
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public LinkedList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(LinkedList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public LinkedList<ObjectId> getRatingIds() {
        return ratingIds;
    }

    public void setRatingIds(LinkedList<ObjectId> ratingIds) {
        this.ratingIds = ratingIds;
    }

    public LinkedList<String> getTags() {
        return tags;
    }

    public void setTags(LinkedList<String> tags) {
        this.tags = tags;
    }

    public LinkedList<Category> getCategories() {
        return categories;
    }

    public void setCategories(LinkedList<Category> categories) {
        this.categories = categories;
    }
}
