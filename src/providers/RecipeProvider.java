package providers;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import models.Category;
import models.Ingredient;
import models.Rating;
import models.Recipe;
import org.bson.Document;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class RecipeProvider {
    private MongoClient mongoClient;
    private MongoDatabase db;

    public RecipeProvider(String databaseName) {
        mongoClient = new MongoClient();
        db = mongoClient.getDatabase(databaseName);
    }

    public Collection<Recipe> getAllRecipes() {
        Collection<Recipe> res = new LinkedList<>();

        db.getCollection("recipe")
                .find()
                .into(new LinkedList<>())
                .forEach((object) -> {
                    // TODO: not yet sure if collections creation works
                    Collection<Ingredient> ingredients = new LinkedList<>();
                    ((List<Document>) object.get("ingredients")).forEach((ingredient) -> {
                        ingredients.add(new Ingredient(ingredient.getString("name"), ingredient.getInteger("quantity"), ingredient.getString("unit")));
                    });
                    Collection<String> ratingIds = new LinkedList<>();
                    ((List<Document>) object.get("ratings")).forEach((rating) -> {
                        ratingIds.add(rating.getString("rating_id"));
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
                            object.getString("_id"),
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

    public Collection<Rating> getRatings(Collection<String> ids) {
        Collection<Rating> res = new LinkedList<>();

        ids.forEach((id) -> {
            db.getCollection("ratings")
                    .find(Filters.eq("_id", id))
                    .into(new LinkedList<>())
                    .forEach((object) -> {
                        res.add(new Rating(
                                id,
                                object.getInteger("stars"),
                                object.getString("description"),
                                object.getDate("date")
                        ));
                    });
        });

        return res;
    }
}
