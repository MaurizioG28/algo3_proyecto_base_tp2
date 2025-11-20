package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Tablero.Costo;

import java.util.Map;
import java.util.List;

public class Jugador {
    // maderaReq, ladrilloReq, lanaReq, cerealReq, mineralReq
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

    private Recurso entregarRecursoAleatorio() {
        return this.almacenJugador.robarRecursoAleatorio();
    }

    public void robarRecurso(Jugador victima) {
        Recurso recursoRobado = victima.entregarRecursoAleatorio();
        if(recursoRobado != null){
            this.almacenJugador.agregarRecurso(recursoRobado,1);
        }
    }
    public void pagar(Costo costo){
        almacenJugador.pagar(costo);
    }
    public void sumarRecursos(List<Recurso> recursos) {
        almacenJugador.sumarRecursos(recursos);
    }
    public boolean tiene(int madera, int ladrillo, int lana, int grano, int mineral){
        return almacenJugador.tiene(madera, ladrillo, lana, grano, mineral);

    }
}
