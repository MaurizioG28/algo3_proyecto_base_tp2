package edu.fiuba.algo3.vistas.vistas;


import edu.fiuba.algo3.Estilos;
import edu.fiuba.algo3.vistas.mensajes.MensajeAyuda;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class VistaAyuda extends StackPane {

    public VistaAyuda() {
        Image imagen = new Image("file:" + System.getProperty("user.dir") + "/src/main/java/edu/fiuba/algo3/resources/Imagenes/catan-ayuda.png");
        BackgroundImage fondoImagen = new BackgroundImage(imagen,
                BackgroundRepeat.ROUND,
                BackgroundRepeat.SPACE,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));

        Background fondo = new Background(fondoImagen);
        super.setBackground(fondo);

        VBox cajaPrincipal = new VBox(100);
        cajaPrincipal.setPadding(new Insets(30));
        cajaPrincipal.setAlignment(Pos.CENTER);

        Label titulo = new Label("Ayuda sobre el juego");
        titulo.setFont(Font.font(Estilos.FUENTE, 25));
        titulo.setStyle("-fx-font-weight: bold");
        titulo.setUnderline(true);
        titulo.setTextFill(Color.BLACK);
        titulo.setWrapText(true);

        cajaPrincipal.getChildren().add(titulo);
        cajaPrincipal.getChildren().add(new MensajeAyuda());
        cajaPrincipal.setAlignment(Pos.CENTER);
        cajaPrincipal.setSpacing(7);

        super.getChildren().add(cajaPrincipal);
    }
}
