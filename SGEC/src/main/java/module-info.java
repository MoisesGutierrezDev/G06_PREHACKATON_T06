module com.example.sgec {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sgec to javafx.fxml;
    exports com.example.sgec;
}