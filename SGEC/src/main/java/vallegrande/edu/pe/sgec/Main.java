package vallegrande.edu.pe.sgec;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vallegrande.edu.pe.sgec.controller.PrincipalController;
import vallegrande.edu.pe.sgec.view.PrincipalView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        PrincipalView principalView = new PrincipalView();
        new PrincipalController(principalView, stage);

        Scene scene = new Scene(principalView, 1100, 700);
        stage.setTitle("SGEC - Sistema de Gestión de Eventos y Conferencias");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}