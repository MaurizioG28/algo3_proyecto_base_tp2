package edu.fiuba.algo3.vistas.vistas;

import edu.fiuba.algo3.controllers.ControladorCantidadJugadores;
import edu.fiuba.algo3.controllers.ControladorFinalizarJuego;
import edu.fiuba.algo3.controllers.ControladorReiniciarJuego;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.vistas.Grillas.GrillaBase;
import edu.fiuba.algo3.vistas.PantallaPrincipal;
import edu.fiuba.algo3.vistas.botones.BotonGenerico;
import edu.fiuba.algo3.vistas.mensajes.CatanMensaje;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;



public class VistaGanador extends StackPane {

    public VistaGanador(Stage stage, PantallaPrincipal pantallaPrincipal, Jugador ganador) {
        // Fondo Oscuro
        this.setBackground(new Background(new BackgroundFill(Color.web("#233850"), null, null)));

        VBox contenido = new VBox(30);
        contenido.setAlignment(Pos.CENTER);

        // Título
        Label lblTitulo = new Label("¡TENEMOS UN GANADOR!");
        lblTitulo.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        lblTitulo.setTextFill(Color.GOLD);

        // Nombre del Ganador
        Label lblGanador = new Label(ganador.getNombre().toUpperCase());
        lblGanador.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
        lblGanador.setTextFill(Color.WHITE);
        lblGanador.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");

        // Detalles
        Label lblPuntos = new Label("Total Puntos de Victoria: " + ganador.totalPuntos());
        lblPuntos.setFont(Font.font("Verdana", 20));
        lblPuntos.setTextFill(Color.LIGHTGRAY);

        // Botones
        ControladorReiniciarJuego reiniciar = new ControladorReiniciarJuego(stage, pantallaPrincipal);
        BotonGenerico btnReiniciar = new BotonGenerico(reiniciar, "Jugar de Nuevo");

        ControladorFinalizarJuego salir = new ControladorFinalizarJuego();
        BotonGenerico btnSalir = new BotonGenerico(salir, "Salir del Juego");

        HBox botones = new HBox(20, btnReiniciar, btnSalir);
        botones.setAlignment(Pos.CENTER);

        contenido.getChildren().addAll(lblTitulo, lblGanador, lblPuntos, botones);
        this.getChildren().add(contenido);
    }
}