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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    ListView<Filter> filtersList;
    @FXML
    TextField nameFilterTextField;
    @FXML
    Button nameFilterButton;

    // TODO: Would be nice to have returned types as Observables

    @FXML
    public void initialize() {
        currentFilter.addFilter(new NameFilter(""));
        // TODO: Consider changing return types from Recipe to StringProperty, etc.
        this.recipeTable.setItems(FXCollections.observableList(recipeProvider.getObjects(currentFilter)));
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
        filtersList.setCellFactory(param -> {
            final HBox hbox = new HBox();
            final Button removeFilterButton = new Button("X");
            final Label label = new Label("Empty");
            final Filter[] f = new Filter[1];

            removeFilterButton.addEventHandler(ActionEvent.ACTION, e -> {
                currentFilter.removeFilter(f[0]);
                filtersList.setItems(FXCollections.observableList(currentFilter.getFilters()));
                recipeTable.setItems(FXCollections.observableList(recipeProvider.getObjects(currentFilter)));
            });

            hbox.getChildren().addAll(label, removeFilterButton);
            ListCell<Filter> cell = new ListCell<>() {
                @Override
                public void updateItem(Filter filter, boolean empty) {
                    super.updateItem(filter, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        label.setText(filter.toString());
                        f[0] = filter;
                        setGraphic(hbox);
                    }
                }
            };
            return cell;
        });

        nameFilterButton.addEventHandler(ActionEvent.ACTION, e -> {
            currentFilter.addFilter(new NameFilter(nameFilterTextField.getText()));
            recipeTable.setItems(FXCollections.observableList(recipeProvider.getObjects(currentFilter)));
            filtersList.setItems(FXCollections.observableList(currentFilter.getFilters()));
        });

    }
}
