package edu.fiuba.algo3.vistas.mensajes;


import edu.fiuba.algo3.Estilos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

public class MensajeAyuda extends Label {

    public MensajeAyuda() {
        setText(
                "--- GUÍA DE REFERENCIA RÁPIDA ---\n" +
                        "\n" +
                        "OBJETIVO: 10 PUNTOS DE VICTORIA (PV)\n" +
                        "- Poblado = 1 PV | Ciudad = 2 PV | Carta PV = 1 PV\n" +
                        "- Gran Caballería (3+ Caballeros) = 2 PV\n" +
                        "- Gran Ruta (5+ Carreteras contiguas) = 2 PV\n" +
                        "\n" +
                        "COSTOS DE CONSTRUCCIÓN\n" +
                        "■ Carretera: 1 Madera + 1 Ladrillo\n" +
                        "■ Poblado: 1 Madera + 1 Ladrillo + 1 Lana + 1 Grano\n" +
                        "■ Ciudad: 2 Grano + 3 Mineral (Mejora un Poblado)\n" +
                        "■ Carta Desarrollo: 1 Lana + 1 Grano + 1 Mineral\n" +
                        "\n" +
                        "CARTAS DE DESARROLLO (EFECTOS)\n" +
                        "• Caballero: Mueve al Ladrón y roba 1 carta a un rival.\n" +
                        "• Monopolio: Pides un recurso; todos te dan TODOS los que tengan.\n" +
                        "• Descubrimiento: Tomas 2 recursos a elección del banco.\n" +
                        "• Const. Carreteras: Construyes 2 carreteras gratis.\n" +
                        "• Puntos Victoria: Suman 1 PV oculto (se revelan al final).\n" +
                        "\n" +
                        "EL LADRÓN (AL SACAR 7)\n" +
                        "1. Nadie produce recursos.\n" +
                        "2. Descarte: Si tienes >7 cartas, descartas la mitad.\n" +
                        "3. Robo: Mueves el Ladrón a otro hexágono (lo bloqueas) y\n" +
                        "   robas 1 carta al azar a un jugador con poblado allí.\n" +
                        "\n" +
                        "COMERCIO CON LA BANCA\n" +
                        "- Tasa Estándar: 4 iguales x 1 cualquiera.\n" +
                        "- Puerto Genérico (?): 3 iguales x 1 cualquiera.\n" +
                        "- Puerto Recurso: 2 del recurso pintado x 1 cualquiera.\n" +
                        "\n" +
                        "REGLAS DE COLOCACIÓN\n" +
                        "- Distancia: Un poblado debe estar al menos a un cruce de distancia\n" +
                        "  de cualquier otro (propio o ajeno).\n" +
                        "- Setup Inicial: 2 rondas. En la 2da (inversa) cobras recursos."
        );

        // Ajustes visuales
        setFont(Font.font(Estilos.FUENTE, 14));
        setTextFill(Color.web(Estilos.VIOLETA));
        setWrapText(true);
        setAlignment(Pos.CENTER_LEFT); // Alineado a la izquierda se lee mejor con tanto texto
        setTextAlignment(TextAlignment.LEFT);

        // Un poco de padding para que el texto no toque los bordes
        setStyle("-fx-padding: 15; -fx-background-color: rgba(255,255,255,0.9); -fx-background-radius: 10;");
    }
}