package edu.fiuba.algo3.vistas.botones;

import edu.fiuba.algo3.Estilos;
import edu.fiuba.algo3.controllers.ReproductorEfectos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Cursor;

public class BotonGenerico extends Button {

    private ReproductorEfectos sfx = new ReproductorEfectos();

    public BotonGenerico(EventHandler<ActionEvent> controlador,String mensaje) {
        super.setText(mensaje);

        super.setFont(Font.font(Estilos.FUENTE, FontWeight.BOLD, 24));
        super.setTextFill(Color.WHITE); // Texto blanco

        // ESTILO CSS "INLINE" PARA EL BOTÓN
        // - Fondo: Gradiente violeta/oscuro
        // - Borde: Dorado (#FFD700)
        // - Radio: Bordes redondeados (30px)
        String estiloNormal = "-fx-background-color: linear-gradient(to bottom, #6a1b9a, #4a148c);" +
                "-fx-background-radius: 30;" +
                "-fx-border-color: #FFD700;" + // Borde dorado
                "-fx-border-width: 3;" +
                "-fx-border-radius: 30;" +
                "-fx-padding: 10 40 10 40;"; // Más ancho

        super.setStyle(estiloNormal);

        // Efecto de sombra para que parezca flotando
        DropShadow sombra = new DropShadow();
        sombra.setColor(Color.BLACK);
        sombra.setRadius(10);
        sombra.setOffsetY(5);
        super.setEffect(sombra);
        super.setCursor(Cursor.HAND);

        super.setOnAction(e -> {
            sfx.reproducirClick();
            controlador.handle(e);
        });

        // Ajustamos los controladores de hover para que cambien el estilo (no solo el color)

        super.setOnMouseEntered(e -> {
            sfx.reproducirHover();
            super.setStyle("-fx-background-color: linear-gradient(to bottom, #8e24aa, #6a1b9a);" + // Más claro al pasar mouse
                    "-fx-background-radius: 30;" +
                    "-fx-border-color: #FFF;" + // Borde blanco al pasar mouse
                    "-fx-border-width: 3;" +
                    "-fx-border-radius: 30;" +
                    "-fx-padding: 10 40 10 40;");
            super.setCursor(Cursor.HAND);
        });

        super.setOnMouseExited(e -> {
            super.setStyle(estiloNormal);
        });
    }
}
