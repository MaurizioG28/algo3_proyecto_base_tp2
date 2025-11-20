package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;

import java.util.Map;
import java.util.List;

public class Jugador {
    int MADERA = 0;
    int LADRILLO = 0;
    int LANA= 0;
    int GRANO = 0;
    int MINERAL= 0;

    // maderaReq, ladrilloReq, lanaReq, cerealReq, mineralReq
    public boolean tiene(int madera, int ladrillo, int lana, int grano, int mineral) {
        return almacenJugador.tiene(madera, ladrillo, lana, grano, mineral);
    }

    public void sumarRecursos(List<Recurso> recursos) {
        for (Recurso recurso : recursos) {
            switch (recurso) {
                case MADERA:
                    MADERA++;
                    break;
                case LADRILLO:
                    LADRILLO++;
                    break;
                case LANA:
                    LANA++;
                    break;
                case GRANO:
                    GRANO++;
                    break;
                case MINERAL:
                    MINERAL++;
                    break;
            }
        }
    }

    private AlmacenDeRecursos almacenJugador;
    private MazoDeCartas mazoCartas;

    public Jugador(){
        this.almacenJugador = new AlmacenDeRecursos();
        this.mazoCartas = new MazoDeCartas();
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

    public void removerRecurso(Recurso recurso, int cantidadRecurso){
        this.almacenJugador.removerRecurso(recurso, cantidadRecurso);
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

    public void agregarCarta(CartaDesarrollo cartaNueva) {
        mazoCartas.agregarCarta(cartaNueva);
    }

    public CartaDesarrollo agarrarCarta(int indice) {
        return mazoCartas.agarrarCarta(indice);
    }
}
