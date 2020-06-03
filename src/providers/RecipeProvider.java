package providers;

import models.Recipe;


public class RecipeProvider extends AbstractProvider<Recipe> {
    public RecipeProvider(String databaseName) {
        super(databaseName, "recipe", Recipe.class);
    }

}
