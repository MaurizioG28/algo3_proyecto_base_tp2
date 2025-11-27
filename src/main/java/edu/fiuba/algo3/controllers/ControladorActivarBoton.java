package edu.fiuba.algo3.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ControladorActivarBoton implements EventHandler<MouseEvent> {
    private Button boton;
    private String botonColor;

    public ControladorActivarBoton(Button boton, String botonColor) {
        this.boton = boton;
        this.botonColor = botonColor;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

    }
}
