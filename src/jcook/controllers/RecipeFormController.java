package jcook.controllers;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jcook.models.Category;
import jcook.models.Ingredient;
import jcook.models.Recipe;
import jcook.providers.RecipeProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class RecipeFormController {
    @FXML
    GridPane categoryPane;
    List<Category> categoryList = new LinkedList<>();
    @FXML
    GridPane ingredientPane;
    List<Ingredient> ingredientList = new LinkedList<>();
    @FXML
    GridPane tagPane;
    List<String> tagList = new LinkedList<>();
    @FXML
    Button save;
    @FXML
    Button image;
    byte[] imageBytes;
    @FXML
    TextField name;
    @FXML
    TextArea description;

    StackPane mainPane;

    private BiConsumer<GridPane, Button> nextCategory(int index) {
        return (grid, add) -> {
            ComboBox<Category> categories = new ComboBox<>(FXCollections.observableArrayList(Category.values()));
            categories.setPromptText("category");
            categories.setPrefWidth(500.0);
            categories.valueProperty().addListener((a, oldValue, newValue) -> {
                add.setDisable(categories.getValue() == null);
                categoryList.set(GridPane.getRowIndex(add), newValue);
            });
            grid.addRow(index, categories, add);
        };
    }

    private BiConsumer<GridPane, Button> nextIngredient(int index) {
        return (grid, add) -> {
            TextField ingredient = new TextField();
            ingredient.setPromptText("ingredient");
            ingredient.setPrefWidth(400.0);
            TextField quantity = new TextField();
            quantity.setPrefWidth(100.0);
            quantity.setPromptText("quantity");
            TextField unit = new TextField();
            unit.setPrefWidth(50.0);
            unit.setPromptText("unit");
            ChangeListener<? super String> listener = (a, oldValue, newValue) -> {
                add.setDisable(ingredient.getText().isBlank() || quantity.getText().isBlank());
                if (!quantity.getText().isBlank()) ingredientList.set(
                        GridPane.getRowIndex(add),
                        new Ingredient(
                                ingredient.getText(),
                                Double.parseDouble(quantity.getText()),
                                unit.getText()
                        )
                );
            };
            ingredient.textProperty().addListener(listener);
            quantity.textProperty().addListener(listener);
            unit.textProperty().addListener(listener);
            grid.addRow(index, ingredient, quantity, unit, add);
        };
    }

    private BiConsumer<GridPane, Button> nextTag(int index) {
        return (grid, add) -> {
            TextField tag = new TextField();
            tag.setPromptText("tag");
            tag.setPrefWidth(500.0);
            tag.textProperty().addListener((a, oldValue, newValue) -> {
                add.setDisable(newValue.isEmpty());
                tagList.set(GridPane.getRowIndex(add), newValue);
            });
            grid.addRow(index, add, tag);
        };
    }

    private <T> void insertRow(GridPane grid, List<T> values, int index,
                               Function<Integer, BiConsumer<GridPane, Button>> nextRow) {
        for (Node n : grid.getChildren()) {
            int row = GridPane.getRowIndex(n);
            if (row >= index) {
                GridPane.setRowIndex(n, row + 1);
            }
        }

        Button add = new Button("+");
        add.setDisable(true);
        add.setOnAction(evt -> insertRow(grid, values, GridPane.getRowIndex(add) + 1, nextRow));

        values.add(index, null);
        nextRow.apply(index).accept(grid, add);
    }

    @FXML
    public void initialize() {
        insertRow(categoryPane, categoryList, 0, this::nextCategory);
        insertRow(ingredientPane, ingredientList, 0, this::nextIngredient);
        insertRow(tagPane, tagList, 0, this::nextTag);
        image.setOnAction(actionEvent -> {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("images (*.jpg,*.jpeg, *.png)",
                            "*.jpg", "*.jpeg", "*.png");
                    fileChooser.getExtensionFilters().add(extFilter);
                    File file = fileChooser.showOpenDialog(null);
                    if (file != null) {
                        imageBytes = new byte[(int) file.length()];
                        try {
                            FileInputStream input = new FileInputStream(file);
                            input.read(imageBytes);
                            input.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        save.setOnAction(event -> {
            RecipeProvider.getInstance().addObject(new Recipe(
                    name.getText(),
                    description.getText(),
                    imageBytes,
                    ingredientList,
                    tagList,
                    categoryList
            ));
            ((Stage) save.getScene().getWindow()).close();
        });
    }

    public void setMainPane(StackPane mainPane) {
        this.mainPane = mainPane;
    }
}
