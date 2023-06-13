package com.example.demo;

import db.DatabaseManager;
import instruments.TableManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    Label myLabel;
    @FXML
    ChoiceBox<String> myChoiceBox;
    String[] selection = {"Employee", "Category", "Check", "Customer_Card", "Product", "Sale","Store_Product"};

    boolean x = false;


    public void initializeTheTable(String selection) throws SQLException, ClassNotFoundException {
        tableManager = new TableManager<>(DatabaseManager.getDatabaseManager(), tableView1, selection);
    }

    public void refreshTable() throws SQLException {
        tableManager.updateRow(new String[]{"4"}, new Category(4, "Овоч"+(x?"і":".")));
        x = !x;
    }

    public void switchToScene1(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void refresh(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            initializeTheTable("Category");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        myChoiceBox.getItems().addAll(selection);
        myChoiceBox.setOnAction(this::getSelection);
    }

    public void getSelection(ActionEvent event){
        String mySelection = myChoiceBox.getValue();
        //myLabel.setText(mySelection);
        tableView1.getColumns().clear();
        tableView1.getItems().clear();
        //стереть таблицу
        //tableview has snapshot method
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
