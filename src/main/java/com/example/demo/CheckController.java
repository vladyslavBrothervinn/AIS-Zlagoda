package com.example.demo;

import db.DatabaseManager;
import instruments.CheckSalesTableManager;
import instruments.TableManager;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.Check;
import models.Customer_Card;
import models.Product;
import models.Store_Product;
import org.controlsfx.control.textfield.TextFields;

import java.io.DataInputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class CheckController implements Initializable {

    @FXML
    TextField customersTextField;
    @FXML
    TableView<Store_Product> table1;
    @FXML
    TableView<Product> table2;
    @FXML
    TableView table3;
    TableManager<Store_Product> tableManager1;
    TableManager<Product> tableManager2;
    CheckSalesTableManager tableManager3;
    ObservableList<Customer_Card> customers;
    ObservableList<String> solutions;


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
        solutions = FXCollections.observableArrayList();
        try {
            customers = Customer_Card.getAllCustomers();
            for (int i = 0; i < customers.size(); i++) {
                solutions.add(customers.get(i).getCardNumber() + " " +
                        customers.get(i).getCustName() + " "
                        + customers.get(i).getCustSurname());
            }
            TextFields.bindAutoCompletion(customersTextField, solutions);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ObservableList selectedRow = table2.getSelectionModel().getSelectedItems();

        selectedRow.addListener((ListChangeListener) c -> {
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

        });
    }

    public void OnClick() {
        try {
            Customer_Card customer_card = customers.get(solutions.indexOf(customersTextField.getText()));
            System.out.println(customer_card.getCustName());
            Check check = new Check("000000", "CON8443IOP",
                    customer_card.getCardNumber(), Date.valueOf(LocalDate.now()), 0.0, 0.0);

        } catch (IndexOutOfBoundsException i) {
            System.out.println("Wrong ");

        }
    }
}
