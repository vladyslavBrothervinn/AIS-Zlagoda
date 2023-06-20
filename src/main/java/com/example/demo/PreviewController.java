package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.example.demo.HelloController2.imageView;


public class PreviewController implements Initializable {
    public ImageView imageView;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button saveButton, closeWindow;
    @FXML
    ScrollPane savePane;

    @FXML
    Label tableName, todayDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //tableName, todayDate

        tableName.setText(HelloController2.choiceBoxValue);
        LocalDate currentDate = LocalDate.now();
        todayDate.setText(currentDate.toString());

        Image image = new Image("C:\\Users\\Cyberpower\\IdeaProjects\\demo-login-window-forDbTesting\\src\\main\\resources\\com\\example\\demo\\image.png");
        imageView.setImage(image);

        savePane.setContent(imageView);

        saveButton.setOnAction(e -> {
            System.out.println("saved!");
        });

    }
    public void switchToMainMenu(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void cancelZvit(ActionEvent event) {
        Stage smallStage = new Stage();
        smallStage.initOwner(stage);
        smallStage.initModality(Modality.APPLICATION_MODAL);
        smallStage.initStyle(StageStyle.UTILITY);

        Label messageLabel = new Label("Натисніть 'Так' для відміни генерації звіту:");
        Button confirmButton = new Button("Так");
        confirmButton.setOnAction(e -> {
            smallStage.close();
            try {
                switchToMainMenu(event);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button cancelButton = new Button("Ні");
        cancelButton.setOnAction(e -> smallStage.close());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, messageLabel);
        gridPane.addRow(1, confirmButton, cancelButton);

        Scene scene = new Scene(gridPane);
        smallStage.setScene(scene);
        smallStage.setTitle("Підтвердження");
        smallStage.showAndWait();
    }
}
