package edu.fiuba.algo3.vistas.mensajes;


import edu.fiuba.algo3.vistas.Estilos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MensajeAyuda extends Label {
    public MensajeAyuda() {
        super("");
        super.setFont(Font.font(Estilos.FUENTE, 18));
        super.setTextFill(Color.YELLOW);
        super.setWrapText(true);
    }
}

