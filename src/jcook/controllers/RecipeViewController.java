package jcook.controllers;

import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import jcook.models.Ingredient;
import jcook.models.Recipe;

import java.io.ByteArrayInputStream;
import java.util.List;

public class RecipeViewController {
    private Recipe recipe;

    @FXML
    Label recipeNameLabel;
    @FXML
    ListView<Ingredient> ingredientList;
    @FXML
    ImageView recipeImage;
    @FXML
    Label recipeDescription;

    public RecipeViewController() { }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;

        recipeNameLabel.setText(recipe.getName());
        ingredientList.setItems(FXCollections.observableList((List)recipe.getIngredients()));
        recipeImage.setImage(new Image(new ByteArrayInputStream(recipe.getImage())));
        recipeDescription.setText(recipe.getDescription());
    }

    public void initialize() {
        ingredientList.setCellFactory(param -> new ListCell<>() {
            @Override
            public void updateItem(Ingredient ingredient, boolean empty) {
                if(empty) {
                    setText(null);
                } else {
                    setText(ingredient.getQuantity()+" "+ingredient.getUnit()+" "+ingredient.getName());
                }
            }
        });
    }
}
