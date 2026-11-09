module vallegrande.edu.pe.sgec {
    requires javafx.controls;
    requires javafx.fxml;


    opens vallegrande.edu.pe.sgec to javafx.fxml;
    exports vallegrande.edu.pe.sgec;
}