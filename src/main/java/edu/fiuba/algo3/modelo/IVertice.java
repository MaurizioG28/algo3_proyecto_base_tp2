package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Jugador;
import java.util.List;

public interface IVertice {
    List<ICelda> obtenerCeldasAdyacentes();

    List<Recurso> darRecursos();

    boolean tieneConstruccionAdyacente();

    boolean tieneConstruccion();

    void colocarPoblado(Jugador jugador);

}
