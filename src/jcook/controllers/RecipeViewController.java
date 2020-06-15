package jcook.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jcook.filters.IdFilter;
import jcook.models.Ingredient;
import jcook.models.Rating;
import jcook.models.Recipe;
import jcook.models.User;
import jcook.providers.UserProvider;
import java.util.List;


public class RecipeViewController {
    @FXML
    Label recipeNameLabel;
    @FXML
    ListView<Ingredient> ingredientList;
    @FXML
    ImageView recipeImage;
    @FXML
    ScrollPane descriptionScroll;
    @FXML
    Text recipeDescription;
    @FXML
    GridPane mainPane;

    @FXML
    TextArea commentArea;
    @FXML
    Button commentButton;
    @FXML
    HBox commentControls;
    @FXML
    ListView<Rating> commentList;


    public RecipeViewController() {
    }

    public void setRecipe(Recipe recipe) {
        recipeNameLabel.setText(recipe.getName());
        ingredientList.setItems(FXCollections.observableList(recipe.getIngredients()));
        if (recipe.getRenderedImage() != null)
            recipeImage.setImage(recipe.getRenderedImage());
        recipeDescription.setText(recipe.getDescription());
        commentList.setItems(FXCollections.observableList((List)recipe.getRatings()));
    }

    public void initialize() {
        initIngredientList();
        initCommentAdding();
        initCommentList();
    }

    private void initIngredientList() {
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

    private void initCommentAdding() {
        // TODO: implement
    }

    private void initCommentList() {
        commentList.setCellFactory(param -> {
            final VBox vbox = new VBox();
            final ImageView image = new ImageView();
            final ScrollPane scrollPane = new ScrollPane();
            final Text commentContent = new Text();
            final HBox authorAndDate = new HBox();
            final Label author = new Label();
            final Label date = new Label();
            image.setFitWidth(50);
            image.setFitHeight(50);
            commentContent.setWrappingWidth(1024);
            scrollPane.setContent(commentContent);
            authorAndDate.getChildren().addAll(author, date);
            authorAndDate.setAlignment(Pos.CENTER);
            authorAndDate.setSpacing(10);
            vbox.getChildren().addAll(image, scrollPane, authorAndDate);
            return new ListCell<>() {
                @Override
                public void updateItem(Rating rating, boolean empty) {
                    super.updateItem(rating, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        image.setImage(new Image(getClass().getResourceAsStream("/images/star.png")));
                        commentContent.setText(rating.getDescription());

                        List<User> users = UserProvider.getInstance().getObjects(new IdFilter(rating.getAuthor()));
                        author.setText("By: " + (users.size() == 0 ? "Unkown user" : users.get(0)));
                        date.setText("Added: " + rating.getDate());
                        setGraphic(vbox);
                    }
                }
            };
        });
    }
}
