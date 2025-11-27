package edu.fiuba.algo3.vistas.vistas;

import edu.fiuba.algo3.Estilos;
import edu.fiuba.algo3.controllers.ControladorCantidadJugadores;
import edu.fiuba.algo3.vistas.Grillas.GrillaBase;
import edu.fiuba.algo3.vistas.PantallaPrincipal;
import edu.fiuba.algo3.vistas.botones.BotonGenerico;
import edu.fiuba.algo3.vistas.mensajes.CatanMensaje;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class VistaPedirCantidadJugadores extends StackPane{

    private static final String IMAGEN_RUTA = "imagenes/pantalla-inicial.png";
    private static final double ANCHO_VENTANA = 1280;
    private static final double ALTO_VENTANA = 720;
    private static final double ESPACIADO_CENTRAL = 40;
    private ComboBox<String> comboBoxCantidadJugadores;

    public VistaPedirCantidadJugadores(Stage stage, PantallaPrincipal pantallaPrincipal) {
        configurarFondo();
        GrillaBase grilla = new GrillaBase(ANCHO_VENTANA, ALTO_VENTANA);

        comboBoxCantidadJugadores = new ComboBox<>();

        VBox nombreJuego = crearNombreJuego();
        VBox cajaPregunta = crearCajaPregunta();
        VBox botonConfirmado = crearBotonConfirmado(stage, pantallaPrincipal);

        grilla.add(nombreJuego, 0, 0);
        grilla.add(cajaPregunta, 0, 1);
        grilla.add(botonConfirmado, 0, 2);

        super.getChildren().add(grilla);
    }

    private void configurarFondo() {
        Image imagen = new Image(IMAGEN_RUTA);
        BackgroundImage fondoImagen = new BackgroundImage(imagen,
                BackgroundRepeat.ROUND,
                BackgroundRepeat.SPACE,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));
        Background fondo = new Background(fondoImagen);
        super.setBackground(fondo);
    }

    private VBox crearNombreJuego() {
        VBox nombreJuego = new VBox(1);
        nombreJuego.setAlignment(Pos.TOP_CENTER);
        CatanMensaje textoCatanInicio = new CatanMensaje();
        nombreJuego.getChildren().add(textoCatanInicio);
        return nombreJuego;
    }

    private VBox crearCajaPregunta() {
        VBox cajaPregunta = new VBox(ESPACIADO_CENTRAL);
        cajaPregunta.setAlignment(Pos.CENTER);

        StackPane contenedor = new StackPane();

        String estiloContenedor = "-fx-background-color: rgba(75, 0, 130, 0.85);" + // Indigo semitransparente
                "-fx-background-radius: 20;" +               // Bordes redondeados
                "-fx-border-color: #FFD700;" +               // Borde Dorado
                "-fx-border-width: 3;" +
                "-fx-border-radius: 20;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0);";

        // Limitamos el ancho para que no ocupe toda la pantalla (se ve más elegante)
        contenedor.setStyle(estiloContenedor);
        contenedor.setMaxWidth(800);
        contenedor.setPadding(new Insets(20)); // Más espacio interno

        HBox hboxContenido = new HBox(20); // HBox para alinear el label y el textfield horizontalmente
        hboxContenido.setAlignment(Pos.CENTER); // Alinear al centro
        hboxContenido.setPadding(new Insets(10));

        Label labelCantidadJugadores = new Label("Seleccione cantidad de jugadores:");
        labelCantidadJugadores.setFont(Font.font(Estilos.FUENTE, FontWeight.BOLD, 24)); // Fuente más gruesa
        labelCantidadJugadores.setTextFill(Color.WHITE);
        // Sombra suave al texto para leerse mejor
        labelCantidadJugadores.setEffect(new DropShadow(2, Color.BLACK));


        comboBoxCantidadJugadores.getItems().addAll("2", "3", "4");
        String estiloCombo = "-fx-background-color: #fdf5e6;" + // Color Crema/Pergamino
                "-fx-font-family: '" + Estilos.FUENTE + "';" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #8B4513;" + // Borde marrón madera
                "-fx-border-width: 2;" +
                "-fx-border-radius: 10;";
        comboBoxCantidadJugadores.setStyle(estiloCombo);
        comboBoxCantidadJugadores.setPrefWidth(100); // Ancho fijo pequeño
        comboBoxCantidadJugadores.setPrefHeight(40);

        hboxContenido.getChildren().addAll(labelCantidadJugadores, comboBoxCantidadJugadores);

        // Añadir HBox interno al contenedor con fondo
        contenedor.getChildren().add(hboxContenido);

        // Añadir el contenedor al VBox principal
        cajaPregunta.getChildren().add(contenedor);

        return cajaPregunta;
    }

    private VBox crearBotonConfirmado(Stage stage, PantallaPrincipal pantallaPrincipal) {
        VBox botonConfirmado = new VBox(0);
        botonConfirmado.setAlignment(Pos.BOTTOM_CENTER);
        BotonGenerico botonSiguiente = new BotonGenerico(new ControladorCantidadJugadores(stage, pantallaPrincipal, comboBoxCantidadJugadores),"Siguiente");
        botonSiguiente.disableProperty().bind(comboBoxCantidadJugadores.valueProperty().isNull());
        botonConfirmado.getChildren().add(botonSiguiente);
        return botonConfirmado;
    }

}
