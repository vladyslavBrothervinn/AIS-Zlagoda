package models;

import db.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Customer_Card extends Table{
    private String cardNumber;
    private String custSurname;
    private String custName;
    private String custPatronymic;
    private String phoneNumber;
    private String city;
    private String street;
    private String zipCode;
    private Integer percent;
    public Customer_Card(String cardNumber, String custSurname, String custName,
                         String custPatronymic, String phoneNumber, String city,
                         String street, String zipCode, Integer percent){
        this.setCardNumber(cardNumber);
        this.setCustSurname(custSurname);
        this.setCustName(custName);
        this.setCustPatronymic(custPatronymic);
        this.setPhoneNumber(phoneNumber);
        this.setCity(city);
        this.setStreet(street);
        this.setZipCode(zipCode);
        this.setPercent(percent);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCustSurname() {
        return custSurname;
    }

    public void setCustSurname(String custSurname) {
        this.custSurname = custSurname;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustPatronymic() {
        return custPatronymic;
    }

    public void setCustPatronymic(String custPatronymic) {
        this.custPatronymic = custPatronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    @Override
    public String[] getFieldsValuesAsStringArray() {
        return new String[]{"'"+getCardNumber()+"'", "'"+getCustSurname()+"'",
                "'"+getCustName()+"'", "'"+getCustPatronymic()+"'", "'"+getPhoneNumber()+"'",
                "'"+getCity()+"'","'"+getStreet()+"'", "'"+getZipCode()+"'", getPercent().toString()};
    }
    @Override
    public String[] getKeyValues(){
        return new String[]{cardNumber};
    }

    public static ObservableList<Customer_Card> getAllCustomers() throws SQLException {
        ResultSet resultSet = DatabaseManager.getDatabaseManager().selectRecords("Customer_Card");
        ObservableList<Customer_Card> customers = FXCollections.observableArrayList();
        while (resultSet.next()) customers.add(new Customer_Card(resultSet.getString(1), resultSet.getString(2),
                resultSet.getString(3), resultSet.getString(4),
                resultSet.getString(5), resultSet.getString(6),
                resultSet.getString(7), resultSet.getString(8),
                resultSet.getInt(9)));
        return customers;
    }
}
