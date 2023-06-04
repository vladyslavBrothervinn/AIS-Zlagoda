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
    boolean x = false;
    public void initialize() throws SQLException, ClassNotFoundException {
        tableManager = new TableManager<>(DatabaseManager.getDatabaseManager(), tableView, "Category");
    }

    public void refreshTable() throws SQLException {
        tableManager.updateRow(new String[]{"4"}, new Category(4, "Овоч"+(x?"і":".")));
        x = !x;
    }
}
