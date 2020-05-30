package providers;

import filters.CategoryFilter;
import models.Category;
import models.Ingredient;
import models.Recipe;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;


public class RecipeProvider extends AbstractProvider<Recipe> {

    public RecipeProvider(String databaseName) {
        super(databaseName, "recipe", Recipe.class);
    }

    public static void main(String[] args) {
        RecipeProvider repr = new RecipeProvider("JCookTest");
        Recipe recipe = new Recipe(
                "Test",
                "path",
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>()
        );
        repr.addObject(recipe);
        //System.out.println(repr.getObjects(new CategoryFilter(Category.ALCOHOL)));
    }
}
