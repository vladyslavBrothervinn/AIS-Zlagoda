package com.example.demo;

import db.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Objects;

public class HelloController1 {
    @FXML
    PasswordField Field_password;
    @FXML
    TextField Field_login;

    private Stage stage;
    private Scene scene;
    private Parent root;
    public static boolean isCashier;

    public void switchToScene2(ActionEvent e) throws IOException {

        //boolean log = loginCheck();
        boolean log = true;
        if (log) {
            //isCashier = managerCheck(Field_login.getText());
            isCashier = true;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Авторизація успішна!");
            alert.showAndWait();

            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка авторизації");
            alert.setHeaderText(null);
            alert.setContentText("Неправильне ім'я користувача або пароль!");
            alert.showAndWait();
            Field_login.clear();
            Field_password.clear();
            Field_login.requestFocus();
        }
    }

    public boolean loginCheck() {
        HashMap<String, String> dictionary;
        try {
            DatabaseManager databaseManager = new DatabaseManager("jdbc:ucanaccess://C:\\Users\\Cyberpower\\Downloads\\Zlagoda.accdb;COLUMNORDER=DISPLAY");
            Statement statement = databaseManager.connection.createStatement();

            String query1 = "SELECT * FROM LoginNPasswords";
            ResultSet result = statement.executeQuery(query1);

            dictionary = new HashMap<>();

            while (result.next()) {
                String employee_id = result.getString("id_employee");
                String password = result.getString("password");
                dictionary.put(employee_id, password);
            }
            for (String key : dictionary.keySet()) {
                String value = dictionary.get(key);
                System.out.println(key + ": " + value);
            }

            databaseManager.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return verification(dictionary);
    }
    public boolean verification(HashMap<String, String> dictionary){
        String log = Field_login.getText();
        String pas = Field_password.getText();

        boolean verification = false;

        for (String key : dictionary.keySet()){
            if (key.equals(log) && dictionary.get(key).equals(pas)) {
                verification = true;
            }
        }
        return verification;
    }
    public boolean managerCheck(String s){
        //HWA239KLO4
        //AKKNSA1N73
        return s.equals("AKKNSA1N73") || s.equals("HWA239KLO4");
    }
}