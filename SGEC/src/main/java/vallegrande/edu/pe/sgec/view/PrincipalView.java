package vallegrande.edu.pe.sgec.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class PrincipalView extends BorderPane {

    private final Button btnInicio = crearBoton("🏠  Inicio");
    private final Button btnEventos = crearBoton("📅  Eventos");
    private final Button btnParticipantes = crearBoton("👥  Participantes");
    private final Button btnInscripciones = crearBoton("📋  Inscripciones");
    private final BorderPane areaContenido = new BorderPane();
    private Node pantallaInicio;

    public PrincipalView() {
        Label titulo = new Label("SGEC");
        titulo.setStyle("-fx-text-fill: white; -fx-font-size: 25px; -fx-font-weight: bold;");

        Label subtitulo = new Label("MENÚ PRINCIPAL");
        subtitulo.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 11px;");

        Region espacio = new Region();
        VBox.setVgrow(espacio, Priority.ALWAYS);
        VBox barraLateral = new VBox(12, titulo, subtitulo, btnEventos,
                btnParticipantes, btnInscripciones, espacio, btnInicio);
        barraLateral.setPadding(new Insets(25, 15, 20, 15));
        barraLateral.setAlignment(Pos.TOP_CENTER);
        barraLateral.setPrefWidth(230);
        barraLateral.setStyle("-fx-background-color: #1e293b;");

        Label bienvenida = new Label("¡Bienvenido al sistema SGEC!");
        bienvenida.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #1e3a8a;");
        Label descripcion = new Label("Selecciona un módulo del menú para comenzar a trabajar.");
        descripcion.setStyle("-fx-font-size: 15px; -fx-text-fill: #475569;");
        VBox inicio = new VBox(15, bienvenida, descripcion);
        inicio.setAlignment(Pos.CENTER);
        inicio.setPadding(new Insets(30));

        pantallaInicio = inicio;
        areaContenido.setCenter(inicio);
        areaContenido.setStyle("-fx-background-color: #f1f5f9;");

        setLeft(barraLateral);
        setCenter(areaContenido);
    }

    private static Button crearBoton(String texto) {
        Button boton = new Button(texto);
        boton.setMaxWidth(Double.MAX_VALUE);
        boton.setAlignment(Pos.CENTER_LEFT);
        boton.setStyle("-fx-background-color: transparent; -fx-text-fill: white;"
                + "-fx-font-size: 14px; -fx-padding: 12px; -fx-cursor: hand;");
        boton.setOnMouseEntered(e -> boton.setStyle("-fx-background-color: #334155;"
                + "-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 12px;"
                + "-fx-background-radius: 6px; -fx-cursor: hand;"));
        boton.setOnMouseExited(e -> boton.setStyle("-fx-background-color: transparent;"
                + "-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 12px; -fx-cursor: hand;"));
        return boton;
    }

    public void mostrarContenido(Node contenido) {
        areaContenido.setCenter(contenido);
    }

    public void mostrarBienvenida() {
        areaContenido.setCenter(pantallaInicio);
    }

    public Button getBtnInicio() { return btnInicio; }
    public Button getBtnEventos() { return btnEventos; }
    public Button getBtnParticipantes() { return btnParticipantes; }
    public Button getBtnInscripciones() { return btnInscripciones; }
}