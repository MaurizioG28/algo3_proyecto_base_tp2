package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Intercambios.PoliticaDeIntercambio;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class Jugador {

    private AlmacenDeRecursos almacenJugador;
    private final List<PoliticaDeIntercambio> politicas = new ArrayList<>();
    private static final int TASA_BASE_BANCO = 4;

    public Jugador(){
        this.almacenJugador = new AlmacenDeRecursos();
    }
    public int CantidadRecurso(TipoDeRecurso recurso) {
        return this.almacenJugador.cantidadDe(recurso);
    }

    public Map<TipoDeRecurso, Integer> descartarMitadDeRecursos() {
        return this.almacenJugador.descartarPorReglaDelSiete();
    }

    public void agregarRecurso(TipoDeRecurso recurso, int cantidadRecurso){
        this.almacenJugador.agregarRecurso(recurso,cantidadRecurso);
    }

    public void quitarRecurso(TipoDeRecurso recurso, int cantidad) {
        boolean ok = this.almacenJugador.quitar(recurso, cantidad);
        if (!ok) {
            throw new IllegalArgumentException("El jugador no tiene suficientes " + recurso);
        }
    }

//    Después, cuando el jugador construye en un vértice con puerto, el Tablero hace:
//    jugador.agregarPolitica(new PuertoGenerico(3));
//// o:
//jugador.agregarPolitica(new PuertoEspecifico(TipoDeRecurso.MADERA, 2));
    public void agregarPolitica(PoliticaDeIntercambio politica) {
        politicas.add(politica);
    }


    public int totalRecursos() {
        return this.almacenJugador.totalRecursos();
    }

    private TipoDeRecurso entregarRecursoAleatorio() {
        return this.almacenJugador.robarRecursoAleatorio();
    }

    public void robarRecurso(Jugador victima) {
        TipoDeRecurso recursoRobado = victima.entregarRecursoAleatorio();
        if(recursoRobado != null){
            this.almacenJugador.agregarRecurso(recursoRobado,1);
        }
    }

    public void sumarRecursos(List<TipoDeRecurso> recursos) {
        if (recursos == null) return;
        for (TipoDeRecurso r : recursos) {
            this.agregarRecurso(r, 1);
        }
    }

    public int mejorTasaPara(TipoDeRecurso recursoEntregado) {
        int mejor = TASA_BASE_BANCO;

        for (PoliticaDeIntercambio p : politicas) {
            if (p.aplicaA(this, recursoEntregado)) {
                mejor = Math.min(mejor, p.tasa());
            }
        }
        return mejor;
    }
}
