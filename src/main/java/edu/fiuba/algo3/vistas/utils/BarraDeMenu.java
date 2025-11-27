package edu.fiuba.algo3.vistas.utils;

import edu.fiuba.algo3.controllers.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;


public class BarraDeMenu extends MenuBar {

    private MenuItem opcionPantallaCompleta = new MenuItem("Pantalla completa");
    private String rutaMusica = "/musica/";
    private ReproductorMusica reproductor;

    public BarraDeMenu(Stage stage) {

        Menu menuArchivo = new Menu("Archivo");
        Menu menuVer = new Menu("Ver");
        Menu menuMusica = new Menu("Música");
        Menu menuAyuda = new Menu("Ayuda");

        MenuItem opcionSalir = new MenuItem("Salir");
        MenuItem opcionAcercaDe = new MenuItem("Acerca de ...");
        MenuItem opcionAyuda = new MenuItem("Ayuda");

        opcionSalir.setOnAction(new ControladorFinalizarJuego());
        opcionAcercaDe.setOnAction(new ControladorAcercaDeAyuda());
        opcionAyuda.setOnAction(new ControladorAyudaDelJuego());
        opcionPantallaCompleta.setOnAction(new ControladorPantallaCompleta(stage, opcionPantallaCompleta));
        agregarMusicas(menuMusica);

        menuArchivo.getItems().addAll(opcionSalir);

        menuAyuda.getItems().addAll(opcionAcercaDe, opcionAyuda);
        menuVer.getItems().addAll(opcionPantallaCompleta);

        this.getMenus().addAll(menuArchivo, menuVer, menuMusica, menuAyuda);

    }

    public void aplicacionMaximizada() {
        opcionPantallaCompleta.setDisable(false);
    }

    private void agregarMusicas(Menu menuMusica) {
        this.reproductor = new ReproductorMusica();
        MenuItem opcionSinMusica = new MenuItem("Sin música");
        MenuItem opcionMusicaComun = new MenuItem("Catan clasica");
        MenuItem opcionMusicaMedieval= new MenuItem("Medieval");
        MenuItem opcionMusicaHappy = new MenuItem("Happy");
        MenuItem opcionMusicaTravel = new MenuItem("Travelling");

        opcionSinMusica.setOnAction(e -> reproductor.escucharTema(""));

        opcionMusicaComun.setOnAction(e -> reproductor.escucharTema(rutaMusica + "classic.mp3"));
        opcionMusicaMedieval.setOnAction(e -> reproductor.escucharTema(rutaMusica + "medieval-folk-rpg.mp3"));
        opcionMusicaHappy.setOnAction(e -> reproductor.escucharTema(rutaMusica + "medieval-happy-music-.mp3"));
        opcionMusicaTravel.setOnAction(e -> reproductor.escucharTema(rutaMusica + "travelling-on-medievalgame-music.mp3"));

        menuMusica.getItems().addAll(opcionSinMusica,
                opcionMusicaComun,
                opcionMusicaMedieval,
                opcionMusicaHappy,
                opcionMusicaTravel
        );
    }


}
