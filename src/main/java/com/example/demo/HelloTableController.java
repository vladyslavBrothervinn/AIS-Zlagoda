package com.example.demo;

import db.DatabaseManager;
import instruments.StoreProductManager;
import instruments.TableManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import models.Store_Product;

import java.sql.SQLException;

public class HelloTableController {
    @FXML
    TableView<Store_Product> tableView;
    TableManager<Store_Product> tableManager;
    StoreProductManager storeProductManager;
    boolean x = false;
    public void initialize() throws SQLException, ClassNotFoundException {
        tableManager = new TableManager<>(DatabaseManager.getDatabaseManager(), tableView, "Store_Product");
        storeProductManager = new StoreProductManager(tableManager);
    }

    public void refreshTable() throws SQLException {
        if(!x) tableManager.insertRow(new Store_Product("1",
                "aa", 2, 1000.0, 100, true));

        x = !x;
        //x = !x;
        //if(x)
        //tableManager.addSubstringFilter("category_name", "С");
        //else tableManager.removeAllFilters();
    }
}
