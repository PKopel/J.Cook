package jcook.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/RecipeListPane.fxml"));
                Pane recipeListPane = loader.load();
                mainPane.getChildren().clear();
                mainPane.getChildren().add(recipeListPane);
                RecipeListController controller = loader.getController();
                controller.setMainPane(mainPane);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
    }

}
