package edu.fiuba.algo3.controllers;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class ReproductorMusica {


    private MediaPlayer mediaPlayer;

    public ReproductorMusica() {
        String temaInicial = "/musica/classic.mp3";
        reproducirTema(temaInicial);
    }
    public void escucharTema(String direccion) {
        // 1. Si ya hay algo sonando, lo detenemos
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        // 2. Si la dirección es válida, reproducimos el nuevo tema
        if (direccion != null && !direccion.isEmpty()) {
            reproducirTema(direccion);
        }
    }

    private void reproducirTema(String direccion) {
        URL url = getClass().getResource(direccion);

        if (url == null) {
            System.err.println("ERROR DE MÚSICA: No se encontró el archivo: " + direccion);
            return;
        }

        try {
            Media media = new Media(url.toExternalForm());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            // Volumen al 50% (0.5).
            mediaPlayer.setVolume(0.1);

            mediaPlayer.play();

        } catch (Exception e) {
            System.err.println("Error al cargar la música: " + e.getMessage());
        }
    }

    public void mutear() {
        if (mediaPlayer != null) {
            mediaPlayer.setMute(!mediaPlayer.isMute());
        }
    }

}
