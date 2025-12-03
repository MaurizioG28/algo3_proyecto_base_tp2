package edu.fiuba.algo3.vistas.botones;

import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.Cubic;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BotonVertice extends Button {

    private final Coordenada coordenada;   // (hex, indice)
    private final Cubic cubicCoord;        // opcional, útil para debug

    public BotonVertice(double x, double y, Cubic cubicCoord, int terrenoId, int indiceVertice) {
        this.cubicCoord = cubicCoord;
        this.coordenada = new Coordenada(terrenoId, indiceVertice);

        setLayoutX(x - 7); // centrar el botón
        setLayoutY(y - 7);
        setPrefSize(15, 15);

        setStyle("-fx-background-radius: 100%; -fx-background-color: black;-fx-opacity: 1.0;");
        //setDisable(true);


        // Esto permite que el Controller lo conecte después
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public Cubic getCubicCoord() {
        return cubicCoord;
    }
}
