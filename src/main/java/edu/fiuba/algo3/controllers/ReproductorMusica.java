package edu.fiuba.algo3.controllers;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class ReproductorMusica {

    private MediaPlayer mediaPlayer;

    public ReproductorMusica() {
        // Ruta relativa desde la carpeta resources
        String rutaInicial = "/edu/fiuba/algo3/resources/musica/classic.mp3";
        escucharTema(rutaInicial);
    }

    public void escucharTema(String direccion) {
        // 1. Detener el tema anterior si existe
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        // 2. Si la dirección no está vacía (opción "Sin Música"), reproducir el nuevo
        if (direccion != null && !direccion.isEmpty()) {
            reproducirTema(direccion);
        }
    }

    private void reproducirTema(String direccion) {
        // VALIDACIÓN DE RUTA: Esto busca dentro del JAR o carpeta de compilación
        URL url = getClass().getResource(direccion);

        if (url == null) {
            System.err.println("No se encontró el archivo: " + direccion);
            return; // Evita que explote el programa
        }

        try {
            Media media = new Media(url.toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop infinito
            mediaPlayer.setVolume(0.5); // IMPORTANTE: Va de 0.0 a 1.0
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error al cargar multimedia: " + e.getMessage());
        }
    }
}
