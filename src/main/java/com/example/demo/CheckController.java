package com.example.demo;

import db.DatabaseManager;
import instruments.TableManager;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import models.Employee;
import models.Product;
import models.Store_Product;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CheckController implements Initializable {

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
                //System.out.println("Selected Value" + selected.getEmplName());

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
}
