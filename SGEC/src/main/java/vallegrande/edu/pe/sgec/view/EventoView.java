package vallegrande.edu.pe.sgec.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.time.LocalDate;

public class EventoView extends BorderPane {

    // CAMPOS DEL FORMULARIO
    private TextField txtNombre;
    private TextArea txtDescripcion;
    private DatePicker dpFecha;
    private TextField txtLugar;
    private ComboBox<String> cbModalidad;
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
    private TableView<vallegrande.edu.pe.sgec.model.Evento> tablaEventos;

    public EventoView() {

        // TÍTULO
        Label titulo = new Label("GESTIÓN DE EVENTOS");
        titulo.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;"
        );

        HBox encabezado = new HBox(titulo);
        encabezado.setAlignment(Pos.CENTER);
        encabezado.setPadding(new Insets(20));
        encabezado.setStyle("-fx-background-color: #1e3a8a;");

        // FORMULARIO
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del evento");

        txtDescripcion = new TextArea();
        txtDescripcion.setPromptText("Descripción del evento");
        txtDescripcion.setPrefRowCount(2);
        txtDescripcion.setWrapText(true);

        dpFecha = new DatePicker();
        dpFecha.setPromptText("Seleccionar fecha");

        txtLugar = new TextField();
        txtLugar.setPromptText("Lugar del evento");

        cbModalidad = new ComboBox<>(
                FXCollections.observableArrayList(
                        "Presencial", "Virtual", "Híbrido"
                )
        );
        cbModalidad.setPromptText("Seleccionar modalidad");

        cbEstado = new ComboBox<>(
                FXCollections.observableArrayList(
                        "Programado", "En curso", "Finalizado", "Cancelado"
                )
        );
        cbEstado.setPromptText("Seleccionar estado");

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

        // ORGANIZACIÓN DEL FORMULARIO
        GridPane formulario = new GridPane();
        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(20));

        formulario.add(new Label("Nombre:"), 0, 0);
        formulario.add(txtNombre, 1, 0);

        formulario.add(new Label("Descripción:"), 0, 1);
        formulario.add(txtDescripcion, 1, 1);

        formulario.add(new Label("Fecha:"), 0, 2);
        formulario.add(dpFecha, 1, 2);

        formulario.add(new Label("Lugar:"), 0, 3);
        formulario.add(txtLugar, 1, 3);

        formulario.add(new Label("Modalidad:"), 0, 4);
        formulario.add(cbModalidad, 1, 4);

        formulario.add(new Label("Estado:"), 0, 5);
        formulario.add(cbEstado, 1, 5);

        HBox botones = new HBox(10,
                btnRegistrar, btnActualizar,
                btnEliminar, btnLimpiar, btnVolver
        );
        botones.setAlignment(Pos.CENTER);

        VBox panelFormulario = new VBox(15,
                new Label("Datos del evento"),
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
        txtBuscar.setPromptText("Buscar evento por nombre");

        btnBuscar = new Button("Buscar");
        configurarBoton(btnBuscar, "#0891b2");

        HBox panelBusqueda = new HBox(10, txtBuscar, btnBuscar);
        panelBusqueda.setAlignment(Pos.CENTER_LEFT);
        panelBusqueda.setPadding(new Insets(10, 0, 10, 0));

        // TABLA DE EVENTOS
        tablaEventos = new TableView<>();

        TableColumn<vallegrande.edu.pe.sgec.model.Evento, Integer> colId =
                new TableColumn<>("ID");

        colId.setCellValueFactory(
                new PropertyValueFactory<>("idEvento")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Evento, String> colNombre =
                new TableColumn<>("Nombre");

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Evento, String> colDescripcion =
                new TableColumn<>("Descripción");

        colDescripcion.setCellValueFactory(
                new PropertyValueFactory<>("descripcion")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Evento, LocalDate> colFecha =
                new TableColumn<>("Fecha");

        colFecha.setCellValueFactory(
                new PropertyValueFactory<>("fecha")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Evento, String> colLugar =
                new TableColumn<>("Lugar");

        colLugar.setCellValueFactory(
                new PropertyValueFactory<>("lugar")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Evento, String> colModalidad =
                new TableColumn<>("Modalidad");

        colModalidad.setCellValueFactory(
                new PropertyValueFactory<>("modalidad")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Evento, String> colEstado =
                new TableColumn<>("Estado");

        colEstado.setCellValueFactory(
                new PropertyValueFactory<>("estado")
        );

        tablaEventos.getColumns().addAll(
                colId, colNombre, colDescripcion,
                colFecha, colLugar, colModalidad, colEstado
        );

        tablaEventos.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        Label tituloTabla = new Label("Lista de eventos registrados");
        tituloTabla.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox contenido = new VBox(10,
                panelFormulario,
                panelBusqueda,
                tituloTabla,
                tablaEventos
        );

        contenido.setPadding(new Insets(20));
        setCenter(contenido);

        setTop(encabezado);
        setStyle("-fx-background-color: #f1f5f9;");
    }

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

    // GETTERS DEL FORMULARIO

    public TextField getTxtNombre() {
        return txtNombre;
    }

    public TextArea getTxtDescripcion() {
        return txtDescripcion;
    }

    public DatePicker getDpFecha() {
        return dpFecha;
    }

    public TextField getTxtLugar() {
        return txtLugar;
    }

    public ComboBox<String> getCbModalidad() {
        return cbModalidad;
    }

    public ComboBox<String> getCbEstado() {
        return cbEstado;
    }

    public TextField getTxtBuscar() {
        return txtBuscar;
    }

    // GETTERS DE BOTONES

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

    public TableView<vallegrande.edu.pe.sgec.model.Evento> getTablaEventos() {
        return tablaEventos;
    }
}