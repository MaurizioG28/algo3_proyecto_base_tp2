package edu.fiuba.algo3.vistas.vistas;


import edu.fiuba.algo3.controllers.ControladorIniciarJuego;
import edu.fiuba.algo3.controllers.ReproductorMusica;
import edu.fiuba.algo3.vistas.Grillas.GrillaBase;
import edu.fiuba.algo3.vistas.PantallaPrincipal;
import edu.fiuba.algo3.vistas.botones.BotonGenerico;
import edu.fiuba.algo3.vistas.mensajes.CatanMensaje;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VistaInicial extends StackPane {

    private Stage stage;


    public VistaInicial(Stage stagePrincipal, PantallaPrincipal contenedorPrincipal) {
        this.stage = stagePrincipal;

        Image imagen = new Image("Imagenes/pantalla-inicial.png");
        BackgroundImage fondoImagen = new BackgroundImage(imagen,
                BackgroundRepeat.ROUND,
                BackgroundRepeat.SPACE,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));

        Background fondo = new Background(fondoImagen);
        super.setBackground(fondo);

        VBox cajaPrincipal = new VBox(100);
        cajaPrincipal.setAlignment(Pos.TOP_CENTER);

        GrillaBase grilla= new GrillaBase(1280,720);
        CatanMensaje cantanMensaje = new CatanMensaje();

        cajaPrincipal.getChildren().add(cantanMensaje);

        BotonGenerico botonInicio = new BotonGenerico(new ControladorIniciarJuego(stagePrincipal, contenedorPrincipal),"Iniciar Juego");
        cajaPrincipal.getChildren().add(botonInicio);

        stage.setTitle("Catan");
        stage.centerOnScreen();

        grilla.add(cajaPrincipal, 0, 1);
        super.getChildren().add(grilla);

    }
}
