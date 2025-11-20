package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.interfaces.*;

import java.util.Map;

public class FabricaConstrucciones implements IFabricaConstrucciones {

    @Override
    public Costo costoDeCamino() {
        return new Costo(Map.of(
                Recurso.MADERA, 1,
                Recurso.LADRILLO, 1
        ));
    }

    @Override

    public ICamino crearCamino(Jugador jugador) {
        return new Camino(jugador);
    }



    @Override
    public Costo costoDePoblado() {
        return null;
    }
}

