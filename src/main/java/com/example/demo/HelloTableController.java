package com.example.demo;

import db.DatabaseManager;
import instruments.TableManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import models.Category;

import java.sql.SQLException;

public class HelloTableController {
    @FXML
    TableView<Category> tableView;
    TableManager<Category> tableManager;
    public void initialize() throws SQLException, ClassNotFoundException {
        tableManager = new TableManager<>(DatabaseManager.getDatabaseManager(), tableView, "Store_Product");
    }
}
