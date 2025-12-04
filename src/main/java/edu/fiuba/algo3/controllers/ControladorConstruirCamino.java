package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.vistas.vistas.VistaTablero2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControladorConstruirCamino implements EventHandler<ActionEvent> {
    private final Catan catan;
    private final VistaTablero2 vista;

    public ControladorConstruirCamino(Catan catan, VistaTablero2 vista) {
        this.catan = catan;
        this.vista = vista;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
