package edu.fiuba.algo3.vistas.vistas;


import edu.fiuba.algo3.Estilos;
import edu.fiuba.algo3.vistas.mensajes.MensajeAyuda;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class VistaAyuda extends StackPane {

    public VistaAyuda() {
        // ----- FONDO AZUL OSCURO -----
        setBackground(new Background(
                new BackgroundFill(Color.web("#233850"), CornerRadii.EMPTY, Insets.EMPTY)
        ));

        // Contenedor que se va a scrollear
        VBox rootScroll = new VBox();
        rootScroll.setPadding(new Insets(40));
        rootScroll.setAlignment(Pos.TOP_CENTER);
        rootScroll.setSpacing(20);
        // IMPORTANTE: que no pinte fondo
        rootScroll.setBackground(Background.EMPTY);

        // ----- TARJETA BLANCA CENTRADA -----
        VBox tarjeta = new VBox(15);
        tarjeta.setPadding(new Insets(25));
        tarjeta.setMaxWidth(700);
        tarjeta.setAlignment(Pos.TOP_LEFT);
        tarjeta.setBackground(new Background(
                new BackgroundFill(
                        Color.rgb(255, 255, 255, 0.95),
                        new CornerRadii(20),
                        Insets.EMPTY
                )
        ));
        tarjeta.setBorder(new Border(new BorderStroke(
                Color.rgb(255, 255, 255, 0.4),
                BorderStrokeStyle.SOLID,
                new CornerRadii(20),
                new BorderWidths(2)
        )));

        // ----- TÍTULO -----
        Label titulo = new Label("Ayuda sobre el juego");
        titulo.setFont(Font.font(Estilos.FUENTE, 28));
        titulo.setStyle("-fx-font-weight: bold;");
        titulo.setTextFill(Color.web("#233850"));
        titulo.setWrapText(true);
        titulo.setAlignment(Pos.CENTER);

        VBox contTitulo = new VBox(titulo);
        contTitulo.setAlignment(Pos.CENTER);

        // ----- CUERPO (MensajeAyuda) -----
        MensajeAyuda mensaje = new MensajeAyuda();

        tarjeta.getChildren().addAll(contTitulo, mensaje);
        rootScroll.getChildren().add(tarjeta);

        // ----- SCROLLPANE TRANSPARENTE -----
        ScrollPane scroll = new ScrollPane(rootScroll);
        scroll.setFitToWidth(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // que no tape el fondo azul
        scroll.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-background: transparent; " +
                        "-fx-border-color: transparent;"
        );
        scroll.setPannable(true);

        getChildren().add(scroll);
    }
}