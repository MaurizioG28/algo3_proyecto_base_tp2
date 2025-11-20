package edu.fiuba.algo3.modelo.interfaces;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;

import java.util.List;

public interface IVertice {
    List<ICelda> obtenerCeldasAdyacentes();

    List<Recurso> darRecursos();

    boolean tieneConstruccionAdyacente();

    boolean tieneConstruccion();

    void colocarPoblado(Jugador jugador);

}
