package jcook.controllers;

import com.mongodb.client.model.Filters;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jcook.filters.CategoryFilter;
import jcook.filters.CombinedFilter;
import jcook.filters.Filter;
import jcook.filters.NameFilter;
import jcook.models.Category;
import jcook.models.Recipe;
import jcook.providers.RecipeProvider;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecipeListController {

    private final CombinedFilter currentFilter = new CombinedFilter(Filters::and);
    private final RecipeProvider recipeProvider = RecipeProvider.getInstance();
    private final int fixedCellSize = 50;
    private final int fixedFilterCellSize = 30;

    @FXML
    Pane header;
    @FXML
    TableView<Recipe> recipeTable;
    @FXML
    TableColumn<Recipe, Image> iconColumn;
    @FXML
    TableColumn<Recipe, String> nameColumn;

    // TODO: change to display stars
    @FXML
    TableColumn<Recipe, Void> ratingColumn;
    @FXML
    ListView<Filter> filtersList;
    @FXML
    VBox filterAddingList;

    private List<VBox> filterForms = new ArrayList<>();

    /*@FXML
    TextField nameFilterTextField;
    @FXML
    Button nameFilterButton;*/

    @FXML
    ComboBox userButtons;

    private StackPane mainPane;

    // TODO: Would be nice to have returned types as Observables

    @FXML
    public void initialize() {
        currentFilter.addFilter(new NameFilter(""));

        initRecipeTable();
        initFilterList();
        initFilterAddingList();
        initUserButtons();

        /*nameFilterButton.addEventHandler(ActionEvent.ACTION, e -> {
            currentFilter.addFilter(new NameFilter(nameFilterTextField.getText()));
            recipeTable.setItems(FXCollections.observableList(recipeProvider.getObjects(currentFilter)));
            filtersList.setItems(FXCollections.observableList(currentFilter.getFilters()));
        });
*/
    }

    private void initRecipeTable() {
        // TODO: Consider changing return types from Recipe to StringProperty, etc.
        this.recipeTable.setItems(FXCollections.observableList(recipeProvider.getObjects(currentFilter)));
        this.iconColumn.setCellFactory(param -> {
            final ImageView imageView = new ImageView();
            imageView.setFitWidth(fixedCellSize);
            imageView.setFitHeight(fixedCellSize);
            TableCell<Recipe, Image> cell = new TableCell<>() {
                @Override
                public void updateItem(Image image, boolean empty) {
                    if (!empty) {
                        imageView.setImage(image);
                    }
                }
            };
            cell.setGraphic(imageView);
            return cell;
        });
        this.iconColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        this.nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        this.ratingColumn.setCellFactory(param -> {
            final ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/star.png")));
            imageView.setFitHeight(fixedCellSize*4/5.0);
            imageView.setFitWidth(fixedCellSize*4/5.0);
            return new TableCell<>() {
                @Override
                public void updateItem(Void item, boolean empty) {
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(getTableRow().getItem().getAverageRating().toString());
                        setGraphic(imageView);
                    }
                }
            };
        });
    }

    private void initFilterList() {
        filtersList.setFixedCellSize(fixedFilterCellSize);
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
            return new ListCell<>() {
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
        });
    }

    private void initUserButtons() {
        List<Button> buttons = new ArrayList<>();
        Button openProfileButton = new Button("Open profile");
        Button logOutButton = new Button("Log out");
        buttons.add(openProfileButton);
        buttons.add(logOutButton);
        ObservableList<Button> obsButtons = FXCollections.observableList(buttons);
        userButtons.setItems(obsButtons);
    }

    private void initFilterAddingList() {
        /* Name filter */
        VBox nameFilter = new VBox();
        nameFilter.getStyleClass().add("filter");
        TextField nameField = new TextField();
        Button addNameFilterButton = new Button("Add filter");
        addNameFilterButton.addEventHandler(ActionEvent.ACTION, e -> {
            currentFilter.addFilter(new NameFilter(nameField.getText()));
            recipeTable.setItems(FXCollections.observableList(recipeProvider.getObjects(currentFilter)));
            filtersList.setItems(FXCollections.observableList(currentFilter.getFilters()));
        });
        nameFilter.getChildren().addAll(new Label("name"), nameField, addNameFilterButton);
        filterForms.add(nameFilter);

        VBox categoryFilter = new VBox();
        categoryFilter.getStyleClass().add("filter");
        ComboBox<Category> categoryBox = new ComboBox<>();
        categoryBox.getItems().setAll(Category.values());
        Button addCategoryFilterButton = new Button("Add filter");
        addCategoryFilterButton.addEventHandler(ActionEvent.ACTION, e -> {
            currentFilter.addFilter(new CategoryFilter(categoryBox.getSelectionModel().getSelectedItem()));
            recipeTable.setItems(FXCollections.observableList(recipeProvider.getObjects(currentFilter)));
            filtersList.setItems(FXCollections.observableList(currentFilter.getFilters()));
        });
        nameFilter.getChildren().addAll(new Label("name"), categoryBox, addCategoryFilterButton);
        filterForms.add(categoryFilter);

        filterAddingList.getChildren().addAll(filterForms);
    }

    public void setMainPane(StackPane mainPane) {
        this.mainPane = mainPane;
    }
}
