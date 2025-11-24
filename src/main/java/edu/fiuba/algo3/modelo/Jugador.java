package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Cartas.PuntoDeVictoria;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Intercambios.PoliticaDeIntercambio;
import edu.fiuba.algo3.modelo.Recursos.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class Jugador {

    private MazoDeCartas cartas;
    private AlmacenDeRecursos almacenJugador;
    private final List<PoliticaDeIntercambio> politicas = new ArrayList<>();
    private  Color color;

    public Jugador(){
        this.almacenJugador = new AlmacenDeRecursos();
        this.cartas = new MazoDeCartas();
        this.color = null;
    }
    public Jugador(String nombre, Color color){
        this();
        this.color = color;
    }
    public Color getColor(){
        return color;
    }
    public int CantidadRecurso(TipoDeRecurso tipo) {
        return almacenJugador.cantidadDe(tipo);
    }

    public boolean tiene(Madera madera, Ladrillo ladrillos, Lana lana, Mineral mineral, Grano grano) {
        return (
                (madera.obtenerCantidad() >= CantidadRecurso(madera)) &
                        (ladrillos.obtenerCantidad() >= CantidadRecurso(ladrillos)) &
                        (lana.obtenerCantidad() >= CantidadRecurso(lana)) &
                        (mineral.obtenerCantidad() >= CantidadRecurso(mineral)) &
                        (grano.obtenerCantidad() >= CantidadRecurso(grano))
        );
    }

    public void agregarRecurso(TipoDeRecurso recurso) {
        almacenJugador.agregarRecurso(recurso);
    }
    public void agregarCarta(CartaDesarrollo cartaNueva) {
        cartas.agregarCarta(cartaNueva);
    }

    public CartaDesarrollo agarrarCarta(int indice) {
        return cartas.agarrarCarta(indice);
    }

    public void quitarRecurso(TipoDeRecurso tipo, int cantidad) {
        if (!almacenJugador.quitar(tipo, cantidad)) {
            throw new IllegalStateException("El jugador no tiene suficientes " + tipo.nombre());
        }
    }

    public int mejorTasaPara(TipoDeRecurso recursoEntregado) {
        return politicas.stream()
                .filter(p -> p.aplicaA(this, recursoEntregado))
                .mapToInt(PoliticaDeIntercambio::tasa)
                .min()
                .orElse(4); // base 4:1
    }
    public void agregarPolitica(PoliticaDeIntercambio politica) {
        politicas.add(politica);
    }

    public Map<TipoDeRecurso, Integer> descartarMitadDeRecursos() {
        return this.almacenJugador.descartarPorReglaDelSiete();
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
            this.almacenJugador.agregarRecurso(recursoRobado);
        }
    }

    public int totalPuntos() {
        // Falta agregar mas implementaciones

        return cartas.cantidadDeTipo(PuntoDeVictoria.class);
    }

    public void intercambiar(TipoDeRecurso recursoEntregar, int cantidadEntregar, Jugador jugador2, TipoDeRecurso recursoRecibir, int cantidadRecibir) throws RecursosIsuficientesException {
        if(!jugador2.cambiar(recursoRecibir, cantidadRecibir, recursoEntregar, cantidadEntregar)){
            throw new RecursosIsuficientesException("El segundo jugador no tiene suficientes recursos.");
        }
        if(!this.cambiar(recursoEntregar, cantidadEntregar, recursoRecibir, cantidadRecibir)){
            jugador2.cambiar(recursoEntregar, cantidadEntregar, recursoRecibir, cantidadRecibir);
            throw new RecursosIsuficientesException("El primer jugador no tiene suficientes recursos.");
        };
    }

    public boolean cambiar(TipoDeRecurso recursoEntregar, int cantidadEntregar, TipoDeRecurso recursoRecibir, int cantidadRecibir) {
        if(!this.almacenJugador.quitar(recursoEntregar,cantidadEntregar)) {
            return false;
        }
        this.almacenJugador.agregarRecurso(recursoRecibir.nuevo(cantidadRecibir));
        return true;
    }
    public boolean tiene(int madera, int ladrillo, int lana, int grano, int mineral) {

        Map<TipoDeRecurso, Integer> requeridos = Map.of(
                new Madera(0), madera,
                new Ladrillo(0), ladrillo,
                new Lana(0), lana,
                new Grano(0), grano,
                new Mineral(0), mineral
        );
        return almacenJugador.tiene(requeridos);
    }
    public void puedeCosntruirPoblado(){
        this.tiene(1,1,1,1,0);
        almacenJugador.quitar(new Madera(0),1);
        almacenJugador.quitar(new Grano(0),1);
        almacenJugador.quitar(new Ladrillo(0),1);
        almacenJugador.quitar(new Lana(0),1);
    }

    public boolean tieneExactamente(int madera, int ladrillo, int lana, int grano, int mineral) {
        Map<TipoDeRecurso, Integer> requeridos = Map.of(
                new Madera(0), madera,
                new Ladrillo(0), ladrillo,
                new Lana(0), lana,
                new Grano(0), grano,
                new Mineral(0), mineral
        );
        return almacenJugador.tieneExactamente(requeridos);
    }

    public void agregarRecursos(int madera, int grano, int ladrillo, int lana, int mineral) {

        Map<TipoDeRecurso, Integer> entrada = Map.of(
                new Madera(0), madera,
                new Grano(0), grano,
                new Ladrillo(0), ladrillo,
                new Lana(0), lana,
                new Mineral(0), mineral
        );

        for (Map.Entry<TipoDeRecurso, Integer> e : entrada.entrySet()) {
            int cantidad = e.getValue();
            if (cantidad > 0) {
                agregarRecurso(e.getKey().nuevo(cantidad));
            }
        }
    }
}
