package jcook.controllers;

import com.mongodb.client.model.Filters;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jcook.filters.CombinedFilter;
import jcook.filters.Filter;
import jcook.filters.NameFilter;
import jcook.models.Recipe;
import jcook.providers.RecipeProvider;


import java.util.*;

public class RecipeListController {

    private CombinedFilter currentFilter = new CombinedFilter(Filters::and);
    private RecipeProvider recipeProvider = new RecipeProvider("JCookTest");
    private final int fixedCellSize = 50;

    @FXML
    TableView<Recipe> recipeTable;
    @FXML
    TableColumn<Recipe, Image> iconColumn;
    @FXML
    TableColumn<Recipe, String> nameColumn;

    // TODO: change to display stars
    @FXML
    TableColumn<Recipe, Double> ratingColumn;
    @FXML
    ListView filtersList;
    @FXML
    TextField nameFilterTextField;
    @FXML
    Button nameFilterButton;

    @FXML
    public void initialize() {
        currentFilter.addFilter(new NameFilter(""));
        // TODO: Consider changing return types from Recipe to StringProperty, etc.
        this.recipeTable.setItems(FXCollections.observableList((List)recipeProvider.getObjects(currentFilter)));
        this.iconColumn.setCellFactory(param -> {
            final ImageView imageView = new ImageView();
            imageView.setFitWidth(fixedCellSize);
            imageView.setFitHeight(fixedCellSize);
            TableCell<Recipe, Image> cell = new TableCell<>() {
                @Override
                public void updateItem(Image image, boolean empty) {
                    if(!empty) {
                        imageView.setImage(image);
                    }
                }
            };
            cell.setGraphic(imageView);
            return cell;
        });
        this.iconColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        this.nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        this.ratingColumn.setCellValueFactory(cellData -> cellData.getValue().getAverageRating().asObject());

        filtersList.setItems(FXCollections.observableList(currentFilter.getFilters()));
        filtersList.setCellFactory(param -> new ListCell<Filter>() {
            private Button removeFilterButton;
            @Override
            public void updateItem(Filter filter, boolean empty) {
                super.updateItem(filter, empty);
                if(empty) {
                    setText(null);
                } else {
                    setText(filter.toString());
                    removeFilterButton = new Button("X");
                    removeFilterButton.addEventHandler(ActionEvent.ACTION, e -> {
                        currentFilter.removeFilter(filter);
                        filtersList.setItems(FXCollections.observableList(currentFilter.getFilters()));
                    });
                    this.getChildren().add(removeFilterButton);
                }
            }
        });

        nameFilterButton.addEventHandler(ActionEvent.ACTION, e -> {
            currentFilter.addFilter(new NameFilter(nameFilterTextField.getText()));
            recipeTable.setItems(FXCollections.observableList((List) recipeProvider.getObjects(currentFilter)));
        });

    }
}
