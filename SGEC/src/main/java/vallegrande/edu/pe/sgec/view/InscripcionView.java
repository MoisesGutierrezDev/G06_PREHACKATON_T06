package vallegrande.edu.pe.sgec.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.time.LocalDate;

public class InscripcionView extends BorderPane {

    // CAMPOS DEL FORMULARIO
    private ComboBox<Integer> cbEvento;
    private ComboBox<Integer> cbParticipante;
    private DatePicker dpFechaInscripcion;
    private ComboBox<String> cbEstado;
    private TextField txtBuscar;

    // BOTONES
    private Button btnRegistrar;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnLimpiar;
    private Button btnVolver;
    private Button btnBuscar;

    // TABLA
    private TableView<vallegrande.edu.pe.sgec.model.Inscripcion>
            tablaInscripciones;

    public InscripcionView() {

        // ENCABEZADO
        Label titulo = new Label("GESTIÓN DE INSCRIPCIONES");

        titulo.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;"
        );

        HBox encabezado = new HBox(titulo);
        encabezado.setAlignment(Pos.CENTER);
        encabezado.setPadding(new Insets(20));
        encabezado.setStyle("-fx-background-color: #1e3a8a;");

        // CAMPOS
        cbEvento = new ComboBox<>();
        cbEvento.setPromptText("Seleccionar evento");
        cbEvento.setPrefWidth(250);

        cbParticipante = new ComboBox<>();
        cbParticipante.setPromptText("Seleccionar participante");
        cbParticipante.setPrefWidth(250);

        dpFechaInscripcion = new DatePicker();
        dpFechaInscripcion.setValue(LocalDate.now());
        dpFechaInscripcion.setPrefWidth(250);

        cbEstado = new ComboBox<>(
                FXCollections.observableArrayList(
                        "Activa",
                        "Cancelada",
                        "Confirmada"
                )
        );

        cbEstado.setPromptText("Seleccionar estado");
        cbEstado.setPrefWidth(250);

        // FORMULARIO
        GridPane formulario = new GridPane();

        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(20));

        formulario.add(new Label("Evento:"), 0, 0);
        formulario.add(cbEvento, 1, 0);

        formulario.add(new Label("Participante:"), 0, 1);
        formulario.add(cbParticipante, 1, 1);

        formulario.add(new Label("Fecha de inscripción:"), 0, 2);
        formulario.add(dpFechaInscripcion, 1, 2);

        formulario.add(new Label("Estado:"), 0, 3);
        formulario.add(cbEstado, 1, 3);

        // BOTONES
        btnRegistrar = new Button("Registrar");
        btnActualizar = new Button("Actualizar");
        btnEliminar = new Button("Eliminar");
        btnLimpiar = new Button("Limpiar");
        btnVolver = new Button("Volver");

        configurarBoton(btnRegistrar, "#16a34a");
        configurarBoton(btnActualizar, "#2563eb");
        configurarBoton(btnEliminar, "#dc2626");
        configurarBoton(btnLimpiar, "#64748b");
        configurarBoton(btnVolver, "#475569");

        HBox botones = new HBox(
                10,
                btnRegistrar,
                btnActualizar,
                btnEliminar,
                btnLimpiar,
                btnVolver
        );

        botones.setAlignment(Pos.CENTER);

        VBox panelFormulario = new VBox(
                15,
                new Label("Datos de la inscripción"),
                formulario,
                botones
        );

        panelFormulario.setPadding(new Insets(15));

        panelFormulario.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10px;"
        );

        // BUSCADOR
        txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar inscripción por ID");

        btnBuscar = new Button("Buscar");
        configurarBoton(btnBuscar, "#0891b2");

        HBox panelBusqueda = new HBox(
                10,
                txtBuscar,
                btnBuscar
        );

        panelBusqueda.setAlignment(Pos.CENTER_LEFT);

        // TABLA
        tablaInscripciones = new TableView<>();

        TableColumn<vallegrande.edu.pe.sgec.model.Inscripcion, Integer>
                colId = new TableColumn<>("ID");

        colId.setCellValueFactory(
                new PropertyValueFactory<>("idInscripcion")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Inscripcion, Integer>
                colEvento = new TableColumn<>("ID Evento");

        colEvento.setCellValueFactory(
                new PropertyValueFactory<>("idEvento")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Inscripcion, Integer>
                colParticipante = new TableColumn<>("ID Participante");

        colParticipante.setCellValueFactory(
                new PropertyValueFactory<>("idParticipante")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Inscripcion, LocalDate>
                colFecha = new TableColumn<>("Fecha de inscripción");

        colFecha.setCellValueFactory(
                new PropertyValueFactory<>("fechaInscripcion")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Inscripcion, String>
                colEstado = new TableColumn<>("Estado");

        colEstado.setCellValueFactory(
                new PropertyValueFactory<>("estado")
        );

        tablaInscripciones.getColumns().addAll(
                colId,
                colEvento,
                colParticipante,
                colFecha,
                colEstado
        );

        tablaInscripciones.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        Label tituloTabla = new Label(
                "Lista de inscripciones registradas"
        );

        tituloTabla.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;"
        );

        VBox contenido = new VBox(
                10,
                panelFormulario,
                panelBusqueda,
                tituloTabla,
                tablaInscripciones
        );

        contenido.setPadding(new Insets(20));

        setTop(encabezado);
        setCenter(contenido);

        setStyle("-fx-background-color: #f1f5f9;");
    }

    // CONFIGURAR BOTONES
    private void configurarBoton(Button boton, String color) {

        boton.setPrefHeight(35);

        boton.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 6px;" +
                        "-fx-cursor: hand;"
        );
    }

    // GETTERS DE LOS CAMPOS

    public ComboBox<Integer> getCbEvento() {
        return cbEvento;
    }

    public ComboBox<Integer> getCbParticipante() {
        return cbParticipante;
    }

    public DatePicker getDpFechaInscripcion() {
        return dpFechaInscripcion;
    }

    public ComboBox<String> getCbEstado() {
        return cbEstado;
    }

    public TextField getTxtBuscar() {
        return txtBuscar;
    }

    // GETTERS DE LOS BOTONES

    public Button getBtnRegistrar() {
        return btnRegistrar;
    }

    public Button getBtnActualizar() {
        return btnActualizar;
    }

    public Button getBtnEliminar() {
        return btnEliminar;
    }

    public Button getBtnLimpiar() {
        return btnLimpiar;
    }

    public Button getBtnVolver() {
        return btnVolver;
    }

    public Button getBtnBuscar() {
        return btnBuscar;
    }

    public TableView<vallegrande.edu.pe.sgec.model.Inscripcion>
    getTablaInscripciones() {
        return tablaInscripciones;
    }
}