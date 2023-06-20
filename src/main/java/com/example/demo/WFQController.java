package com.example.demo;

import instruments.SQLBasedTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class WFQController implements Initializable {
    @FXML
    public TableView<String> checkTable;
    SQLBasedTable sqlBasedTable;
    public ChoiceBox<String> queryBox;
    public Text text;
    public Text description;
    public TextField textField;
    public DatePicker first_date;
    public DatePicker second_date;
    private Stage stage;
    private Scene scene;
    private Parent root;
    // String sqlQ1 = "PARAMETERS [DateParam] DateTime; SELECT e.empl_surname, e.empl_name, SUM(s.selling_price) AS total_sales FROM (Employee AS e INNER JOIN Check AS c ON e.id_employee = c.id_employee) INNER JOIN Sale AS s ON c.check_number = s.check_number WHERE c.print_date = [DateParam]GROUP BY e.empl_surname, e.empl_nameORDER BY SUM(s.selling_price) DESC;";

    String sqlQ1 = "SELECT e.empl_surname, e.empl_name, SUM(s.selling_price) AS total_sales FROM (Employee AS e INNER JOIN Check AS c ON e.id_employee = c.id_employee) INNER JOIN Sale AS s ON c.check_number = s.check_number WHERE c.print_date = ? GROUP BY e.empl_surname, e.empl_name ORDER BY SUM(s.selling_price) DESC;";
    String sqlQ2=""; // потім допишу
    //твої запити
    String sqlQ3="SELECT Customer_Card.card_number, cust_surname, cust_name, SUM(Nz(sum_total, 0)) AS total_sum\n " +
            "FROM Customer_Card LEFT JOIN Check ON Customer_Card.card_number = Check.card_number\n " +
            "WHERE Check.check_number IN (SELECT check_number \n" +
            "FROM Sale\n" +
            "WHERE selling_price > 150)\n " +
            "GROUP BY Customer_Card.card_number, cust_surname, cust_name\n";
    String sqlQ4="SELECT card_number, cust_surname, cust_name\n " +
            "FROM Customer_Card\n " +
            "WHERE card_number NOT IN (SELECT card_number\n " +
            "FROM Check\n " +
            "WHERE Customer_Card.card_number = Check.card_number\n " +
            "AND id_employee NOT IN (SELECT id_employee\n " +
            "FROM Employee\n " +
            "WHERE Employee.id_employee = Check.id_employee\n " +
            "AND city = ?))\n";
    String[] sqls;
    // Впиши дескріпшони
    String description1 = "description1";
    String description2 = "description2";
    String description3 = "Порахувати, скільки кожен клієнт з тих, що хоч раз купував товар, \n" +
            "дорожчий за 150 грн., витратив грошей загалом(у всіх чеках)";
    String description4 = "Знайти усіх клієнтів, які не купували товари у продавців не з деякого вказаного міста";
    String[] descriptions;

    String[] q_arr = new String[]{"q1", "q2", "q3", "q4"};

    @FXML
    public void comeback(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        queryBox.getItems().addAll(q_arr);
        queryBox.setOnAction(this::getQuery);
        sqls = new String[]{sqlQ1, sqlQ2, sqlQ3, sqlQ4};
        descriptions = new String[]{description1, description2, description3, description4};
        sqlBasedTable = new SQLBasedTable(checkTable);
    }

    private void getQuery(ActionEvent event) {
        textField.setText("");
        first_date.setValue(null);
        second_date.setValue(null);
        String mySelection = queryBox.getValue();
        text.setText(sqls[queryBox.getSelectionModel().getSelectedIndex()]);
        description.setText(descriptions[queryBox.getSelectionModel().getSelectedIndex()]);
        // не встигаю дописати, крч далі буде логіка в залежності від queryBox.getValue()
        // в нас як в HelloController2 ініціалізується табличка з лейблами того запиту, який ми вибираємо
    }

    public void executeQuery(){
        ArrayList<String> arrayList = new ArrayList<>();
        if(!Objects.equals(textField.getText(), "") &&textField.getText()!=null){
            arrayList.add(textField.getText());
        }
        if(first_date.getValue()!=null){
            arrayList.add(String.valueOf(first_date.getValue()));
        }
        if(second_date.getValue()!=null){
            arrayList.add(String.valueOf(second_date.getValue()));
        }
        String[] params = new String[arrayList.size()];
        for(int i = 0; i < params.length; i++){
            params[i] = arrayList.get(i);
        }
        System.out.println(params.length);
        try {
            sqlBasedTable.runSQL(text.getText(), params);
        } catch (SQLException e) {
            showInfoWindow("Помилка!", "Схоже, запит некоректний або параметри введено неправильно");
            e.printStackTrace();
        }
    }

    private void showInfoWindow(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.showAndWait();
    }
}
