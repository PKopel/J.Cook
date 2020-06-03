package jcook.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML
    StackPane mainPane;
    @FXML
    Button button;

    public MainController() { }

    @FXML
    public void initialize() {
        button.addEventHandler(ActionEvent.ACTION, e -> {
            if(mainPane == null) {
                System.out.println("WTF");
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginPane.fxml"));
                Pane loginPane = loader.load();
                mainPane.getChildren().clear();
                mainPane.getChildren().add(loginPane);
                LoginController controller = loader.getController();
                controller.setMainPane(mainPane);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
