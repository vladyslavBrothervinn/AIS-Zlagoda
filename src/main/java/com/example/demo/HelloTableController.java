package com.example.demo;

import instruments.CheckSalesTableManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.sql.SQLException;

public class HelloTableController {
    @FXML
    TableView tableView;
    CheckSalesTableManager checkSalesTableManager;

    boolean x = false;
    public void initialize() throws SQLException {
        checkSalesTableManager = new CheckSalesTableManager(tableView, "241455");
    }

    public void refreshTable() throws SQLException {
        checkSalesTableManager.renewTable();
        x = !x;
        //x = !x;
        //if(x)
        //tableManager.addSubstringFilter("category_name", "С");
        //else tableManager.removeAllFilters();
    }
}
