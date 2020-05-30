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
        LinkedList<Ingredient> ingredients = new LinkedList<>(Arrays.asList(
                new Ingredient("a", 1, "l"),
                new Ingredient("b", 1, "l"),
                new Ingredient("c", 1, "l")
        ));
        Recipe recipe = new Recipe(
                "Test",
                "path",
                ingredients,
                new LinkedList<>(Collections.singleton(ObjectId.get())),
                new LinkedList<>(Collections.singletonList("tag")),
                new LinkedList<>(Collections.singletonList(Category.ALCOHOL))
        );
        repr.addObject(recipe);
        //System.out.println(repr.getObjects(new CategoryFilter(Category.ALCOHOL)));
    }
}
