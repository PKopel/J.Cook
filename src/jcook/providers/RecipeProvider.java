package jcook.providers;

import jcook.models.Recipe;


public class RecipeProvider extends AbstractProvider<Recipe> {
    private static RecipeProvider instance = null;

    private RecipeProvider(String databaseName) {
        super(databaseName, "recipe", Recipe.class);
    }

    public static void initialize(String databaseName) {
        if (instance == null) instance = new RecipeProvider(databaseName);
    }

    public static RecipeProvider getInstance() {
        return instance;
    }
}
