package vallegrande.edu.pe.sgec.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class ParticipanteView extends BorderPane {

    // CAMPOS DEL FORMULARIO
    private TextField txtNombres;
    private TextField txtApellidos;
    private TextField txtDni;
    private TextField txtCorreo;
    private TextField txtTelefono;
    private TextField txtInstitucion;
    private TextField txtBuscar;

    // BOTONES
    private Button btnRegistrar;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnLimpiar;
    private Button btnVolver;
    private Button btnBuscar;

    // TABLA
    private TableView<vallegrande.edu.pe.sgec.model.Participante> tablaParticipantes;

    public ParticipanteView() {

        // ENCABEZADO
        Label titulo = new Label("GESTIÓN DE PARTICIPANTES");

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
        txtNombres = new TextField();
        txtNombres.setPromptText("Ingrese los nombres");

        txtApellidos = new TextField();
        txtApellidos.setPromptText("Ingrese los apellidos");

        txtDni = new TextField();
        txtDni.setPromptText("Ingrese el DNI");

        txtCorreo = new TextField();
        txtCorreo.setPromptText("Ingrese el correo");

        txtTelefono = new TextField();
        txtTelefono.setPromptText("Ingrese el teléfono");

        txtInstitucion = new TextField();
        txtInstitucion.setPromptText("Ingrese la institución");

        // FORMULARIO
        GridPane formulario = new GridPane();
        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(20));

        formulario.add(new Label("Nombres:"), 0, 0);
        formulario.add(txtNombres, 1, 0);

        formulario.add(new Label("Apellidos:"), 0, 1);
        formulario.add(txtApellidos, 1, 1);

        formulario.add(new Label("DNI:"), 0, 2);
        formulario.add(txtDni, 1, 2);

        formulario.add(new Label("Correo:"), 0, 3);
        formulario.add(txtCorreo, 1, 3);

        formulario.add(new Label("Teléfono:"), 0, 4);
        formulario.add(txtTelefono, 1, 4);

        formulario.add(new Label("Institución:"), 0, 5);
        formulario.add(txtInstitucion, 1, 5);

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
                new Label("Datos del participante"),
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
        txtBuscar.setPromptText("Buscar por nombres, apellidos o DNI");

        btnBuscar = new Button("Buscar");
        configurarBoton(btnBuscar, "#0891b2");

        HBox panelBusqueda = new HBox(10, txtBuscar, btnBuscar);
        panelBusqueda.setAlignment(Pos.CENTER_LEFT);

        // TABLA
        tablaParticipantes = new TableView<>();

        TableColumn<vallegrande.edu.pe.sgec.model.Participante, Integer> colId =
                new TableColumn<>("ID");

        colId.setCellValueFactory(
                new PropertyValueFactory<>("idParticipante")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Participante, String> colNombres =
                new TableColumn<>("Nombres");

        colNombres.setCellValueFactory(
                new PropertyValueFactory<>("nombres")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Participante, String> colApellidos =
                new TableColumn<>("Apellidos");

        colApellidos.setCellValueFactory(
                new PropertyValueFactory<>("apellidos")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Participante, String> colDni =
                new TableColumn<>("DNI");

        colDni.setCellValueFactory(
                new PropertyValueFactory<>("dni")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Participante, String> colCorreo =
                new TableColumn<>("Correo");

        colCorreo.setCellValueFactory(
                new PropertyValueFactory<>("correo")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Participante, String> colTelefono =
                new TableColumn<>("Teléfono");

        colTelefono.setCellValueFactory(
                new PropertyValueFactory<>("telefono")
        );

        TableColumn<vallegrande.edu.pe.sgec.model.Participante, String> colInstitucion =
                new TableColumn<>("Institución");

        colInstitucion.setCellValueFactory(
                new PropertyValueFactory<>("institucion")
        );

        tablaParticipantes.getColumns().addAll(
                colId,
                colNombres,
                colApellidos,
                colDni,
                colCorreo,
                colTelefono,
                colInstitucion
        );

        tablaParticipantes.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        Label tituloTabla = new Label("Lista de participantes registrados");
        tituloTabla.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;"
        );

        VBox contenido = new VBox(
                10,
                panelFormulario,
                panelBusqueda,
                tituloTabla,
                tablaParticipantes
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

    public TextField getTxtNombres() {
        return txtNombres;
    }

    public TextField getTxtApellidos() {
        return txtApellidos;
    }

    public TextField getTxtDni() {
        return txtDni;
    }

    public TextField getTxtCorreo() {
        return txtCorreo;
    }

    public TextField getTxtTelefono() {
        return txtTelefono;
    }

    public TextField getTxtInstitucion() {
        return txtInstitucion;
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

    public TableView<vallegrande.edu.pe.sgec.model.Participante>
    getTablaParticipantes() {
        return tablaParticipantes;
    }
}