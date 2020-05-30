package jcook.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Paint;
import jcook.models.Recipe;

import java.util.LinkedList;

public class RecipeListController {

    private final ObservableList<Recipe> testData = FXCollections.observableArrayList();
    private final int fixedCellSize = 40;

    @FXML
    ListView list;

    @FXML
    public void initialize() {
        for(int i = 0; i < 15; i++) {
            testData.add(new Recipe(null, "Recipe"+i, "j_cook.jpeg", new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>()));
        }

        list.setFixedCellSize(fixedCellSize);
        list.setItems(testData);
        list.setCellFactory(param -> new ListCell<Recipe>() {
            private ImageView icon = new ImageView();
            @Override
            public void updateItem(Recipe recipe, boolean empty) {
                super.updateItem(recipe, empty);
                if(empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    icon.setImage(recipe.getImage());
                    icon.setFitHeight(fixedCellSize);
                    icon.setFitWidth(fixedCellSize);
                    setText(recipe.getName());
                    setGraphic(icon);
                    // this.setBorder(new Border(new BorderStroke(Paint.valueOf("000000"), BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
                }
            }
        });

    }
}
