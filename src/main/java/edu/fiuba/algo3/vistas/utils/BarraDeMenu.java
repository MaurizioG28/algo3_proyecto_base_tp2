package edu.fiuba.algo3.vistas.utils;

import edu.fiuba.algo3.controllers.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;


public class BarraDeMenu extends MenuBar {

    MenuItem opcionPantallaCompleta = new MenuItem("Pantalla completa");
    private String rutaMusica = "\\src\\main\\java\\edu\\fiuba\\algo3\\resources\\musica\\";

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
        //agregarMusicas(menuMusica);

        menuArchivo.getItems().addAll(opcionSalir);

        menuAyuda.getItems().addAll(opcionAcercaDe, opcionAyuda);
        menuVer.getItems().addAll(opcionPantallaCompleta);

        this.getMenus().addAll(menuArchivo, menuVer, menuMusica, menuAyuda);

    }

    public void aplicacionMaximizada() {
        opcionPantallaCompleta.setDisable(false);
    }

//    private void agregarMusicas(Menu menuMusica) {
//        ReproductorMusica unReproductor = new ReproductorMusica();
//        MenuItem opcionSinMusica = new MenuItem("Sin música");
//        MenuItem opcionMusicaComun = new MenuItem("Kahoot Original");
//        MenuItem opcionMusicaArcade = new MenuItem("Kahoot Arcade");
//        MenuItem opcionMusicaCyberPunk = new MenuItem("Kahoot Cyber-hoot");
//        MenuItem opcionMusicaHappy = new MenuItem("kahoot happy");
//        MenuItem opcionMusicaSlow = new MenuItem("kahoot slow");
//        MenuItem opcionMusicaHalloween = new MenuItem("Kahoot Halloween");
//        MenuItem opcionMusicaMine = new MenuItem("kahoot Mine");
//
//        opcionSinMusica.setOnAction(new ControladorMusica("", unReproductor));
//        opcionMusicaComun.setOnAction(new ControladorMusica(rutaMusica + "classic.mp3", unReproductor));
//        opcionMusicaArcade.setOnAction(new ControladorMusica(rutaMusica + "arcade.mp3", unReproductor));
//        opcionMusicaCyberPunk.setOnAction(new ControladorMusica(rutaMusica + "cyberpunk-bits.mp3", unReproductor));
//        opcionMusicaHappy.setOnAction(new ControladorMusica(rutaMusica + "happy.mp3", unReproductor));
//        opcionMusicaSlow.setOnAction(new ControladorMusica(rutaMusica + "slow-bits.mp3", unReproductor));
//        opcionMusicaHalloween.setOnAction(new ControladorMusica(rutaMusica + "halloween.mp3", unReproductor));
//        opcionMusicaMine.setOnAction(new ControladorMusica(rutaMusica + "Miner.mp3", unReproductor));
//
//
//        menuMusica.getItems().addAll(opcionSinMusica, opcionMusicaComun,
//                opcionMusicaArcade,
//                opcionMusicaCyberPunk,
//                opcionMusicaHappy,
//                opcionMusicaSlow,
//                opcionMusicaHalloween,
//                opcionMusicaMine
//        );


//    }

}
