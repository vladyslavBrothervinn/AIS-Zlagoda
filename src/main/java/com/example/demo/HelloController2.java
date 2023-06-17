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
import models.Employee;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
//import javafx.embed.swing.SwingFXUtils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TableView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
    String[] selection;

    boolean x = false;


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

        try {
            initializeTheTable("Category");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        myChoiceBox.getItems().addAll(selection);
        myChoiceBox.setOnAction(this::getSelection);
    }

    @FXML
    private void showAddWindow() {
        Stage smallStage = new Stage();
        smallStage.initOwner(stage);
        smallStage.initModality(Modality.WINDOW_MODAL);
        smallStage.initStyle(StageStyle.UTILITY);

        switch (myChoiceBox.getValue()){
            case "Customer Card" :
                TextField textField3 = new TextField(); //1
                TextField textField4 = new TextField();
                TextField textField5 = new TextField();
                TextField textField6 = new TextField();
                TextField textField7 = new TextField();
                TextField textField8 = new TextField();
                TextField textField9 = new TextField();
                TextField textField10 = new TextField();
                TextField textField11 = new TextField();
                Button closeButton1 = new Button("Закрити");
                Button saveButton1 = new Button("Зберегти");
                closeButton1.setOnAction(e -> smallStage.close());

                GridPane gridPane2 = new GridPane();
                gridPane2.setPadding(new Insets(10));
                gridPane2.setHgap(10);
                gridPane2.setVgap(10);
                gridPane2.addRow(0, new Label("Card number:"), textField3);
                gridPane2.addRow(1, new Label("Customer surname:"), textField4);
                gridPane2.addRow(2, new Label("Customer name:"), textField5);
                gridPane2.addRow(3, new Label("Customer patronymic:"), textField6);
                gridPane2.addRow(4, new Label("phone number:"), textField7);
                gridPane2.addRow(5, new Label("city:"), textField8);
                gridPane2.addRow(6, new Label("street:"), textField9);
                gridPane2.addRow(7, new Label("zip code:"), textField10);
                gridPane2.addRow(8, new Label("percent:"), textField11);
                gridPane2.addRow(9, saveButton1);
                gridPane2.addRow(9, closeButton1);

                Scene scene2 = new Scene(gridPane2);
                smallStage.setScene(scene2);
                smallStage.setTitle("Додати");
                smallStage.showAndWait();
                break;

            case "Category" :
                TextField textField1 = new TextField();
                TextField textField2 = new TextField();
                Button closeButton = new Button("Закрити");
                Button saveButton = new Button("Зберегти");
                closeButton.setOnAction(e -> smallStage.close());

                GridPane gridPane1 = new GridPane();
                gridPane1.setPadding(new Insets(4));
                gridPane1.setHgap(10);
                gridPane1.setVgap(10);
                gridPane1.addRow(0, new Label("Category number:"), textField1);
                gridPane1.addRow(1, new Label("Category name:"), textField2);
                gridPane1.addRow(2, saveButton);
                gridPane1.addRow(2, closeButton);

                Scene scene1 = new Scene(gridPane1);
                smallStage.setScene(scene1);
                smallStage.setTitle("Додати");
                smallStage.showAndWait();

                break;

        }
            /*TextField textField = new TextField();
        Button closeButton = new Button("Закрити");
        Button saveButton = new Button("Зберегти");
        closeButton.setOnAction(e -> smallStage.close());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, new Label("Enter Info:"), textField);
        gridPane.addRow(1, saveButton);
        gridPane.addRow(1, closeButton);

        Scene scene = new Scene(gridPane);
        smallStage.setScene(scene);
        smallStage.setTitle("Window2");
        smallStage.showAndWait();*/
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
