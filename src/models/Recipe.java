package models;

import java.util.Collection;

public class Recipe {
    String name;
    String imagePath; // Or BufferedImage - less efficient if we filter out some recipes, but might be more convenient
    Collection<Ingredient> ingredients;
    Collection<String> ratings;
    Collection<String> tags;
    Collection<Category> Categories;
}
