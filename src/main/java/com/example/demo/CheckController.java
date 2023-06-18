package com.example.demo;

import db.DatabaseManager;
import instruments.TableManager;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Product;
import models.Store_Product;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CheckController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TableView<Store_Product> table1;
    @FXML
    TableView<Product> table2;
    TableManager<Store_Product> tableManager1;
    TableManager<Product> tableManager2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tableManager1 = new TableManager<>(DatabaseManager.getDatabaseManager(), table1, "Store_Product");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            tableManager2 = new TableManager<>(DatabaseManager.getDatabaseManager(), table2, "Product");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ObservableList selectedRow = table2.getSelectionModel().getSelectedItems();

        selectedRow.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                Product selected = (Product) selectedRow.get(0);

                try {
                    tableManager1.removeAllFilters();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    tableManager1.addKeyFilter("id_product", selected.getIdProduct().toString());
                    tableManager1.renewTable();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
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
    private void cancelReceipt(ActionEvent event) {
        Stage smallStage = new Stage();
        smallStage.initOwner(stage);
        smallStage.initModality(Modality.APPLICATION_MODAL);
        smallStage.initStyle(StageStyle.UTILITY);

        Label messageLabel = new Label("Натисніть 'Так' для відміни генерації чеку:");
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
