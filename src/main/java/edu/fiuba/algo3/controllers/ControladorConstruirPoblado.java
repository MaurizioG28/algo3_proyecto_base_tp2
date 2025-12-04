package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.vistas.vistas.VistaTablero2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControladorConstruirPoblado implements EventHandler<ActionEvent> {

    private final Catan catan;
    private final VistaTablero2 vista;

    public ControladorConstruirPoblado(Catan catan, VistaTablero2 vista) {
        this.catan = catan;
        this.vista = vista;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        System.out.println(">>> Solicitando construir Poblado...");
        // Aquí  la lógica visual de mostrar los vértices en el mapa
        // vista.mostrarVerticesParaConstruir();
        // catan.getManagerTurno()...
        vista.verificarGanador();
    }

}
