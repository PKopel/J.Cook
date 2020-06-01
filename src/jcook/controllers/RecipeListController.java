package jcook.controllers;

import com.mongodb.client.model.Filters;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import jcook.filters.CombinedFilter;
import jcook.filters.Filter;
import jcook.filters.NameFilter;
import jcook.models.Recipe;
import jcook.providers.RecipeProvider;


import java.util.*;

public class RecipeListController {

    private CombinedFilter currentFilter = new CombinedFilter(Filters::and);
    private NameFilter nameFilter = null;
    private RecipeProvider recipeProvider = new RecipeProvider("JCookTest");
    private final int fixedCellSize = 50;

    @FXML
    ListView list;
    @FXML
    TextField nameFilterTextField;
    @FXML
    Button nameFilterButton;

    @FXML
    public void initialize() {
        currentFilter.addFilter(new NameFilter("lemonade"));

        list.setFixedCellSize(fixedCellSize);
        list.setItems(FXCollections.observableList((List) recipeProvider.getObjects(currentFilter)));
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

        nameFilterButton.addEventHandler(ActionEvent.ACTION, e -> {
            if(nameFilter != null) {
                currentFilter.removeFilter(nameFilter);
            }
            nameFilter = new NameFilter(nameFilterTextField.getText());
            currentFilter.addFilter(nameFilter);
            list.setItems(FXCollections.observableList((List) recipeProvider.getObjects(currentFilter)));
        });

    }
}
