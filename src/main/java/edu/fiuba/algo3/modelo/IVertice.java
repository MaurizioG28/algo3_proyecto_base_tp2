package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Jugador;
import java.util.List;

public interface IVertice {
    boolean validarConstruccion(Jugador jugador);
    List<ICelda> obtenerCeldasAdyacentes();

    List<Recurso> darRecursos();
}
