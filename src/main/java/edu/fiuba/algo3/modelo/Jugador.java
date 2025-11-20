package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Recursos.RecursosIsuficientesException;
import edu.fiuba.algo3.modelo.Tablero.Costo;

import java.util.Map;
import java.util.List;

public class Jugador {
    private Color color;

    private AlmacenDeRecursos almacenJugador;
    private MazoDeCartas mazoCartas;

    public Jugador(){
        this.almacenJugador = new AlmacenDeRecursos();
        this.mazoCartas = new MazoDeCartas();
    }
    public Jugador(Color color){
        this.almacenJugador = new AlmacenDeRecursos();
        this.color = color;
    }
    public int cantidadRecurso(Recurso recurso) {
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
    public Construccion comprarPoblado() {
        return almacenJugador.comprarPoblado(color);
    }

    public boolean tieneColor(String color) {
        return this.color.esMismoColor(color);
    }

    public void intercambiar(Recurso recursoEntregar, int cantidadEntregar, Jugador jugador2, Recurso recursoRecibir, int cantidadRecibir) throws RecursosIsuficientesException {
        if(!jugador2.cambiar(recursoRecibir, cantidadRecibir, recursoEntregar, cantidadEntregar)){
            throw new RecursosIsuficientesException("El segundo jugador no tiene suficientes recursos.");
        }
        if(!this.cambiar(recursoEntregar, cantidadEntregar, recursoRecibir, cantidadRecibir)){
            jugador2.cambiar(recursoEntregar, cantidadEntregar, recursoRecibir, cantidadRecibir);
            throw new RecursosIsuficientesException("El primer jugador no tiene suficientes recursos.");
        };
    }

    public boolean cambiar(Recurso recursoEntregar, int cantidadEntregar, Recurso recursoRecibir, int cantidadRecibir) {
        if(!this.almacenJugador.quitar(recursoEntregar,cantidadEntregar)) {
            return false;
        }
        this.almacenJugador.agregarRecurso(recursoRecibir, cantidadRecibir);
        return true;
    }
    public boolean tiene(int madera, int ladrillo, int lana, int grano, int mineral) {

        Map<Recurso, Integer> requeridos = Map.of(
                Recurso.MADERA, madera,
                Recurso.LADRILLO, ladrillo,
                Recurso.LANA, lana,
                Recurso.GRANO, grano,
                Recurso.MINERAL, mineral
        );

        return almacenJugador.tiene(requeridos);
    }

    public void sumarRecursos(List<Recurso> recursos) {
        almacenJugador.sumarRecursos(recursos);
    }

    public void pagar(Costo costo) {almacenJugador.pagar(costo);};
}
