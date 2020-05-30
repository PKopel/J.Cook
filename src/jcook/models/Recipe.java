package jcook.models;

import javafx.scene.image.Image;
import org.bson.types.ObjectId;

import java.util.Collection;

public class Recipe {
    ObjectId id;
    String name;

    // TODO: Chyba że BufferedImage zamiast ścieżki? Mniej optymalne gdybyśmy pobrali obrazki, a później część z nich
    //   * odsiali, ale być może bardziej wygodne - P: szczerze mówiąc nie mam pojęcia, ale chyba lepiej tak jak jest
    String imagePath;
    Collection<Ingredient> ingredients;

    // TODO: Alternatywnie Collection<Rating> - P: objectid chyba lepsze
    Collection<ObjectId> ratingIds;
    Collection<String> tags;
    Collection<Category> categories;

    public Recipe(ObjectId id, String name, String imagePath, Collection<Ingredient> ingredients,
                  Collection<ObjectId> ratingIds, Collection<String> tags, Collection<Category> categories) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
        this.ratingIds = ratingIds;
        this.tags = tags;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Image getImage() {
        return new Image(getClass().getResourceAsStream("/images/"+imagePath));
    }

    public String getName() {
        return this.name;
    }
}
