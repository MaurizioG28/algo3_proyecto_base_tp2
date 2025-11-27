package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.vistas.PantallaPrincipal;
import edu.fiuba.algo3.vistas.vistas.VistaPedirCantidadJugadores;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class ControladorIniciarJuego implements EventHandler<ActionEvent> {
    private Stage stage;
    private PantallaPrincipal pantallaPrincipal;

    public ControladorIniciarJuego(Stage stage,PantallaPrincipal pantallaPrincipal) {
        this.stage = stage;
        this.pantallaPrincipal = pantallaPrincipal;

    }

    @Override
    public void handle(ActionEvent actionEvent) {
        pantallaPrincipal.setCentro(new VistaPedirCantidadJugadores(stage,pantallaPrincipal));
    }
}
