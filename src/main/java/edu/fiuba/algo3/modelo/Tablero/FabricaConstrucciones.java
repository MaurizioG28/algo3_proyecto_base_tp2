package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;

import java.util.Map;

public class FabricaConstrucciones {

    public Costo costoDeCamino() {
        return new Costo(Map.of(
                Recurso.MADERA, 1,
                Recurso.LADRILLO, 1
        ));
    }

    public Camino crearCamino(Jugador jugador) {
        return new Camino(jugador);
    }
}
