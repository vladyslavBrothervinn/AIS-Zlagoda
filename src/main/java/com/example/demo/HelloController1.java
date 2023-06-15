package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController1 {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToScene1(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //stage = (Stage) ((Node)e.getSourse()).getScene().getWindow();
    }

    public void switchToScene2(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("sampleScene2.fxml"));
    }
}