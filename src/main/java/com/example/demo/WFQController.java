package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class WFQController implements Initializable {
    public TableView<String> checkTable;
    public ChoiceBox<String> queryBox;
    public Text text;
    public Text description;
    public TextField textField;
    public DatePicker first_date;
    public DatePicker second_date;
    private Stage stage;
    private Scene scene;
    private Parent root;

    String sqlQ1 = "PARAMETERS [DateParam] DateTime; SELECT e.empl_surname, e.empl_name, SUM(s.selling_price) AS total_sales FROM (Employee AS e INNER JOIN Check AS c ON e.id_employee = c.id_employee) INNER JOIN Sale AS s ON c.check_number = s.check_number WHERE c.print_date = [DateParam]GROUP BY e.empl_surname, e.empl_nameORDER BY SUM(s.selling_price) DESC;";
    String sqlQ2=""; // потім допишу
    //твої запити
    String sqlQ3="";
    String sqlQ4="";

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
    }

    private void getQuery(ActionEvent event) {
        String mySelection = queryBox.getValue();

        // не встигаю дописати, крч далі буде логіка в залежності від queryBox.getValue()
        // в нас як в HelloController2 ініціалізується табличка з лейблами того запиту, який ми вибираємо
    }
}
