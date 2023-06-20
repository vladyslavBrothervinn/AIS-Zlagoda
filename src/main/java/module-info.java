module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;
    requires org.apache.pdfbox;
    requires org.controlsfx.controls;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports models;
    opens models to javafx.fxml;
}