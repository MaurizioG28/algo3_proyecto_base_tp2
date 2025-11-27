package edu.fiuba.algo3.controllers;

import javafx.scene.media.AudioClip;
import java.net.URL;

public class ReproductorEfectos {

    private static final String RUTA_CLICK = "/efectos/click.mp3";
    private static final String RUTA_HOVER = "/efectos/hover.mp3";

    public void reproducirClick() {
        reproducir(RUTA_CLICK, 1.0);
    }

    public void reproducirHover() {
        reproducir(RUTA_HOVER, 0.2); // Volumen bajo para que no moleste
    }

private void reproducir(String ruta, double volumen) {
    URL url = getClass().getResource(ruta);

    if (url == null) {
        System.err.println("ERROR CRÍTICO DE AUDIO");
        System.err.println("Java intentó buscar: " + ruta);
        System.err.println("Dentro de: " + getClass().getResource("/"));
        return;
    }

    try {
        AudioClip clip = new AudioClip(url.toExternalForm());
        clip.setVolume(volumen);
        clip.play();
    } catch (Exception e) {
        System.err.println("El archivo existe pero falló al reproducir: " + e.getMessage());
    }
}
}