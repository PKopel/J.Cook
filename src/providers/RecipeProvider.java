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
import org.bson.conversions.Bson;
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

    private Recipe readRecipe(Document doc){
        Collection<Ingredient> ingredients = doc.getList("ingredients", Ingredient.class);
        Collection<ObjectId> ratingIds = doc.getList("ratings", ObjectId.class);
        Collection<String> tags = doc.getList("tags", String.class);
        Collection<Category> categories =
                doc.getList("categories", String.class)
                        .stream().map(Category::getCategory)
                        .collect(Collectors.toList());
        return new Recipe(
                doc.getObjectId("_id"),
                doc.getString("name"),
                doc.getString("image"),
                ingredients,
                ratingIds,
                tags,
                categories);
    }

    public Collection<Recipe> getRecipes(RecipeFilter filter){
        return db.getCollection("recipe")
                .find(filter.getQuery())
                .map(this::readRecipe)
                .into(new LinkedList<>());
    }

    public Collection<Rating> getRatings(Collection<ObjectId> ids) {

        Collection<Bson> idsQuery = ids.stream()
                .map(id -> Filters.eq("_id", id))
                .collect(Collectors.toList());
        return db.getCollection("ratings")
                .find(Filters.or(idsQuery), Rating.class)
                .into(new LinkedList<>());
    }
}
