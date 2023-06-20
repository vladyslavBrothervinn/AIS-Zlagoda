package com.example.demo;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.example.demo.HelloController2.imageView;


public class PreviewController implements Initializable {
    @FXML
    public ImageView imageView;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button saveButton, closeWindow;
    @FXML
    AnchorPane savePane;

    @FXML
    Label tableName, todayDate;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm");
    DateTimeFormatter dtf_forSave = DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm");
    LocalDateTime currentDate = LocalDateTime.now();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //tableName, todayDate

        tableName.setText(HelloController2.choiceBoxValue);


        //System.out.println(dtf.format(currentDate));
        todayDate.setText(dtf.format(currentDate));

        Image image = new Image("C:\\Users\\Cyberpower\\IdeaProjects\\demo-login-window-forDbTesting\\src\\main\\resources\\com\\example\\demo\\image.png");
        imageView.setImage(image);

        /*//savePane add image
        imageView.setLayoutX(2);
        imageView.setLayoutY(155);*/
        // Add the ImageView to the AnchorPane
        //savePane.getChildren().add(imageView);

        /*saveButton.setOnAction(e -> {
            System.out.println("saved!");
        });*/

    }
    public void switchToMainMenu(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void saveZvit(ActionEvent event) throws IOException {
        String outputPath = "C:\\Users\\Cyberpower\\Downloads\\zvits\\report-"+dtf_forSave.format(currentDate)+".pdf";
        File file = new File(outputPath);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT); // Set the background fill to transparent

        // Create a WritableImage object with appropriate dimensions
        WritableImage image = new WritableImage((int) savePane.getWidth(), (int) savePane.getHeight());

        // Capture the snapshot
        savePane.snapshot(params, image);

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

            try {
                ImageIO.write(Objects.requireNonNull(SwingFXUtils.fromFXImage(image, null)), "pdf", file);
                document.save(file);
                System.out.println("Snapshot saved to: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
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
        switchToMainMenu(event);
    }


        @FXML
    private void cancelZvit(ActionEvent event) {
        Stage smallStage = new Stage();
        smallStage.initOwner(stage);
        smallStage.initModality(Modality.APPLICATION_MODAL);
        smallStage.initStyle(StageStyle.UTILITY);

        Label messageLabel = new Label("Натисніть 'Так' для відміни генерації звіту:");
        Button confirmButton = new Button("Так");
        confirmButton.setOnAction(e -> {
            smallStage.close();
            try {
                switchToMainMenu(event);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button cancelButton = new Button("Ні");
        cancelButton.setOnAction(e -> smallStage.close());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, messageLabel);
        gridPane.addRow(1, confirmButton, cancelButton);

        Scene scene = new Scene(gridPane);
        smallStage.setScene(scene);
        smallStage.setTitle("Підтвердження");
        smallStage.showAndWait();
    }
}
