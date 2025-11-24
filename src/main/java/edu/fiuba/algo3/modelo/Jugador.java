package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Cartas.PuntoDeVictoria;
import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Recursos.*;
//import edu.fiuba.algo3.modelo.Tablero.Costo;

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
    public int cantidadRecurso(TipoDeRecurso tipo) {
        return this.almacenJugador.cantidadDe(tipo);
    }

    public Map<TipoDeRecurso, Integer> descartarMitadDeRecursos() {
        return this.almacenJugador.descartarPorReglaDelSiete();
    }

    public void agregarRecurso(TipoDeRecurso recurso){
        this.almacenJugador.agregarRecurso(recurso);
    }

    public void quitarRecurso(TipoDeRecurso tipo, int cantidad) {
        if (!almacenJugador.quitar(tipo, cantidad)) {
            throw new IllegalStateException("El jugador no tiene suficientes " + tipo.nombre());
        }
    }

    public int totalRecursos() {
        return this.almacenJugador.totalRecursos();
    }

//    private Recurso entregarRecursoAleatorio() {
//        return this.almacenJugador.robarRecursoAleatorio();
//    }

//    public void robarRecurso(Jugador victima) {
//        Recurso recursoRobado = victima.entregarRecursoAleatorio();
//        if(recursoRobado != null){
//            this.almacenJugador.agregarRecurso(recursoRobado,1);
//        }
//    }

    public void agregarCarta(CartaDesarrollo cartaNueva) {
        mazoCartas.agregarCarta(cartaNueva);
    }

    public CartaDesarrollo agarrarCarta(int indice) {
        return mazoCartas.agarrarCarta(indice);
    }
//    public Construccion comprarPoblado() {
//        return almacenJugador.comprarPoblado(color);
//    }

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

    public boolean tiene(Madera madera, Ladrillo ladrillos, Lana lana, Mineral mineral, Grano grano) {
        return (
                (madera.obtenerCantidad() >= cantidadRecurso(madera)) &
                        (ladrillos.obtenerCantidad() >= cantidadRecurso(ladrillos)) &
                        (lana.obtenerCantidad() >= cantidadRecurso(lana)) &
                        (mineral.obtenerCantidad() >= cantidadRecurso(mineral)) &
                        (grano.obtenerCantidad() >= cantidadRecurso(grano))
        );
    }

    public int totalPuntos() {
        return mazoCartas.cantidadDeTipo(PuntoDeVictoria.class);

        //Implementaciones requeridas para cosntrucciones

    }

    public void sumarRecursos(List<Recurso> recursos) {
        almacenJugador.sumarRecursos(recursos);
    }

    public void pagar(Costo costo) {almacenJugador.pagar(costo);};

}
