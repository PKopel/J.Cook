package models;

import java.util.Collection;

public class Recipe {
    String id;
    String name;

    // TODO: Chyba że BufferedImage zamiast ścieżki? Mniej optymalne gdybyśmy pobrali obrazki, a później część z nich
    //   * odsiali, ale być może bardziej wygodne
    String imagePath;
    Collection<Ingredient> ingredients;

    // TODO: Alternatywnie Collection<Rating>
    Collection<String> ratingIds;
    Collection<String> tags;
    Collection<Category> categories;

    public Recipe(String id, String name, String imagePath, Collection<Ingredient> ingredients, Collection<String> ratingIds, Collection<String> tags, Collection<Category> categories) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
        this.ratingIds = ratingIds;
        this.tags = tags;
        this.categories = categories;
    }
}
