package edu.fiuba.algo3.modelo.interfaces;

import edu.fiuba.algo3.modelo.Jugador;

public interface IVertice {
//    List<ICelda> obtenerCeldasAdyacentes();

//    List<Recurso> darRecursos();

    boolean tieneConstruccionAdyacente();

    boolean tieneConstruccion();

    void colocarPoblado(Jugador jugador);

}
