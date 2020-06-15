package jcook.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import jcook.models.Ingredient;
import jcook.models.Recipe;

public class RecipeViewController {
    @FXML
    Label recipeNameLabel;
    @FXML
    ListView<Ingredient> ingredientList;
    @FXML
    ImageView recipeImage;
    @FXML
    Label recipeDescription;
    private Recipe recipe;

    public RecipeViewController() {
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;

        recipeNameLabel.setText(recipe.getName());
        ingredientList.setItems(FXCollections.observableList(recipe.getIngredients()));
        recipeImage.setImage(recipe.getRenderedImage());
        recipeDescription.setText(recipe.getDescription());
    }

    public void initialize() {
        ingredientList.setCellFactory(param -> new ListCell<>() {
            @Override
            public void updateItem(Ingredient ingredient, boolean empty) {
                if (empty) {
                    setText(null);
                } else {
                    setText(ingredient.getQuantity() + " " + ingredient.getUnit() + " " + ingredient.getName());
                }
            }
        });
    }
}
