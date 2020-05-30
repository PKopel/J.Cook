package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Button button = new Button();
        button.setText("Start exploring our recipes");
        button.setPrefSize(400, 100);
        button.setFont(new Font(28));
        primaryStage.setResizable(false);
        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(layout, 800, 675));
        primaryStage.show();
    }
}
