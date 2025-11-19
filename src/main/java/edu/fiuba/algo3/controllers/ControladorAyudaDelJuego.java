package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.vistas.vistas.VistaAyuda;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ControladorAyudaDelJuego implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        Stage stageAyuda = new Stage();
        Scene sceneAyuda = new Scene(new VistaAyuda(), 900, 600);
        Image icono = new Image("file:" + System.getProperty("user.dir") + "src/main/java/edu/fiuba/algo3/resources/imagenes/help.png");
        stageAyuda.getIcons().add(icono);
        stageAyuda.setTitle("Ayuda Catan!");
        stageAyuda.setScene(sceneAyuda);
        stageAyuda.setResizable(false);
        stageAyuda.show();
    }
}
