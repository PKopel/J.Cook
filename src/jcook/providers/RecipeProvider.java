package jcook.providers;

import jcook.models.Recipe;


public class RecipeProvider extends AbstractProvider<Recipe> {
    private static RecipeProvider instance = null;

    private RecipeProvider(String connectionName, String databaseName) {
        super(connectionName, databaseName, "recipe", Recipe.class);
    }

    public static void initialize(String connectionName, String databaseName) {
        if (instance == null) instance = new RecipeProvider(connectionName, databaseName);
    }

    public static RecipeProvider getInstance() {
        return instance;
    }
}
