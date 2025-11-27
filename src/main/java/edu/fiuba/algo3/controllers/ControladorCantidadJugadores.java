package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.vistas.PantallaPrincipal;
import edu.fiuba.algo3.vistas.vistas.VistaPedirNombreJugadores;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.awt.event.ActionListener;

public class ControladorCantidadJugadores implements EventHandler<ActionEvent> {
    private Stage stage;
    private PantallaPrincipal pantallaPrincipal;
    private ComboBox<String> comboBoxCantidadJugadores;

    public ControladorCantidadJugadores(Stage stage, PantallaPrincipal pantallaPrincipal, ComboBox<String> comboBoxCantidadJugadores) {
        this.stage = stage;
        this.pantallaPrincipal = pantallaPrincipal;
        this.comboBoxCantidadJugadores = comboBoxCantidadJugadores;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        String seleccion = comboBoxCantidadJugadores.getValue();
        int cantidad = Integer.parseInt(seleccion);
        pantallaPrincipal.setCentro(new VistaPedirNombreJugadores(stage, pantallaPrincipal, cantidad));


    }
}
