package providers;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import filters.RecipeFilter;
import models.Category;
import models.Ingredient;
import models.Rating;
import models.Recipe;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class RecipeProvider {
    private final MongoDatabase db;

    public RecipeProvider(String databaseName) {
        MongoClient mongoClient = new MongoClient();
        db = mongoClient.getDatabase(databaseName);
    }
public Collection<Recipe> getAllRecipes(RecipeFilter filter) {
        Collection<Recipe> res = new LinkedList<>();

        db.getCollection("recipe")
                .find(filter.getQuery())
                .into(new LinkedList<>())
                .forEach((object) -> {
                    // TODO: not yet sure if collections creation works
                    Collection<Ingredient> ingredients = new LinkedList<>();
                    ((List<Document>) object.get("ingredients")).forEach((ingredient) -> {
                        ingredients.add(new Ingredient(ingredient.getString("name"), ingredient.getInteger("quantity"), ingredient.getString("unit")));
                    });
                    Collection<ObjectId> ratingIds = new LinkedList<>();
                    ((List<Document>) object.get("ratings")).forEach((rating) -> {
                        ratingIds.add(rating.getObjectId("rating_id"));
                    });
                    Collection<String> tags = new LinkedList<>();
                    ((List<Document>) object.get("tags")).forEach((tag) -> {
                        tags.add(tag.getString("tag"));
                    });
                    Collection<Category> categories = new LinkedList<>();
                    ((List<Document>) object.get("categories")).forEach((category) -> {
                        categories.add(Category.getCategory(category.getString("category")));
                    });

                    res.add(new Recipe(
                            object.getObjectId("_id"),
                            object.getString("name"),
                            object.getString("image"),
                            ingredients,
                            ratingIds,
                            tags,
                            categories
                            ));
                });

        return res;
    }

    public Collection<Rating> getRatings(Collection<ObjectId> ids) {
        Collection<Rating> res = new LinkedList<>();

        ids.forEach((id) -> db.getCollection("ratings")
                .find(Filters.eq("_id", id))
                .into(new LinkedList<>())
                .forEach((object) -> {
                    res.add(new Rating(
                            id,
                            object.getInteger("stars"),
                            object.getString("description"),
                            object.getDate("date")
                    ));
                }));

        return res;
    }
}
