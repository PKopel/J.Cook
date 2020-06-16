package jcook.controllers;

import com.mongodb.client.model.Filters;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import jcook.filters.Filter;
import jcook.filters.IdFilter;
import jcook.authentication.LoginManager;
import jcook.models.Ingredient;
import jcook.models.Rating;
import jcook.models.Recipe;
import jcook.models.User;
import jcook.providers.RecipeProvider;
import jcook.providers.UserProvider;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class RecipeViewController {
    @FXML
    Label recipeNameLabel;
    @FXML
    ListView<Ingredient> ingredientList;
    @FXML
    ImageView recipeImage;
    @FXML
    Button deleteButton;
    @FXML
    Button updateButton;

    @FXML
    ScrollPane descriptionScroll;
    @FXML
    Text recipeDescription;
    @FXML
    GridPane mainPane;

    @FXML
    VBox addCommentBox;
    @FXML
    TextArea commentArea;
    @FXML
    ChoiceBox<Integer> ratingBox;
    @FXML
    Button commentButton;
    @FXML
    HBox commentControls;
    @FXML
    ListView<Rating> commentList;

    private Recipe recipe;
    private final LoginManager loginManager = LoginManager.getInstance();
    private final User currentUser = loginManager.getLoggedUser();
    private User owner;

    public RecipeViewController() throws IOException {
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;

        owner = UserProvider.getInstance().getObjects(new Filter() {
                                                          @Override
                                                          public Bson getQuery() {
                                                              return Filters.eq("uploaded_recipes", recipe.getId());
                                                          }
                                                      }).get(0);
        recipeNameLabel.setText(recipe.getName() + " (by "+ owner.getName() + ")");
        ingredientList.setItems(FXCollections.observableList(recipe.getIngredients()));
        if (recipe.getRenderedImage() != null)
            recipeImage.setImage(recipe.getRenderedImage());
        recipeDescription.setText(recipe.getDescription());
        refresh();
        initOwnerButtons();
    }

    public void initialize() {
        initIngredientList();
        initCommentAdding();
        initCommentList();
    }

    private void initOwnerButtons() {
        if(currentUser.getId().equals(owner.getId())) {
            deleteButton.addEventHandler(ActionEvent.ACTION, e -> {
                RecipeProvider.getInstance().deleteObjects(new IdFilter(recipe.getId()));
                ((Stage) mainPane.getScene().getWindow()).close();
            });
            updateButton.addEventHandler(ActionEvent.ACTION, e -> {
                // TODO: implement
            });
        } else {
            deleteButton.setVisible(false);
            updateButton.setVisible(false);
        }
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
        if(loginManager.offlineSession()) {
            addCommentBox.getChildren().clear();
            Label label = new Label("You have to be logged in to add comments");
            label.setAlignment(Pos.CENTER);
            label.setPrefWidth(commentList.getPrefWidth());
            label.setTextAlignment(TextAlignment.CENTER);
            label.setTextFill(Color.INDIANRED);
            mainPane.getChildren().remove(addCommentBox);
            mainPane.add(label, 0, 3);
        } else {
            ratingBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
            ratingBox.setValue(5);
            commentButton.addEventHandler(ActionEvent.ACTION, e -> {
                Rating comment = new Rating(ratingBox.getValue(),
                        commentArea.getText(),
                        new SimpleDateFormat("dd/MM/yyyy").format(new Date()),
                        currentUser.getId());
                UserProvider.getInstance().updateObject(currentUser, recipe.getId(), "rated_recipes");
                RecipeProvider.getInstance().updateObject(recipe, comment, "ratings");
                refresh();
            });
        }
    }

    private void initCommentList() {
        commentList.setCellFactory(param -> {
            final VBox vbox = new VBox();
            final HBox starsBox = new HBox();
            final ScrollPane scrollPane = new ScrollPane();
            final Text commentContent = new Text();
            final HBox authorAndDate = new HBox();
            final Label author = new Label();
            final Label date = new Label();
            commentContent.setWrappingWidth(commentList.getPrefWidth());
            scrollPane.setContent(commentContent);
            authorAndDate.getChildren().addAll(author, date);
            authorAndDate.setAlignment(Pos.CENTER);
            authorAndDate.setSpacing(25);
            vbox.getChildren().addAll(starsBox, scrollPane, authorAndDate);
            return new ListCell<>() {
                @Override
                public void updateItem(Rating rating, boolean empty) {
                    super.updateItem(rating, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Image image = new Image(getClass().getResourceAsStream("/images/star.png"));
                        for(int i = 0; i < rating.getStars(); i++) {
                            ImageView star = new ImageView(image);
                            star.setFitWidth(30);
                            star.setFitHeight(30);
                            starsBox.getChildren().add(star);
                        }
                        commentContent.setText(rating.getDescription());

                        List<User> users = UserProvider.getInstance().getObjects(new IdFilter(rating.getAuthor()));
                        author.setText("By: " + (users.size() == 0 ? "Unknown user" : users.get(0).getName()));
                        date.setText("Added: " + rating.getDate());
                        setGraphic(vbox);
                    }
                }
            };
        });
    }

    private void refresh() {
        recipe = RecipeProvider.getInstance().getObjects(new IdFilter(recipe.getId())).get(0);
        commentList.setItems(FXCollections.observableList(recipe.getRatings()));
        if(recipe.getRatings().stream().anyMatch(o -> o.getAuthor().equals(currentUser.getId()))) {
            addCommentBox.getChildren().clear();
            Label label = new Label("You have already rated this recipe");
            label.setAlignment(Pos.CENTER);
            label.setPrefWidth(commentList.getPrefWidth());
            label.setTextFill(Color.GREEN);
            mainPane.getChildren().remove(addCommentBox);
            mainPane.add(label, 0, 3);
        }
    }
}
