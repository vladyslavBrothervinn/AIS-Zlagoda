package com.example.demo;

import db.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setOnCloseRequest(windowEvent -> {
            try {
                DatabaseManager.getDatabaseManager().statement.close();
                DatabaseManager.getDatabaseManager().connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}