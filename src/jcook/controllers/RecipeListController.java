package jcook.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class RecipeListController {

    private final ObservableList testData = FXCollections.observableArrayList();

    @FXML
    ListView list;

    @FXML
    public void initialize() {
        testData.addAll("Recipe1", "Recipe2", "Recipe0", "Not a recipe");
        list.setItems(testData);
    }
}
