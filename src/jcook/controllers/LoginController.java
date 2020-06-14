package jcook.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    Button loginButton;

    @FXML
    StackPane mainPane;

    public LoginController() { }

    @FXML
    public void initialize() {
        loginButton.addEventHandler(ActionEvent.ACTION, e -> {
            try {
                ((Stage) mainPane.getScene().getWindow()).close();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/RecipeListPane.fxml"));

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

}
