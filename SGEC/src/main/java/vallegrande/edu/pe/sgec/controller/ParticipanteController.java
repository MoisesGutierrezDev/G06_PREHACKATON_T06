package vallegrande.edu.pe.sgec.controller;

import vallegrande.edu.pe.sgec.model.Participante;
import vallegrande.edu.pe.sgec.model.ParticipanteDAO;
import vallegrande.edu.pe.sgec.view.ParticipanteView;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParticipanteController {

    private final ParticipanteView vista;
    private final ParticipanteDAO dao;

    public ParticipanteController(ParticipanteView vista) {
        this.vista = vista;
        this.dao = new ParticipanteDAO();

        inicializarEventos();
        cargarParticipantes();
    }

    // CONFIGURAR BOTONES
    private void inicializarEventos() {

        vista.getBtnRegistrar().setOnAction(e -> registrar());
        vista.getBtnActualizar().setOnAction(e -> actualizar());
        vista.getBtnEliminar().setOnAction(e -> eliminar());
        vista.getBtnLimpiar().setOnAction(e -> limpiar());
        vista.getBtnBuscar().setOnAction(e -> buscar());

        vista.getTablaParticipantes().setOnMouseClicked(
                e -> seleccionarParticipante()
        );
    }

    // REGISTRAR PARTICIPANTE
    private void registrar() {

        if (!validarCampos()) {
            return;
        }

        Participante participante = obtenerDatosFormulario();

        if (dao.registrar(participante)) {

            mostrarMensaje(Alert.AlertType.INFORMATION,
                    "Éxito", "Participante registrado correctamente.");

            cargarParticipantes();
            limpiar();

        } else {

            mostrarMensaje(Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo registrar. Comprueba que el DNI no esté registrado.");
        }
    }

    // ACTUALIZAR PARTICIPANTE
    private void actualizar() {

        Participante seleccionado =
                vista.getTablaParticipantes()
                        .getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "Atención", "Selecciona un participante de la tabla.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        Participante participante = obtenerDatosFormulario();
        participante.setIdParticipante(seleccionado.getIdParticipante());

        if (dao.actualizar(participante)) {

            mostrarMensaje(Alert.AlertType.INFORMATION,
                    "Éxito", "Participante actualizado correctamente.");

            cargarParticipantes();
            limpiar();

        } else {

            mostrarMensaje(Alert.AlertType.ERROR,
                    "Error", "No se pudo actualizar el participante.");
        }
    }

    // ELIMINAR PARTICIPANTE
    private void eliminar() {

        Participante seleccionado =
                vista.getTablaParticipantes()
                        .getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "Atención", "Selecciona un participante de la tabla.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("Eliminar participante");
        confirmacion.setContentText(
                "¿Deseas eliminar a "
                        + seleccionado.getNombres() + " "
                        + seleccionado.getApellidos() + "?"
        );

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent()
                && resultado.get() == ButtonType.OK) {

            if (dao.eliminar(seleccionado.getIdParticipante())) {

                mostrarMensaje(Alert.AlertType.INFORMATION,
                        "Éxito", "Participante eliminado correctamente.");

                cargarParticipantes();
                limpiar();

            } else {

                mostrarMensaje(Alert.AlertType.ERROR,
                        "Error",
                        "No se pudo eliminar. Comprueba si tiene inscripciones asociadas.");
            }
        }
    }

    // LISTAR PARTICIPANTES
    private void cargarParticipantes() {
        vista.getTablaParticipantes().getItems().setAll(dao.listar());
    }

    // BUSCAR PARTICIPANTES
    private void buscar() {

        String texto = vista.getTxtBuscar().getText()
                .trim().toLowerCase();

        List<Participante> resultados = dao.listar().stream()
                .filter(p ->
                        p.getNombres().toLowerCase().contains(texto)
                                || p.getApellidos().toLowerCase().contains(texto)
                                || p.getDni().contains(texto)
                                || p.getCorreo().toLowerCase().contains(texto)
                )
                .collect(Collectors.toList());

        vista.getTablaParticipantes().getItems().setAll(resultados);
    }

    // SELECCIONAR PARTICIPANTE
    private void seleccionarParticipante() {

        Participante participante =
                vista.getTablaParticipantes()
                        .getSelectionModel().getSelectedItem();

        if (participante != null) {

            vista.getTxtNombres().setText(participante.getNombres());
            vista.getTxtApellidos().setText(participante.getApellidos());
            vista.getTxtDni().setText(participante.getDni());
            vista.getTxtCorreo().setText(participante.getCorreo());
            vista.getTxtTelefono().setText(participante.getTelefono());
            vista.getTxtInstitucion().setText(participante.getInstitucion());
        }
    }

    // OBTENER DATOS DEL FORMULARIO
    private Participante obtenerDatosFormulario() {

        Participante participante = new Participante();

        participante.setNombres(vista.getTxtNombres().getText().trim());
        participante.setApellidos(vista.getTxtApellidos().getText().trim());
        participante.setDni(vista.getTxtDni().getText().trim());
        participante.setCorreo(vista.getTxtCorreo().getText().trim());
        participante.setTelefono(vista.getTxtTelefono().getText().trim());
        participante.setInstitucion(vista.getTxtInstitucion().getText().trim());

        return participante;
    }

    // VALIDAR CAMPOS
    private boolean validarCampos() {

        String dni = vista.getTxtDni().getText().trim();
        String correo = vista.getTxtCorreo().getText().trim();

        if (vista.getTxtNombres().getText().trim().isEmpty()
                || vista.getTxtApellidos().getText().trim().isEmpty()
                || dni.isEmpty()
                || correo.isEmpty()) {

            mostrarMensaje(Alert.AlertType.WARNING,
                    "Campos obligatorios",
                    "Completa nombres, apellidos, DNI y correo.");
            return false;
        }

        if (!dni.matches("\\d{8}")) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "DNI inválido", "El DNI debe tener exactamente 8 dígitos.");
            return false;
        }

        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "Correo inválido", "Ingresa un correo electrónico válido.");
            return false;
        }

        return true;
    }

    // LIMPIAR FORMULARIO
    private void limpiar() {

        vista.getTxtNombres().clear();
        vista.getTxtApellidos().clear();
        vista.getTxtDni().clear();
        vista.getTxtCorreo().clear();
        vista.getTxtTelefono().clear();
        vista.getTxtInstitucion().clear();

        vista.getTablaParticipantes()
                .getSelectionModel().clearSelection();
    }

    // MOSTRAR MENSAJES
    private void mostrarMensaje(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}