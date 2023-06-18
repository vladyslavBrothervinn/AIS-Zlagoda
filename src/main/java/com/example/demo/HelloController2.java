package com.example.demo;

import db.DatabaseManager;
import instruments.TableManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Category;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
//import javafx.embed.swing.SwingFXUtils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TableView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.controlsfx.control.textfield.TextFields;

import static com.example.demo.HelloController1.isCashier;

public class HelloController2 implements Initializable {

    @FXML
    public AnchorPane paneToSave;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    TableView<Category> tableView1;
    TableManager<Category> tableManager;
    @FXML
    Button add;
    @FXML
    Button edit;
    @FXML
    Button del;
    @FXML
    ChoiceBox<String> myChoiceBox;
    @FXML
    ChoiceBox<String> AttrChoiceBox;
    String[] attr_arr;
    String[] selection;
    @FXML
    TextField textField;


    public void initializeTheTable(String selection) throws SQLException, ClassNotFoundException {
        tableManager = new TableManager<>(DatabaseManager.getDatabaseManager(), tableView1, selection);
    }

    public void switchToScene1(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToCheckScene(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("checkScene.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void delete(){
        //method to delete a chosen row

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("scene2: "+HelloController1.isCashier);
        if (!isCashier){
            System.out.println("it;s false");
            add.setDisable(!isCashier);
            edit.setDisable(!isCashier);
            del.setDisable(!isCashier);
            selection = new String[]{"Category", "Check", "Customer_Card", "Product", "Sale", "Store_Product"};
        }
        else selection = new String[]{"Employee", "Category", "Check", "Customer_Card", "Product", "Sale", "Store_Product"};

        add.setOnAction(e -> AddOrUpdate(add.getText()));
        edit.setOnAction(e -> AddOrUpdate(edit.getText()));

        try {
            initializeTheTable("Category");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        myChoiceBox.getItems().addAll(selection);
        myChoiceBox.setOnAction(this::getSelection);

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Input method text changed: " + newValue);
            // Do something with the new input method text
            //AttrChoiceBox
            try {
                tableManager.removeAllFilters();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                tableManager.addSubstringFilter(AttrChoiceBox.getValue(), newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //textField.requestFocus();
        });
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
    @FXML
    private void AddOrUpdate(String s) {
        Stage smallStage = new Stage();
        smallStage.initOwner(stage);
        smallStage.initModality(Modality.APPLICATION_MODAL);
        smallStage.initStyle(StageStyle.UTILITY);

        try {
            if (myChoiceBox.getValue() == null) showInfoWindow("Помилка", "Виберіть таблицю для взаємодії!");

        switch (myChoiceBox.getValue()) {
            case "Customer_Card" -> {
                TextField textField1_0 = new TextField();
                TextField textField1_1 = new TextField();
                TextField textField1_2 = new TextField();
                TextField textField1_3 = new TextField();
                TextField textField1_4 = new TextField();
                TextField textField1_5 = new TextField();
                TextField textField1_6 = new TextField();
                TextField textField1_7 = new TextField();
                TextField textField1_8 = new TextField();

                Button closeButton1 = new Button("Закрити");
                Button saveButton1 = new Button("Зберегти");

                closeButton1.setOnAction(e -> smallStage.close());
                // save button has two options if s = "add" or if s = "update"
                saveButton1.setOnAction(event -> {
                    if (s.equals("Додати")){
                        // add to the database info
                        System.out.println(textField1_1.getText());
                    }
                    if (s.equals("Редагувати")){
                        // edit the database with the info from the text fields
                        System.out.println(textField1_2.getText());
                    }
                });
                //

                GridPane gridPane1 = new GridPane();
                gridPane1.setPadding(new Insets(10));
                gridPane1.setHgap(25);
                gridPane1.setVgap(25);
                //gridPane1.addRow(0, new Label("Customer id:"), textField1_0);
                gridPane1.addRow(1, new Label("Customer surname:"), textField1_1);
                gridPane1.addRow(2, new Label("Customer name:"), textField1_2);
                gridPane1.addRow(3, new Label("Customer patronymic:"), textField1_3);
                gridPane1.addRow(4, new Label("phone number:"), textField1_4);
                gridPane1.addRow(5, new Label("city:"), textField1_5);
                gridPane1.addRow(6, new Label("street:"), textField1_6);
                gridPane1.addRow(7, new Label("zip code:"), textField1_7);
                gridPane1.addRow(8, new Label("percent:"), textField1_8);
                gridPane1.addRow(9, saveButton1);
                gridPane1.addRow(9, closeButton1);

                Scene scene1 = new Scene(gridPane1);
                smallStage.setScene(scene1);
                smallStage.setTitle(s);
                smallStage.showAndWait();
            }
            case "Category" -> {
                TextField textField2_0 = new TextField();
                TextField textField2_1 = new TextField();

                Button closeButton2 = new Button("Закрити");
                Button saveButton2 = new Button("Зберегти");

                closeButton2.setOnAction(e -> smallStage.close());

                GridPane gridPane2 = new GridPane();
                gridPane2.setPadding(new Insets(4));
                gridPane2.setHgap(25);
                gridPane2.setVgap(25);
                //gridPane2.addRow(0, new Label("Category number:"), textField2_0);
                gridPane2.addRow(1, new Label("Category name:"), textField2_1);
                gridPane2.addRow(2, saveButton2);
                gridPane2.addRow(2, closeButton2);

                Scene scene2 = new Scene(gridPane2);
                smallStage.setScene(scene2);
                smallStage.setTitle(s);
                smallStage.showAndWait();
            }
            case "Employee" ->{
                TextField textField3_0 = new TextField();
                TextField textField3_1 = new TextField();
                TextField textField3_2 = new TextField();
                TextField textField3_3 = new TextField();
                TextField textField3_4 = new TextField();
                TextField textField3_5 = new TextField();
                TextField textField3_6 = new TextField();
                TextField textField3_7 = new TextField();
                TextField textField3_8 = new TextField();
                TextField textField3_9 = new TextField();
                TextField textField3_10 = new TextField();
                TextField textField3_11 = new TextField();


                /*String[] arr = new String[]{"aba", "3331", "124"};
                TextFields.bindAutoCompletion(textField3_0, arr);*/



                Button closeButton3 = new Button("Закрити");
                Button saveButton3 = new Button("Зберегти");

                closeButton3.setOnAction(e -> smallStage.close());

                GridPane gridPane3 = new GridPane();
                gridPane3.setPadding(new Insets(13));
                gridPane3.setHgap(25);
                gridPane3.setVgap(25);
                gridPane3.addRow(0, new Label("Employee password:"), textField3_0);
                gridPane3.addRow(1, new Label("Employee surname:"), textField3_1);
                gridPane3.addRow(2, new Label("Employee name:"), textField3_2);
                gridPane3.addRow(3, new Label("Employee patronymic:"), textField3_3);
                gridPane3.addRow(4, new Label("role:"), textField3_4);
                gridPane3.addRow(5, new Label("salary:"), textField3_5);
                gridPane3.addRow(6, new Label("date of birth:"), textField3_6);
                gridPane3.addRow(7, new Label("date of start:"), textField3_7);
                gridPane3.addRow(8, new Label("phone number:"), textField3_8);
                gridPane3.addRow(9, new Label("city:"), textField3_9);
                gridPane3.addRow(10, new Label("street:"), textField3_10);
                gridPane3.addRow(11, new Label("zip code:"), textField3_11);
                gridPane3.addRow(12, saveButton3);
                gridPane3.addRow(12, closeButton3);

                Scene scene3 = new Scene(gridPane3);
                smallStage.setScene(scene3);
                smallStage.setTitle(s);
                smallStage.showAndWait();
            }
            case "Product" ->{
                TextField textField4_0 = new TextField();
                TextField textField4_1 = new TextField();
                TextField textField4_2 = new TextField();
                TextField textField4_3 = new TextField();

                Button closeButton4 = new Button("Закрити");
                Button saveButton4 = new Button("Зберегти");

                closeButton4.setOnAction(e -> smallStage.close());

                GridPane gridPane4 = new GridPane();
                gridPane4.setPadding(new Insets(4));
                gridPane4.setHgap(25);
                gridPane4.setVgap(25);

                //gridPane4.addRow(0, new Label("id_product:"), textField4_0);
                gridPane4.addRow(1, new Label("Category number:"), textField4_1);
                gridPane4.addRow(2, new Label("Product name:"), textField4_2);
                gridPane4.addRow(3, new Label("Characteristics:"), textField4_3);
                gridPane4.addRow(4, saveButton4);
                gridPane4.addRow(4, closeButton4);

                Scene scene4 = new Scene(gridPane4);
                smallStage.setScene(scene4);
                smallStage.setTitle(s);
                smallStage.showAndWait();
            }
            case "Sale" ->{
                TextField textField5_0 = new TextField();
                TextField textField5_1 = new TextField();
                TextField textField5_2 = new TextField();
                TextField textField5_3 = new TextField();

                Button closeButton5 = new Button("Закрити");
                Button saveButton5 = new Button("Зберегти");

                closeButton5.setOnAction(e -> smallStage.close());

                GridPane gridPane5 = new GridPane();
                gridPane5.setPadding(new Insets(4));
                gridPane5.setHgap(25);
                gridPane5.setVgap(25);
                //gridPane5.addRow(0, new Label("UPC:"), textField5_0);
                gridPane5.addRow(1, new Label("Check Number:"), textField5_1);
                gridPane5.addRow(2, new Label("Product number:"), textField5_2);
                gridPane5.addRow(3, new Label("Selling price:"), textField5_3);
                gridPane5.addRow(4, saveButton5);
                gridPane5.addRow(4, closeButton5);

                Scene scene5 = new Scene(gridPane5);
                smallStage.setScene(scene5);
                smallStage.setTitle(s);
                smallStage.showAndWait();
            }
            case "Store_Product" ->{
                TextField textField6_0 = new TextField();
                TextField textField6_1 = new TextField();
                TextField textField6_2 = new TextField();
                TextField textField6_3 = new TextField();
                TextField textField6_4 = new TextField();
                TextField textField6_5 = new TextField();

                Button closeButton6 = new Button("Закрити");
                Button saveButton6 = new Button("Зберегти");

                closeButton6.setOnAction(e -> smallStage.close());

                GridPane gridPane6 = new GridPane();
                gridPane6.setPadding(new Insets(6));
                gridPane6.setHgap(25);
                gridPane6.setVgap(25);
                //gridPane6.addRow(0, new Label("UPC:"), textField6_0);
                gridPane6.addRow(1, new Label("UPC prom:"), textField6_1);
                gridPane6.addRow(2, new Label("Id product:"), textField6_2);
                gridPane6.addRow(3, new Label("Selling price:"), textField6_3);
                gridPane6.addRow(4, new Label("Products number:"), textField6_4);
                gridPane6.addRow(5, new Label("promotional product:"), textField6_5);
                gridPane6.addRow(6, saveButton6);
                gridPane6.addRow(6, closeButton6);

                Scene scene6 = new Scene(gridPane6);
                smallStage.setScene(scene6);
                smallStage.setTitle(s);
                smallStage.showAndWait();
            }
            case "" ->{}
        }
        } catch (NullPointerException e){
            System.out.println("choice box is null");
        }

    }

    public void getSelection(ActionEvent event){
        String mySelection = myChoiceBox.getValue();
        tableView1.getColumns().clear();
        tableView1.getItems().clear();
        try {
            initializeTheTable(mySelection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        switch(mySelection){
            case "Employee" ->{
                attr_arr = new String[]{"id_employee","empl_surname","empl_name","empl_patronymic","empl_role","salary","date_of_birth","date_of_start","phone_number","city","street","zip_code"};
                AttrChoiceBox.getItems().clear();
                AttrChoiceBox.getItems().addAll(attr_arr);
            }
            case "Category" ->{
                attr_arr = new String[]{"category_number","category_name"};
                AttrChoiceBox.getItems().clear();
                AttrChoiceBox.getItems().addAll(attr_arr);
            }
            case "Check" ->{
                attr_arr = new String[]{"check_number","id_employee","card_number","print_date","sum_total","vat"};
                AttrChoiceBox.getItems().clear();
                AttrChoiceBox.getItems().addAll(attr_arr);
            }
            case "Customer_Card" ->{
                attr_arr = new String[]{"card_number","cust_surname","cust_name","cust_patronymic","phone_number","city","street","zip_code","percent"};
                AttrChoiceBox.getItems().clear();
                AttrChoiceBox.getItems().addAll(attr_arr);
            }
            case "Product" ->{
                attr_arr = new String[]{"id_product","category_number","product_name","characteristics"};
                AttrChoiceBox.getItems().clear();
                AttrChoiceBox.getItems().addAll(attr_arr);
            }
            case "Sale" ->{
                attr_arr = new String[]{"UPC","check_number","product_number","selling_price"};
                AttrChoiceBox.getItems().clear();
                AttrChoiceBox.getItems().addAll(attr_arr);
            }
            case "Store_Product" ->{
                attr_arr = new String[]{"product_name","UPC","UPC_prom","id_product","selling_price","product_number","promotional_product"};
                AttrChoiceBox.getItems().clear();
                AttrChoiceBox.getItems().addAll(attr_arr);
            }
        }
    }
    public void snapshot() {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT); // Set the background fill to transparent

        // Create a WritableImage object with appropriate dimensions
        WritableImage image = new WritableImage((int) paneToSave.getWidth(), (int) paneToSave.getHeight());

        // Capture the snapshot
        paneToSave.snapshot(params, image);

        // Save the image as a BufferedImage
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

        // Create a PDF document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(new PDRectangle(bufferedImage.getWidth(), bufferedImage.getHeight()));
        document.addPage(page);

        try {
            // Create a PDImageXObject from the BufferedImage
            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);

            // Create a content stream and draw the image on the page
            PDRectangle mediaBox = page.getMediaBox();
            float startX = mediaBox.getLowerLeftX();
            float startY = mediaBox.getLowerLeftY();
            float width = mediaBox.getWidth();
            float height = mediaBox.getHeight();
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.drawImage(pdImage, startX, startY, width, height);
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Snapshot");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF format", "*.pdf"));

            // Show the save file dialog
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                try {
                    ImageIO.write(Objects.requireNonNull(SwingFXUtils.fromFXImage(image, null)), "pdf", file);
                    document.save(file);
                    System.out.println("Snapshot saved to: " + file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Snapshot canceled by the user.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
