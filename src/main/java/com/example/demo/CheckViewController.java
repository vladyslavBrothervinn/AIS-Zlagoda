package com.example.demo;

import db.DatabaseManager;
import instruments.SQLBasedTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import models.Check;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CheckViewController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    Check check;

    @FXML
    Button returnButton;
    @FXML
    TableView tableView;
    @FXML
    Label label_checkNum;
    @FXML
    Label Surname;
    @FXML
    Label Date;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        check = HelloController2.selectedCheck;
        SQLBasedTable sqlBasedTable = new SQLBasedTable(tableView);
        String sql = "SELECT UPC, product_name, product_number, Sale.selling_price AS price"+
                " FROM ((Sale INNER JOIN Store_Product ON Sale.UPC = Store_Product.UPC)"+
                " INNER JOIN Product ON Product.id_product = Store_Product.id_product)"+
                " WHERE check_number = '" + check.getCheckNumber()+"'";
        try {
            sqlBasedTable.runSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        label_checkNum.setText(check.getCheckNumber());
        try {
            ResultSet resultSet = DatabaseManager.getDatabaseManager().statement.executeQuery("SELECT empl_surname FROM Employee" +
                    " WHERE id_employee = '"+check.getIdEmployee()+"'");
            if(resultSet.next()) Surname.setText(resultSet.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Date.setText(check.getPrintDate().toString());

    }

    @FXML
    public void comeback(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
