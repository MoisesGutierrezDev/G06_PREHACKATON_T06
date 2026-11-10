package vallegrande.edu.pe.sgec.controller;

import vallegrande.edu.pe.sgec.model.Evento;
import vallegrande.edu.pe.sgec.model.EventoDAO;
import vallegrande.edu.pe.sgec.view.EventoView;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventoController {

    private final EventoView vista;
    private final EventoDAO dao;

    public EventoController(EventoView vista) {
        this.vista = vista;
        this.dao = new EventoDAO();

        inicializarEventos();
        cargarEventos();
    }

    // CONFIGURAR BOTONES
    private void inicializarEventos() {

        vista.getBtnRegistrar().setOnAction(e -> registrar());

        vista.getBtnActualizar().setOnAction(e -> actualizar());

        vista.getBtnEliminar().setOnAction(e -> eliminar());

        vista.getBtnLimpiar().setOnAction(e -> limpiar());

        vista.getBtnBuscar().setOnAction(e -> buscar());

        vista.getTablaEventos().setOnMouseClicked(e -> seleccionarEvento());
    }

    // REGISTRAR EVENTO
    private void registrar() {

        if (!validarCampos()) {
            return;
        }

        Evento evento = obtenerDatosFormulario();

        if (dao.registrar(evento)) {

            mostrarMensaje(
                    Alert.AlertType.INFORMATION,
                    "Éxito",
                    "Evento registrado correctamente."
            );

            cargarEventos();
            limpiar();

        } else {

            mostrarMensaje(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo registrar el evento."
            );
        }
    }

    // ACTUALIZAR EVENTO
    private void actualizar() {

        Evento seleccionado =
                vista.getTablaEventos().getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "Atención", "Selecciona un evento de la tabla.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        Evento evento = obtenerDatosFormulario();
        evento.setIdEvento(seleccionado.getIdEvento());

        if (dao.actualizar(evento)) {

            mostrarMensaje(Alert.AlertType.INFORMATION,
                    "Éxito", "Evento actualizado correctamente.");

            cargarEventos();
            limpiar();

        } else {

            mostrarMensaje(Alert.AlertType.ERROR,
                    "Error", "No se pudo actualizar el evento.");
        }
    }

    // ELIMINAR EVENTO
    private void eliminar() {

        Evento seleccionado =
                vista.getTablaEventos().getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "Atención", "Selecciona un evento de la tabla.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("Eliminar evento");
        confirmacion.setContentText(
                "¿Deseas eliminar el evento " + seleccionado.getNombre() + "?"
        );

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() &&
                resultado.get() == ButtonType.OK) {

            if (dao.eliminar(seleccionado.getIdEvento())) {

                mostrarMensaje(Alert.AlertType.INFORMATION,
                        "Éxito", "Evento eliminado correctamente.");

                cargarEventos();
                limpiar();

            } else {

                mostrarMensaje(Alert.AlertType.ERROR,
                        "Error",
                        "No se pudo eliminar. Comprueba si tiene inscripciones asociadas.");
            }
        }
    }

    // LISTAR EVENTOS
    private void cargarEventos() {

        vista.getTablaEventos().getItems().setAll(dao.listar());
    }

    // BUSCAR EVENTOS
    private void buscar() {

        String texto = vista.getTxtBuscar().getText()
                .trim().toLowerCase();

        List<Evento> resultados = dao.listar().stream()
                .filter(evento ->
                        evento.getNombre().toLowerCase().contains(texto))
                .collect(Collectors.toList());

        vista.getTablaEventos().getItems().setAll(resultados);
    }

    // SELECCIONAR EVENTO DE LA TABLA
    private void seleccionarEvento() {

        Evento evento =
                vista.getTablaEventos().getSelectionModel().getSelectedItem();

        if (evento != null) {

            vista.getTxtNombre().setText(evento.getNombre());
            vista.getTxtDescripcion().setText(evento.getDescripcion());
            vista.getDpFecha().setValue(evento.getFecha());
            vista.getTxtLugar().setText(evento.getLugar());
            vista.getCbModalidad().setValue(evento.getModalidad());
            vista.getCbEstado().setValue(evento.getEstado());
        }
    }

    // OBTENER DATOS DEL FORMULARIO
    private Evento obtenerDatosFormulario() {

        Evento evento = new Evento();

        evento.setNombre(vista.getTxtNombre().getText().trim());
        evento.setDescripcion(vista.getTxtDescripcion().getText().trim());
        evento.setFecha(vista.getDpFecha().getValue());
        evento.setLugar(vista.getTxtLugar().getText().trim());
        evento.setModalidad(vista.getCbModalidad().getValue());
        evento.setEstado(vista.getCbEstado().getValue());

        return evento;
    }

    // VALIDAR CAMPOS
    private boolean validarCampos() {

        if (vista.getTxtNombre().getText().trim().isEmpty()
                || vista.getTxtLugar().getText().trim().isEmpty()
                || vista.getDpFecha().getValue() == null
                || vista.getCbModalidad().getValue() == null
                || vista.getCbEstado().getValue() == null) {

            mostrarMensaje(Alert.AlertType.WARNING,
                    "Campos obligatorios",
                    "Completa todos los campos obligatorios.");
            return false;
        }

        if (vista.getDpFecha().getValue().isBefore(LocalDate.now())) {

            mostrarMensaje(Alert.AlertType.WARNING,
                    "Fecha inválida",
                    "La fecha del evento no puede ser anterior a hoy.");
            return false;
        }

        return true;
    }

    // LIMPIAR FORMULARIO
    private void limpiar() {

        vista.getTxtNombre().clear();
        vista.getTxtDescripcion().clear();
        vista.getDpFecha().setValue(null);
        vista.getTxtLugar().clear();
        vista.getCbModalidad().setValue(null);
        vista.getCbEstado().setValue(null);

        vista.getTablaEventos().getSelectionModel().clearSelection();
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