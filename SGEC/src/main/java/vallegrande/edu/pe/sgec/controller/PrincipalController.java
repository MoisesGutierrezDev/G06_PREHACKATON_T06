package vallegrande.edu.pe.sgec.controller;

import javafx.scene.Node;
import javafx.stage.Stage;
import vallegrande.edu.pe.sgec.view.EventoView;
import vallegrande.edu.pe.sgec.view.InscripcionView;
import vallegrande.edu.pe.sgec.view.ParticipanteView;
import vallegrande.edu.pe.sgec.view.PrincipalView;

public class PrincipalController {

    private final PrincipalView vista;
    private final Stage escenario;

    public PrincipalController(PrincipalView vista, Stage escenario) {
        this.vista = vista;
        this.escenario = escenario;
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnInicio().setOnAction(e -> volverPrincipal());
        vista.getBtnEventos().setOnAction(e -> abrirEventos());
        vista.getBtnParticipantes().setOnAction(e -> abrirParticipantes());
        vista.getBtnInscripciones().setOnAction(e -> abrirInscripciones());
    }

    private void abrirEventos() {
        EventoView modulo = new EventoView();
        new EventoController(modulo);
        mostrarModulo(modulo, "Eventos");
    }

    private void abrirParticipantes() {
        ParticipanteView modulo = new ParticipanteView();
        new ParticipanteController(modulo);
        mostrarModulo(modulo, "Participantes");
    }

    private void abrirInscripciones() {
        InscripcionView modulo = new InscripcionView();
        new InscripcionController(modulo);
        mostrarModulo(modulo, "Inscripciones");
    }

    private void mostrarModulo(Node modulo, String titulo) {
        vista.mostrarContenido(modulo);
        escenario.setTitle("SGEC - " + titulo);
    }

    private void volverPrincipal() {
        vista.mostrarBienvenida();
        escenario.setTitle("SGEC - Sistema de Gestión de Eventos y Conferencias");
    }
}