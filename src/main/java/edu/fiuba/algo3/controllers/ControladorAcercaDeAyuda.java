package edu.fiuba.algo3.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class ControladorAcercaDeAyuda implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de...");
        alert.setHeaderText("Acerca de la aplicacion");
        String mensaje = "Juego Catan creado para el TP2 de la materia Algoritmos y programacion 3 por el grupo 5:\n" +
                "\t*Enzo Ponferrada \n" +
                "\t*Ariel Cruz\n" +
                "\t*Nicolas Guerrero \n" +
                "\t*Tomas Echeverria \n" +
                "\t*Maurizio Giannantonio\n";
        alert.setContentText(mensaje);
        alert.show();
    }
}
