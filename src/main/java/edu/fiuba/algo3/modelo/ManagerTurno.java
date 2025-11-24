package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Tablero.Factory.Hexagono;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.MazoOculto;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class ManagerTurno {
    private final List<Jugador> jugadores;
    private int indiceJugadorActual = 0;
    private int numeroTurnoActual = 0;
    private final Tablero tablero;
    private final Random azar;
    private final MazoOculto mazoOculto;

    public ManagerTurno(List<Jugador> jugadores, Tablero tablero, Random Random, MazoOculto mazoOculto) {
        this.jugadores = jugadores;
        this.tablero = tablero;
        this.azar=  Random;
        this.mazoOculto = mazoOculto;
    }

    public void comprarCarta() {
        Jugador jugador = getJugadorActual();
        jugador.agregarCarta(mazoOculto.comprarCarta(getJugadorActual(), numeroTurnoActual));
    }

    public void usarUnaCarta(int indice) {
        CartaDesarrollo cartaSeleccionada = getJugadorActual().agarrarCarta(indice);

        if (!cartaSeleccionada.SePuedeUsar(numeroTurnoActual)) {
            throw new ReglaDeCompraYUsoException("La carta no puede ser usada el mismo turno en el que se compra.");
        }

        // Utilidad de las cartas
    }

    private Jugador getJugadorActual() {
        return jugadores.get(indiceJugadorActual);
    }

    public void siguienteTurno() {

        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
        numeroTurnoActual += 1;
    }

    public void construirPoblado(IVertice vertice){
        Jugador jugadorActual = getJugadorActual();
        try {
            tablero.construirPoblado(jugadorActual, vertice);
        } catch (ReglaDistanciaException e) {
            System.out.println( e.getMessage());
        }
    }

    public void moverLadron(Integer posicion){
        Jugador jugadorActual = getJugadorActual();
        List<Color> coloresDeVictimas= tablero.moverLadron(jugadorActual, posicion);
        List<Jugador> victimas =
                coloresDeVictimas.stream()
                        .map(this::getJugadorPorColor)
                        .collect(Collectors.toList());

        if(!victimas.isEmpty()){

            Jugador victima = victimas.get(azar.nextInt(victimas.size()));
            //Selecciona una victima al azar por ahora, depues vemos como hacer para que el jugador elija desde la interfaz
            jugadorActual.robarRecurso(victima);
        }

    }

    private Jugador getJugadorPorColor(Color color) {
        return jugadores.stream()
                .filter(jugador -> jugador.obtenerColor().equals(color) )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un jugador con el color especificado: " + color));
    }

}
