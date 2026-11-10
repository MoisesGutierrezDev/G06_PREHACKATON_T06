package vallegrande.edu.pe.sgec.controller;

import vallegrande.edu.pe.sgec.model.Inscripcion;
import vallegrande.edu.pe.sgec.model.InscripcionDAO;
import vallegrande.edu.pe.sgec.model.EventoDAO;
import vallegrande.edu.pe.sgec.model.ParticipanteDAO;
import vallegrande.edu.pe.sgec.view.InscripcionView;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InscripcionController {

    private final InscripcionView vista;
    private final InscripcionDAO dao;
    private final EventoDAO eventoDAO;
    private final ParticipanteDAO participanteDAO;

    public InscripcionController(InscripcionView vista) {
        this.vista = vista;
        this.dao = new InscripcionDAO();
        this.eventoDAO = new EventoDAO();
        this.participanteDAO = new ParticipanteDAO();

        inicializarEventos();
        cargarCombos();
        cargarInscripciones();
    }

    // CONFIGURAR BOTONES
    private void inicializarEventos() {

        vista.getBtnRegistrar().setOnAction(e -> registrar());
        vista.getBtnActualizar().setOnAction(e -> actualizar());
        vista.getBtnEliminar().setOnAction(e -> eliminar());
        vista.getBtnLimpiar().setOnAction(e -> limpiar());
        vista.getBtnBuscar().setOnAction(e -> buscar());

        vista.getTablaInscripciones().setOnMouseClicked(
                e -> seleccionarInscripcion()
        );
    }

    // CARGAR COMBOS DE EVENTOS Y PARTICIPANTES
    private void cargarCombos() {

        vista.getCbEvento().setItems(
                FXCollections.observableArrayList(
                        eventoDAO.listar().stream()
                                .map(evento -> evento.getIdEvento())
                                .collect(Collectors.toList())
                )
        );

        vista.getCbParticipante().setItems(
                FXCollections.observableArrayList(
                        participanteDAO.listar().stream()
                                .map(participante ->
                                        participante.getIdParticipante())
                                .collect(Collectors.toList())
                )
        );
    }

    // REGISTRAR INSCRIPCIÓN
    private void registrar() {

        if (!validarCampos()) {
            return;
        }

        Inscripcion inscripcion = obtenerDatosFormulario();

        if (dao.registrar(inscripcion)) {

            mostrarMensaje(Alert.AlertType.INFORMATION,
                    "Éxito", "Inscripción registrada correctamente.");

            cargarInscripciones();
            limpiar();

        } else {

            mostrarMensaje(Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo registrar. Comprueba que el participante no esté inscrito dos veces en el mismo evento.");
        }
    }

    // ACTUALIZAR INSCRIPCIÓN
    private void actualizar() {

        Inscripcion seleccionada =
                vista.getTablaInscripciones()
                        .getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "Atención", "Selecciona una inscripción de la tabla.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        Inscripcion inscripcion = obtenerDatosFormulario();
        inscripcion.setIdInscripcion(seleccionada.getIdInscripcion());

        if (dao.actualizar(inscripcion)) {

            mostrarMensaje(Alert.AlertType.INFORMATION,
                    "Éxito", "Inscripción actualizada correctamente.");

            cargarInscripciones();
            limpiar();

        } else {

            mostrarMensaje(Alert.AlertType.ERROR,
                    "Error", "No se pudo actualizar la inscripción.");
        }
    }

    // ELIMINAR INSCRIPCIÓN
    private void eliminar() {

        Inscripcion seleccionada =
                vista.getTablaInscripciones()
                        .getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "Atención", "Selecciona una inscripción de la tabla.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("Eliminar inscripción");
        confirmacion.setContentText(
                "¿Deseas eliminar la inscripción N.º "
                        + seleccionada.getIdInscripcion() + "?"
        );

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent()
                && resultado.get() == ButtonType.OK) {

            if (dao.eliminar(seleccionada.getIdInscripcion())) {

                mostrarMensaje(Alert.AlertType.INFORMATION,
                        "Éxito", "Inscripción eliminada correctamente.");

                cargarInscripciones();
                limpiar();

            } else {

                mostrarMensaje(Alert.AlertType.ERROR,
                        "Error", "No se pudo eliminar la inscripción.");
            }
        }
    }

    // LISTAR INSCRIPCIONES
    private void cargarInscripciones() {
        vista.getTablaInscripciones().getItems().setAll(dao.listar());
    }

    // BUSCAR INSCRIPCIONES POR ID
    private void buscar() {

        String texto = vista.getTxtBuscar().getText().trim();

        if (texto.isEmpty()) {
            cargarInscripciones();
            return;
        }

        List<Inscripcion> resultados = dao.listar().stream()
                .filter(i ->
                        String.valueOf(i.getIdInscripcion()).contains(texto)
                                || String.valueOf(i.getIdEvento()).contains(texto)
                                || String.valueOf(i.getIdParticipante()).contains(texto)
                )
                .collect(Collectors.toList());

        vista.getTablaInscripciones().getItems().setAll(resultados);
    }

    // SELECCIONAR INSCRIPCIÓN
    private void seleccionarInscripcion() {

        Inscripcion inscripcion =
                vista.getTablaInscripciones()
                        .getSelectionModel().getSelectedItem();

        if (inscripcion != null) {

            vista.getCbEvento().setValue(inscripcion.getIdEvento());
            vista.getCbParticipante().setValue(
                    inscripcion.getIdParticipante()
            );
            vista.getDpFechaInscripcion().setValue(
                    inscripcion.getFechaInscripcion()
            );
            vista.getCbEstado().setValue(inscripcion.getEstado());
        }
    }

    // OBTENER DATOS DEL FORMULARIO
    private Inscripcion obtenerDatosFormulario() {

        Inscripcion inscripcion = new Inscripcion();

        inscripcion.setIdEvento(vista.getCbEvento().getValue());
        inscripcion.setIdParticipante(
                vista.getCbParticipante().getValue()
        );
        inscripcion.setFechaInscripcion(
                vista.getDpFechaInscripcion().getValue()
        );
        inscripcion.setEstado(vista.getCbEstado().getValue());

        return inscripcion;
    }

    // VALIDAR CAMPOS
    private boolean validarCampos() {

        if (vista.getCbEvento().getValue() == null
                || vista.getCbParticipante().getValue() == null
                || vista.getDpFechaInscripcion().getValue() == null
                || vista.getCbEstado().getValue() == null) {

            mostrarMensaje(Alert.AlertType.WARNING,
                    "Campos obligatorios",
                    "Completa todos los campos de la inscripción.");

            return false;
        }

        return true;
    }

    // LIMPIAR FORMULARIO
    private void limpiar() {

        vista.getCbEvento().setValue(null);
        vista.getCbParticipante().setValue(null);
        vista.getDpFechaInscripcion().setValue(
                java.time.LocalDate.now()
        );
        vista.getCbEstado().setValue(null);

        vista.getTablaInscripciones()
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