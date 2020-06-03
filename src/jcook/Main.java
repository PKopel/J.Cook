package jcook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jcook.providers.RecipeProvider;
import jcook.providers.UserProvider;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginPane.fxml"));

        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.setTitle("J.Cook");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/j_cook.jpeg")));
        primaryStage.show();
    }


    public static void main(String[] args) {
        RecipeProvider.initialize("mongodb://127.0.0.1","JCookTest");
        UserProvider.initialize("mongodb://127.0.0.1","JCookTest");
        launch(args);
    }
}
