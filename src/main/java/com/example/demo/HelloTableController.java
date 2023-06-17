package com.example.demo;

import db.DatabaseManager;
import instruments.StoreProductManager;
import instruments.TableManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import models.Employee;
import models.Store_Product;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloTableController {
    @FXML
    TableView<Employee> tableView;
    TableManager<Employee> tableManager;
    StoreProductManager storeProductManager;
    boolean x = false;
    public void initialize() throws SQLException, ClassNotFoundException {
        tableManager = new TableManager<>(DatabaseManager.getDatabaseManager(), tableView, "Employee");
    }

    public void refreshTable() throws SQLException {
        ResultSet r = DatabaseManager.getDatabaseManager().selectRecords("Employee");
        r.next();
        if(!x) tableManager.updateRow(new String[]{r.getString(1)},
                new Employee("AAAAAAAAAAAA", "Стефаненко", "Стефан", "Стефанович",
                        "Касир", 10000.0, new Date(120,1,1), new Date(120, 1, 1),
                        "091201234", "Івано-Франківськ", "Франка", "02102"));
        x = !x;
        //x = !x;
        //if(x)
        //tableManager.addSubstringFilter("category_name", "С");
        //else tableManager.removeAllFilters();
    }
}
