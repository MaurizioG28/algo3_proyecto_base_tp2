package edu.fiuba.algo3.modelo.interfaces;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.Costo;

public interface IFabricaConstrucciones {
    Costo costoDeCamino();
    ICamino crearCamino(Jugador jugador);
    Costo costoDePoblado();
}
