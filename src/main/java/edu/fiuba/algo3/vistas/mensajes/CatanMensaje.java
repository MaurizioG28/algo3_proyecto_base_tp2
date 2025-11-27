package edu.fiuba.algo3.vistas.mensajes;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CatanMensaje extends Label {

    public CatanMensaje() {
        super("CATAN");

        // 1. FUENTE: Usamos una fuente más gruesa y grande (Arial Black es segura, o carga una custom)
        super.setFont(Font.font("Arial Black", FontWeight.BOLD, 100));

        // 2. RELLENO: Creamos un degradado dorado/piedra (De arriba a abajo)
        Stop[] stops = new Stop[] {
                new Stop(0, Color.web("#f1c40f")),  // Oro claro arriba
                new Stop(1, Color.web("#b7950b"))   // Oro oscuro abajo
        };
        LinearGradient degradado = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        super.setTextFill(degradado);

        // 3. BORDE: Un borde negro grueso alrededor de las letras
        // Nota: Label no tiene setStroke directo, pero podemos simularlo con CSS o usar Text.
        // Para simplificar manteniendo Label, usaremos un estilo CSS inline fuerte:
        super.setStyle("-fx-stroke: black; -fx-stroke-width: 2px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0.5, 0, 5);");

        // OPCIÓN B (Más potente): Usar DropShadow para simular profundidad 3D
        DropShadow sombra = new DropShadow();
        sombra.setColor(Color.BLACK);
        sombra.setRadius(5);
        sombra.setOffsetX(5);
        sombra.setOffsetY(5);
        super.setEffect(sombra);


    }

    public CatanMensaje(String colorIgnorado) {
        this();
    }
}