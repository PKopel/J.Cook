package jcook.sample;

import com.mongodb.client.model.Filters;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jcook.filters.CombinedFilter;
import jcook.filters.NameFilter;
import jcook.models.Recipe;
import jcook.providers.RecipeProvider;

import java.util.Collection;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginPane.fxml"));

        primaryStage.setScene(new Scene(root, 800, 615));
        primaryStage.setTitle("J.Cook");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/j_cook.jpeg")));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
