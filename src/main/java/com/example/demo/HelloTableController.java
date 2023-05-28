package com.example.demo;

import db.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloTableController {
    @FXML
    TableView<Category> tableView;
    public void initialize() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getDatabaseManager();
        TableColumn<Category, Integer> tableColumn1 = new TableColumn<>("Number");
        tableColumn1.setCellValueFactory(new PropertyValueFactory<>("categoryNumber"));
        TableColumn<Category, String> tableColumn2 = new TableColumn<>("Name");
        tableColumn2.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        tableView.getColumns().add(tableColumn1);
        tableView.getColumns().add(tableColumn2);
        ObservableList<Category> observableList = FXCollections.observableArrayList();
        ResultSet resultSet = databaseManager.selectRecords("Category");
        while(resultSet.next()) observableList.add(new Category(resultSet.getInt("category_number"),
                resultSet.getString("category_name")));
        tableView.setItems(observableList);
    }
}
