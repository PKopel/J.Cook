package jcook.controllers;

import com.mongodb.client.model.Filters;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jcook.filters.*;
import jcook.authentication.LoginManager;
import jcook.models.Category;
import jcook.models.Recipe;
import jcook.providers.RecipeProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecipeListController {

    private final CombinedFilter currentFilter = new CombinedFilter(Filters::and);
    private final RecipeProvider recipeProvider = RecipeProvider.getInstance();
    private final int fixedCellSize = 50;
    private final int fixedFilterCellSize = 30;
    private final List<VBox> filterForms = new ArrayList<>();
    @FXML
    TableView<Recipe> recipeTable;
    @FXML
    TableColumn<Recipe, Image> iconColumn;
    @FXML
    TableColumn<Recipe, String> nameColumn;
    @FXML
    TableColumn<Recipe, Void> ratingColumn;
    @FXML
    ListView<Filter> filtersList;
    @FXML
    VBox filterAddingList;
    @FXML
    ImageView userImage;
    @FXML
    MenuButton userButtons;
    @FXML
    MenuItem openProfile;
    @FXML
    MenuItem logOut;

    @FXML
    Button recipeFormButton;
    @FXML
    GridPane recipeListContentPane;

    @FXML
    public void initialize() throws IOException {
        initRecipeTable();
        initFilterList();
        initFilterAddingList();
        initHeader();
    }

    private void initRecipeTable() {
        this.recipeTable.setItems(FXCollections.observableList(recipeProvider.getObjects(currentFilter)));

        // On-click handler
        this.recipeTable.setRowFactory(param -> {
            TableRow<Recipe> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RecipeView.fxml"));
                        GridPane recipeViewPane = loader.load();
                        RecipeViewController recipeViewController = loader.getController();
                        recipeViewController.setRecipe(row.getItem());
                        recipeViewController.setRecipeListController(this);
                        final Stage recipeView = new Stage();
                        recipeView.initModality(Modality.APPLICATION_MODAL);
                        Scene recipeViewScene = new Scene(recipeViewPane, 1024, 768);
                        recipeView.setScene(recipeViewScene);
                        recipeView.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            return row;
        });

        // Columns data factories
        this.iconColumn.setCellFactory(param -> {
            final ImageView imageView = new ImageView();
            imageView.setFitWidth(fixedCellSize);
            imageView.setFitHeight(fixedCellSize);
            TableCell<Recipe, Image> cell = new TableCell<>() {
                @Override
                public void updateItem(Image image, boolean empty) {
                    if(empty) {
                        setGraphic(null);
                    } else {
                        imageView.setImage(image);
                        setGraphic(imageView);
                    }
                }
            };
            return cell;
        });
        this.iconColumn.setCellValueFactory(new PropertyValueFactory<>("renderedImage"));
        this.nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));

        this.ratingColumn.setCellFactory(param -> {
            final ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/star.png")));
            imageView.setFitHeight(fixedCellSize);
            imageView.setFitWidth(fixedCellSize);
            return new TableCell<>() {
                @Override
                public void updateItem(Void item, boolean empty) {
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        if (getTableRow().getItem() != null)
                            setText(
                                    getTableRow()
                                            .getItem()
                                            .avgRating()
                                            .toString());
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
            final Button removeFilterButton = new Button("X");
            final Label label = new Label("Empty");
            final Filter[] f = new Filter[1];

            removeFilterButton.addEventHandler(ActionEvent.ACTION, e -> {
                currentFilter.removeFilter(f[0]);
                refresh();
            });

            BorderPane.setAlignment(label, Pos.CENTER_LEFT);
            BorderPane.setAlignment(removeFilterButton, Pos.CENTER_RIGHT);
            final BorderPane borderPane = new BorderPane(null, null, removeFilterButton, null, label);

            return new ListCell<>() {
                @Override
                public void updateItem(Filter filter, boolean empty) {
                    super.updateItem(filter, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        label.setText(filter.toString());
                        f[0] = filter;
                        setGraphic(borderPane);
                    }
                }
            };
        });
    }

    private void initHeader() throws IOException {
        if (LoginManager.getInstance().offlineSession()) {
            recipeFormButton.setVisible(false);
        }

        recipeFormButton.addEventHandler(ActionEvent.ACTION, e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RecipeForm.fxml"));
                GridPane recipeFormPane = loader.load();
                ((RecipeFormController)loader.getController()).setRecipeListController(this);
                final Stage recipeForm = new Stage();
                recipeForm.initModality(Modality.APPLICATION_MODAL);
                Scene recipeFormScene = new Scene(recipeFormPane, 600, 700);
                recipeForm.setScene(recipeFormScene);
                recipeForm.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        userImage.setImage(LoginManager.getInstance().getLoggedUser().getRenderedImage());
        userButtons.setText(LoginManager.getInstance().getLoggedUser().getName());
        openProfile.setOnAction(e -> System.out.println("Opening profile"));
        logOut.setOnAction(e -> {
            LoginManager.getInstance().logOut();
            ((Stage) recipeListContentPane.getScene().getWindow()).close();


            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginPane.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 1024, 768));
                stage.setTitle("J.Cook");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/j_cook.jpeg")));
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void initFilterAddingList() {
        /* Name filter */
        VBox nameFilter = new VBox();
        nameFilter.getStyleClass().add("filter");
        TextField nameField = new TextField();
        Button addNameFilterButton = new Button("Add filter");
        addNameFilterButton.addEventHandler(ActionEvent.ACTION, e -> {
            currentFilter.addFilter(new NameFilter(nameField.getText()));
            refresh();
        });
        nameFilter.getChildren().addAll(new Label("Name"), nameField, addNameFilterButton);
        filterForms.add(nameFilter);

        /* Category filter */
        VBox categoryFilter = new VBox();
        categoryFilter.getStyleClass().add("filter");
        ComboBox<Category> categoryBox = new ComboBox<>();
        categoryBox.getItems().setAll(Category.values());
        Button addCategoryFilterButton = new Button("Add filter");
        addCategoryFilterButton.addEventHandler(ActionEvent.ACTION, e -> {
            currentFilter.addFilter(new CategoryFilter(categoryBox.getSelectionModel().getSelectedItem()));
            refresh();
        });
        categoryFilter.getChildren().addAll(new Label("Category"), categoryBox, addCategoryFilterButton);
        categoryBox.setMaxWidth(5000.0);
        filterForms.add(categoryFilter);

        /* Tag filter */
        VBox tagFilter = new VBox();
        tagFilter.getStyleClass().add("filter");
        TextField tagField = new TextField();
        Button addTagFilterButton = new Button("Add filter");
        addTagFilterButton.addEventHandler(ActionEvent.ACTION, e -> {
            currentFilter.addFilter(new TagFilter(tagField.getText()));
            refresh();
        });
        tagFilter.getChildren().addAll(new Label("Tag"), tagField, addTagFilterButton);
        filterForms.add(tagFilter);


        filterAddingList.getChildren().addAll(filterForms);
    }

    public void refresh() {
        recipeTable.setItems(FXCollections.observableList(recipeProvider.getObjects(currentFilter)));
        filtersList.setItems(FXCollections.observableList(currentFilter.getFilters()));
    }
}
