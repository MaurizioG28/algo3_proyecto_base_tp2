package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.vistas.vistas.VistaIntercambioConLaBanca;
import edu.fiuba.algo3.vistas.vistas.VistaIntercambioEntreJugadores;
import edu.fiuba.algo3.vistas.vistas.VistaTablero2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorIntercambioEntreJugadores implements EventHandler<ActionEvent> {

    private final Catan catan;
    private final VistaTablero2 vistaPrincipal;

    public ControladorIntercambioEntreJugadores(Catan catan, VistaTablero2 vistaPrincipal) {
        this.catan = catan;
        this.vistaPrincipal = vistaPrincipal;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Stage stagePadre = (Stage) vistaPrincipal.getScene().getWindow();

        VistaIntercambioEntreJugadores ventana = new VistaIntercambioEntreJugadores(stagePadre);
        ventana.showAndWait(); // Pausa aquí hasta que se cierre la ventana de intercambio
        vistaPrincipal.actualizarInventario();

    }

}