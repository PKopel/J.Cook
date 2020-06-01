module JCook {
    requires javafx.fxml;
    requires javafx.controls;
    requires mongo.java.driver;

    opens jcook.controllers;
    opens jcook.sample;

    exports jcook.models;
}
