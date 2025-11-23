package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Cartas.PuntoDeVictoria;
import edu.fiuba.algo3.modelo.Intercambios.PoliticaDeIntercambio;
import edu.fiuba.algo3.modelo.Recursos.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class Jugador {

    private MazoDeCartas cartas;
    private AlmacenDeRecursos almacenJugador;
    private final List<PoliticaDeIntercambio> politicas = new ArrayList<>();

    public Jugador(){
        this.almacenJugador = new AlmacenDeRecursos();
        this.cartas = new MazoDeCartas();
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

}
