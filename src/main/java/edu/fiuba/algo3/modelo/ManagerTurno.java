package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Contruccion.Ciudad;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Intercambios.Banco;
import edu.fiuba.algo3.modelo.Intercambios.ServicioComercio;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class ManagerTurno {
    private final List<Jugador> jugadores;
    private int indiceJugadorActual = 0;
    private int numeroTurnoActual = 0;
    private final Tablero tablero;
    private final Random azar;
    private ServicioComercio servicioComercio = new ServicioComercio(new Banco());

    public ManagerTurno(List<Jugador> jugadores, Tablero tablero, Random Random) {
        this.jugadores = jugadores;
        this.tablero = tablero;
        this.azar=  Random;
    }

    public void comprarCarta() {
        Jugador jugador = getJugadorActual();
        jugador.agregarCarta(servicioComercio.venderCartaDesarrollo(jugador,numeroTurnoActual));
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

                Jugador jugadorActual = getJugadorActual();
                tablero.colocarEnVertice(poblado, coordenada);
            } catch (ReglaDistanciaException | ConstruccionExistenteException e){
                // 3. ¡Error! El lugar estaba ocupado o muy cerca. Devolvemos la plata.
                servicioComercio.reembolsarPoblado(getJugadorActual());
                throw e; // Avisamos a la vista
            }

        } catch (ReglaDistanciaException | ConstruccionExistenteException e) {
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
            tablero.mejoraACiudadEn(coordenada,jugadorActual.getColor());
        } catch (IllegalStateException e) {
            // 3. ROLLBACK: Si falló (no era dueño, no había poblado, etc.), devolvemos la plata.
            servicioComercio.reembolsarCiudad(jugadorActual);
            throw e; // Avisar a la vista del error
        }

    }

    public void setServicioComercio(ServicioComercio servicioComercio) {
        this.servicioComercio = servicioComercio;
    }

    public void intercambiarConJugadores(Jugador jugador1, TipoDeRecurso recursoAentregar, int cantidadAentregar, TipoDeRecurso recursoArecibir, int cantidadArecibir, List<Jugador> jugadores){
        servicioComercio.intercambiarConJugadores(jugador1,
                recursoAentregar,
                cantidadAentregar,
                recursoArecibir,
                cantidadArecibir,
                jugadores);
    }

}
