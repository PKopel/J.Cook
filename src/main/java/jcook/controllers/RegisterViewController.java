package jcook.controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import jcook.filters.NameFilter;
import jcook.models.User;
import jcook.providers.UserProvider;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.LinkedList;

public class RegisterViewController {
    private final int minLength = 5;
    private final int maxLength = 20;

    private final int closeWindowDelay = 5;

    private final String usernameWrong = "Username has to be at least " + minLength + " character and at most " + maxLength + " character long and not contain any special characters";
    private final String usernameTaken = "This username is already in use";
    private final String wrongPassword = "Password has to be at least " + minLength + " characters and at most " + maxLength + " character long." +
            "It has to contain at least one capital letter, at least one digit and no special characters";
    private final String passwordsDontMatch = "Passwords don't match";
    private final String registerSuccess = "Successfully registered. You can now login to the app. This window will automatically close after " + closeWindowDelay +
            " seconds";

    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    PasswordField repeatPasswordField;
    @FXML
    Label registerInfo;
    @FXML
    Button registerButton;

    @FXML
    ImageView currentImageView;
    @FXML
    Button imageButton;
    @FXML
    Pane mainPane;
    private byte[] imageBytes;

    public void initialize() {

        setMaxLengthListeners();

        // Init default image
        try {
            String defaultImagePath = "/images/j_cook.jpeg";
            imageBytes = getClass().getResourceAsStream(defaultImagePath).readAllBytes();
            currentImageView.setImage(new Image(new ByteArrayInputStream(imageBytes)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imageButton.addEventHandler(ActionEvent.ACTION, e -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("images (*.jpg,*.jpeg, *.png)",
                    "*.jpg", "*.jpeg", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                imageBytes = new byte[(int) file.length()];
                try {
                    FileInputStream input = new FileInputStream(file);
                    int numberRead = input.read(imageBytes);
                    if (numberRead > 0)
                        currentImageView.setImage(new Image(new ByteArrayInputStream(imageBytes)));
                    input.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Register register handler
        registerButton.addEventHandler(ActionEvent.ACTION, e -> {

            // Check if username is legal
            String username = usernameField.getText();
            if (username.length() < minLength || username.length() > maxLength || containsSpecialCharacters(username)) {
                registerInfo.setText(usernameWrong);
                registerInfo.setTextFill(Color.RED);
                return;
            }
            // Check if there is not user with given username
            if (UserProvider.getInstance().getObjects(new NameFilter(username)).size() > 0) {
                registerInfo.setText(usernameTaken);
                registerInfo.setTextFill(Color.RED);
                return;
            }

            // Check if password is legal
            String password = passwordField.getText();
            if (!passwordCorrect(password)) {
                registerInfo.setText(wrongPassword);
                registerInfo.setTextFill(Color.RED);
                return;
            }
            // Check if passwords match
            String repeatedPassword = repeatPasswordField.getText();
            if (!repeatedPassword.equals(password)) {
                registerInfo.setText(passwordsDontMatch);
                registerInfo.setTextFill(Color.RED);
                return;
            }

            //hash password
            SecureRandom random = new SecureRandom();
            byte[] hash, salt = new byte[16];
            random.nextBytes(salt);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            try {
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                hash = keyFactory.generateSecret(spec).getEncoded();
            } catch (InvalidKeySpecException | NoSuchAlgorithmException invalidKeySpecException) {
                invalidKeySpecException.printStackTrace();
                return;
            }
            //create new user entry in base
            UserProvider.getInstance().addObject(new User(username, new LinkedList<>(), new LinkedList<>(), salt, hash,
                    imageBytes));
            registerInfo.setText(registerSuccess);
            registerInfo.setTextFill(Color.GREEN);
            PauseTransition delay = new PauseTransition(Duration.seconds(closeWindowDelay));
            delay.setOnFinished(event -> ((Stage) mainPane.getScene().getWindow()).close());
            delay.play();
        });
    }

    private boolean containsSpecialCharacters(String text) {
        for (char c : text.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean passwordCorrect(String password) {
        if (password.length() < minLength || password.length() > maxLength) {
            return false;
        }
        boolean hasCapital = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                if (Character.isUpperCase(c)) {
                    hasCapital = true;
                }
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                return false;
            }
        }
        return (hasCapital && hasDigit);
    }

    private void setMaxLengthListeners() {
        setMaxLengthListener(usernameField);
        setMaxLengthListener(passwordField);
        setMaxLengthListener(repeatPasswordField);
    }

    private void setMaxLengthListener(TextField textField) {
        textField.textProperty().addListener((observableValue, s, t1) -> {
            if (textField.getText().length() > maxLength) {
                textField.setText(textField.getText().substring(0, maxLength));
            }
        });
    }
}
