package edu.fiuba.algo3.vistas.vistas;


import edu.fiuba.algo3.vistas.Estilos;
import edu.fiuba.algo3.vistas.mensajes.MensajeAyuda;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class VistaAyuda extends StackPane {

    public VistaAyuda() {
        BackgroundFill fondoColor = new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY);
        setBackground(new Background(fondoColor));
        VBox cajaPrincipal = new VBox(100);
        cajaPrincipal.setPadding(new Insets(30));
        cajaPrincipal.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("Ayuda sobre el juego");
        titulo.setFont(Font.font(Estilos.FUENTE, 25));
        titulo.setStyle("-fx-font-weight: bold");
        titulo.setUnderline(true);
        titulo.setTextFill(Color.WHITE);
        titulo.setWrapText(true);
        cajaPrincipal.getChildren().add(titulo);
        cajaPrincipal.getChildren().add(new MensajeAyuda());
        cajaPrincipal.setAlignment(Pos.TOP_CENTER);
        cajaPrincipal.setSpacing(7);

        super.getChildren().add(cajaPrincipal);
    }
}
