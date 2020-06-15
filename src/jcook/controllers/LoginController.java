package jcook.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import jcook.loginManager.LoginManager;

import java.io.IOException;

public class LoginController {
    @FXML
    Button loginButton;
    @FXML
    Button registerButton;
    @FXML
    Button joinOfflineButton;

    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;

    @FXML
    StackPane mainPane;

    public LoginController() { }

    @FXML
    public void initialize() {
        loginButton.addEventHandler(ActionEvent.ACTION, e -> {
            if(LoginManager.logIn(loginField.getText(), passwordField.getText())) {
                openApp();
            }
        });
        joinOfflineButton.addEventHandler(ActionEvent.ACTION, e -> {
            if(LoginManager.joinOffline()) {
                openApp();
            }
        });
        registerButton.addEventHandler(ActionEvent.ACTION, e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/RegisterView.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 600, 400));
                stage.setTitle("Register to J.Cook");
                stage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void openApp() {
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
    }

}
