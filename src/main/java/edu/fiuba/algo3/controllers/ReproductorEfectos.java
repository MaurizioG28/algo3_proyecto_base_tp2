package edu.fiuba.algo3.controllers;

import javafx.scene.media.AudioClip;
import java.net.URL;

public class ReproductorEfectos {

    // Rutas relativas a resources
    private static final String RUTA_CLICK = "/edu/fiuba/algo3/resources/efectos/click2.mp3";
    //private static final String RUTA_HOVER = "/edu/fiuba/algo3/resources/efectos/hover.mp3";

    public void reproducirClick() {
        reproducir(RUTA_CLICK, 1.0);
    }

    public void reproducirHover() {
        //reproducir(RUTA_HOVER, 0.2); // Volumen bajo para que no moleste
    }

    private void reproducir(String ruta, double volumen) {
        URL url = getClass().getResource(ruta);
        if (url != null) {
            AudioClip clip = new AudioClip(url.toExternalForm());
            clip.setVolume(volumen);
            clip.play();
        }
    }
}