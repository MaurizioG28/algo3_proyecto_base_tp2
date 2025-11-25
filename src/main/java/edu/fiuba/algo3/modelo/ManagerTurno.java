package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Contruccion.Ciudad;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Intercambios.Banco;
import edu.fiuba.algo3.modelo.Intercambios.ServicioComercio;
import edu.fiuba.algo3.modelo.Recursos.RecursosIsuficientesException;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.Hexagono;
import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.MazoOculto;

import java.util.ArrayList;
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
    private ServicioComercio servicioComercio = new ServicioComercio(new Banco());

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

    public void repartirDividendos(int sumaDeDados) {
        List<List<Dividendo>> listaDividendosPorTerreno = this.tablero.distribuirProduccion(sumaDeDados);
        listaDividendosPorTerreno.forEach(listaDividendos -> {

            listaDividendos.forEach(dividendo -> {
                if(dividendo==null) {return;}
                Jugador jugador = getJugadorPorColor(dividendo.getColor());
                List<TipoDeRecurso> recursosPorDividendo = dividendo.getRecursos();
                recursosPorDividendo.forEach(jugador::agregarRecurso);

            });
        });
    }
    public void construirPoblado(Coordenada coordenada) {
        try {
            // 1. El servicio valida recursos, cobra al jugador y guarda en el Banco
            Poblado poblado = servicioComercio.venderPoblado(getJugadorActual());

            try {
                // 2. El tablero intenta colocarlo
                // (Requiere que hayas agregado obtenerVertice o modificado colocarEnVertice)
                Vertice v = tablero.obtenerVertice(coordenada);
                Jugador jugadorActual = getJugadorActual();
                tablero.construirPoblado(jugadorActual.getColor(), v); // Tu metodo existente

            } catch (ReglaDistanciaException e) {
                // 3. ¡Error! El lugar estaba ocupado o muy cerca. Devolvemos la plata.
                servicioComercio.reembolsarPoblado(getJugadorActual());
                throw e; // Avisamos a la vista
            }

        } catch (ReglaDistanciaException e) {
            System.out.println("No alcanza la plata: " + e.getMessage());
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
                .filter(jugador -> jugador.getColor().equals(color) )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un jugador con el color especificado: " + color));
    }
    public void mejorarACiudad(Coordenada coordenada) {
        Jugador jugadorActual = getJugadorActual();

        Ciudad nuevaCiudad = servicioComercio.venderCiudad(jugadorActual);

        try {
            // IMPACTAR EN TABLERO: Buscamos el vértice y mejoramos
            Vertice vertice = tablero.obtenerVertice(coordenada);

            // Validaciones de negocio (se pueden mover a Vertice también)
            if (vertice.getPropietario() != jugadorActual.getColor()) {
                throw new IllegalStateException("No puedes mejorar un edificio que no es tuyo.");
            }

            vertice.mejorarACiudad();

        } catch (IllegalStateException e) {
            // 3. ROLLBACK: Si falló (no era dueño, no había poblado, etc.), devolvemos la plata.
            servicioComercio.reembolsarCiudad(jugadorActual);
            throw e; // Avisar a la vista del error
        }

    }

    public void setServicioComercio(ServicioComercio servicioComercio) {
        this.servicioComercio = servicioComercio;
    }

}
