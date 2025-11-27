package edu.fiuba.algo3.vistas.vistas;

import edu.fiuba.algo3.Estilos;
import edu.fiuba.algo3.controllers.ControladorCrearPartida;
import edu.fiuba.algo3.vistas.Grillas.GrillaBase;
import edu.fiuba.algo3.vistas.PantallaPrincipal;
import edu.fiuba.algo3.vistas.botones.BotonGenerico;
import edu.fiuba.algo3.vistas.mensajes.CatanMensaje;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;

public class VistaPedirNombreJugadores extends StackPane {

    private static final String IMAGEN_RUTA = "/src/main/java/edu/fiuba/algo3/resources/Imagenes/pantalla-inicial.png";
    private static final double ANCHO_VENTANA = 1280;
    private static final double ALTO_VENTANA = 720;

    private ArrayList<TextField> nombresIngresados;
    private ArrayList<ColorPicker> coloresElegidos;

    public VistaPedirNombreJugadores(Stage stage, PantallaPrincipal pantallaPrincipal, int cantidadJugadores) {
        this.nombresIngresados = new ArrayList<>();
        this.coloresElegidos = new ArrayList<>();

        configurarFondo();

        GrillaBase grilla = new GrillaBase(ANCHO_VENTANA, ALTO_VENTANA);

        VBox nombreJuego = crearNombreJuego();
        VBox formularioCentral = crearFormularioJugadores(cantidadJugadores);
        VBox botonConfirmado = crearBotonConfirmado(stage, pantallaPrincipal);

        // Ubicación en la grilla (Fila 0: Título, Fila 1: Formulario, Fila 2: Botón)
        grilla.add(nombreJuego, 0, 0);
        grilla.add(formularioCentral, 0, 1);
        grilla.add(botonConfirmado, 0, 2);

        super.getChildren().add(grilla);
    }

    private void configurarFondo() {
        Image imagen = new Image("file:" + System.getProperty("user.dir") + IMAGEN_RUTA);
        BackgroundImage fondoImagen = new BackgroundImage(imagen,
                BackgroundRepeat.ROUND,
                BackgroundRepeat.SPACE,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));
        Background fondo = new Background(fondoImagen);
        super.setBackground(fondo);
    }

    private VBox crearNombreJuego() {
        VBox nombreJuego = new VBox();
        nombreJuego.setAlignment(Pos.CENTER);
        CatanMensaje catanMensaje = new CatanMensaje();
        catanMensaje.setScaleX(0.8);
        catanMensaje.setScaleY(0.8);
        nombreJuego.getChildren().add(catanMensaje);
        return nombreJuego;
    }

    private VBox crearFormularioJugadores(int cantidadJugadores) {
        VBox contenedorPrincipal = new VBox();
        contenedorPrincipal.setAlignment(Pos.CENTER);

        //EL PANEL DE FONDO (Glass Effect)
        VBox panelFormulario = new VBox(15); // Espacio vertical entre renglones
        panelFormulario.setAlignment(Pos.CENTER);
        panelFormulario.setPadding(new Insets(30));
        panelFormulario.setMaxWidth(850);

        // Estilo CSS: Fondo negro transparente + Borde Dorado + Sombra
        String estiloPanel = "-fx-background-color: rgba(0, 0, 0, 0.75);" +
                "-fx-background-radius: 20;" +
                "-fx-border-color: #FFD700;" + // Dorado
                "-fx-border-width: 3;" +
                "-fx-border-radius: 20;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);";
        panelFormulario.setStyle(estiloPanel);

        // TÍTULO DEL PANEL
        Label tituloFormulario = new Label("Registro de Jugadores");
        tituloFormulario.setFont(Font.font(Estilos.FUENTE, FontWeight.BOLD, 22));
        tituloFormulario.setTextFill(Color.WHITE);
        tituloFormulario.setPadding(new Insets(0, 0, 15, 0)); // Separación abajo
        panelFormulario.getChildren().add(tituloFormulario);

        // GENERAR RENGLONES DE  JUGADORES
        for (int i = 0; i < cantidadJugadores; i++) {
            HBox renglon = crearRenglonJugador(i + 1);
            panelFormulario.getChildren().add(renglon);
        }

        contenedorPrincipal.getChildren().add(panelFormulario);
        return contenedorPrincipal;
    }

    private HBox crearRenglonJugador(int numeroJugador) {
        HBox fila = new HBox(15);
        fila.setAlignment(Pos.CENTER);
        fila.setPadding(new Insets(5));

        //  Etiqueta "Jugador X" (Dorado)
        Label lblNombre = new Label("Jugador " + numeroJugador);
        lblNombre.setFont(Font.font(Estilos.FUENTE, FontWeight.BOLD, 20));
        lblNombre.setTextFill(Color.web("#FFD700"));
        lblNombre.setPrefWidth(120);

        // Input Nombre (Estilo Pergamino)
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre...");
        txtNombre.setFont(Font.font(Estilos.FUENTE, 18));
        txtNombre.setPrefWidth(280);
        // CSS: Color crema + Borde marrón cuero
        txtNombre.setStyle("-fx-background-color: #fdf5e6; -fx-border-color: #8B4513; -fx-background-radius: 5; -fx-border-radius: 5;");

        nombresIngresados.add(txtNombre);

        //Etiqueta Color (Blanco)
        Label lblColor = new Label("Color:");
        lblColor.setFont(Font.font(Estilos.FUENTE, FontWeight.BOLD, 18));
        lblColor.setTextFill(Color.WHITE);

        // Selector de Color
        ColorPicker colorPicker = new ColorPicker();
        //colores por defecto para ayudar al usuario
        Color colorDefecto = obtenerColorPorDefecto(numeroJugador);
        colorPicker.setValue(colorDefecto);

        // Estilo del picker para que combine
        colorPicker.setStyle("-fx-background-color: #fdf5e6; -fx-border-color: #8B4513; -fx-border-radius: 5;");
        colorPicker.setPrefWidth(50);
        colorPicker.setPrefHeight(35);

        coloresElegidos.add(colorPicker);

        fila.getChildren().addAll(lblNombre, txtNombre, lblColor, colorPicker);
        return fila;
    }

    private VBox crearBotonConfirmado(Stage stage, PantallaPrincipal pantallaPrincipal) {
        VBox botonConfirmado = new VBox();
        botonConfirmado.setAlignment(Pos.CENTER);

        BotonGenerico btn = new BotonGenerico(new ControladorCrearPartida(stage, pantallaPrincipal, nombresIngresados, coloresElegidos), "COMENZAR");
        botonConfirmado.getChildren().add(btn);


        return botonConfirmado;
    }

    //dar colores variados por defecto
    private Color obtenerColorPorDefecto(int indice) {
        Color[] colores = {Color.RED, Color.BLUE, Color.ORANGE, Color.WHITE, Color.GREEN, Color.PURPLE};
        return colores[(indice - 1) % colores.length];
    }
}
