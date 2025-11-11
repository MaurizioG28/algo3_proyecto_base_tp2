package edu.fiuba.algo3.modelo;

import java.util.Map;

public class Jugador {
    private AlmacenDeRecursos almacenJugador;

    public Jugador(){
        this.almacenJugador = new AlmacenDeRecursos();
    }
    public int CantidadRecurso(Recurso recurso) {
        return this.almacenJugador.cantidadDe(recurso);
    }

    public Map<Recurso, Integer> descartarMitadDeRecursos() {
        return this.almacenJugador.descartarPorReglaDelSiete();
    }

    public void agregarRecurso(Recurso recurso, int cantidadRecurso){
        this.almacenJugador.agregarRecurso(recurso,cantidadRecurso);
    }

    public int totalRecursos() {
        return this.almacenJugador.totalRecursos();
    }
}
