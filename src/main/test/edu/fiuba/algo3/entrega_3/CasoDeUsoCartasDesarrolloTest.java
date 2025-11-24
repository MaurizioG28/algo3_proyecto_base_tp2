package edu.fiuba.algo3.entrega_3;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.Mocks.FakeRandom;
import edu.fiuba.algo3.modelo.Recursos.Grano;
import edu.fiuba.algo3.modelo.Recursos.Lana;
import edu.fiuba.algo3.modelo.Recursos.Mineral;
import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Factory.TableroFactory;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.TableroProduccion;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CasoDeUsoCartasDesarrolloTest {

    List<Terreno> hexagonos = Arrays.asList(
            new Bosque(),
            new Campo(),
            new Bosque(),
            new Pastizal(),
            new Bosque(),
            new Campo(),
            new Montania(),
            new Campo(),
            new Montania(),
            new Campo(),
            new Colina(),
            new Colina(),
            new Desierto(),
            new Colina(),
            new Pastizal(),
            new Montania(),
            new Pastizal(),
            new Bosque(),
            new Pastizal()
    );

    List<Produccion> fichasNumeradas = new LinkedList<>(Arrays.asList(
            new Produccion(2),
            new Produccion(3),
            new Produccion(3),
            new Produccion(4),
            new Produccion(4),
            new Produccion(5),
            new Produccion(5),
            new Produccion(6),
            new Produccion(6),
            new Produccion(8),
            new Produccion(8),
            new Produccion(9),
            new Produccion(9),
            new Produccion(10),
            new Produccion(10),
            new Produccion(11),
            new Produccion(11),
            new Produccion(12)

    ));

    @Test
    public void Test01UnJugadorDebeConsumirSusRecursosAlComprarUnaCartaQueNoOtorgaPuntosDeVictoria() {
        int cantidadRecursosEsperada = 0;
        Random numeroRandom = new FakeRandom(0);
        MazoOculto servicio = new MazoOculto(numeroRandom);
        Jugador comprador = new Jugador();

        comprador.agregarRecurso(new Lana(1));
        comprador.agregarRecurso(new Grano(1));
        comprador.agregarRecurso(new Mineral(1));

        servicio.comprarCarta(comprador, 0);

        assertEquals(cantidadRecursosEsperada, comprador.totalRecursos());
    }

    @Test
    public void Test02UnJugadorNoPuedeJugarUnaCartaDeDesarrolloEnElMismoTurnoQueLaCompra() {
        Random numeroRandom = new FakeRandom(0);
        MazoOculto unMazoOculto = new MazoOculto(numeroRandom);
        Jugador comprador = new Jugador();
        List<Jugador> jugadores = new ArrayList<>(4);
        jugadores.add(comprador);
        Tablero unTablero = TableroFactory.crear(hexagonos, fichasNumeradas);
        ManagerTurno manager = new ManagerTurno(jugadores, unTablero, numeroRandom, unMazoOculto);

        comprador.agregarRecurso(new Lana(1));
        comprador.agregarRecurso(new Grano(1));
        comprador.agregarRecurso(new Mineral(1));

        manager.comprarCarta();

        assertThrows(ReglaDeCompraYUsoException.class,
                () -> manager.usarUnaCarta(0));
    }

    @Test
    public void Test03UnJugadorDeberiaPoderUsarUnaCartaQueNoOtorgaPuntosDeVictoriaEnUnTurnoPosteriorALaCompra() {
        Random numeroRandom = new FakeRandom(0);
        MazoOculto unMazoOculto = new MazoOculto(numeroRandom);
        Jugador comprador = new Jugador();
        List<Jugador> jugadores = new ArrayList<>(4);
        jugadores.add(comprador);
        Tablero unTablero = TableroFactory.crear(hexagonos, fichasNumeradas);
        ManagerTurno manager = new ManagerTurno(jugadores, unTablero, numeroRandom, unMazoOculto);

        comprador.agregarRecurso(new Lana(1));
        comprador.agregarRecurso(new Grano(1));
        comprador.agregarRecurso(new Mineral(1));

        manager.comprarCarta();
        manager.siguienteTurno();

        try {
            manager.usarUnaCarta(0);
        } catch (ReglaDeCompraYUsoException e) {
            throw new RuntimeException(e);
        }

        assertDoesNotThrow(() -> {
            String mensaje = "Esta operacion no deberia activar ninguna excepcion.";
            System.out.println(mensaje);
        });
    }

    @Test
    public void Test04ComprarUnaCartaDeDesarrolloQueOtorgaPuntosDeVictoriaDebeSumarlePuntosAlJugador() {
        int cantidadDePuntosEsperada = 1;
        Random numeroRandom = new FakeRandom(4);
        MazoOculto servicio = new MazoOculto(numeroRandom);
        Jugador comprador = new Jugador();

        comprador.agregarRecurso(new Lana(1));
        comprador.agregarRecurso(new Grano(1));
        comprador.agregarRecurso(new Mineral(1));

        comprador.agregarCarta(servicio.comprarCarta(comprador, 0));

        assertEquals(cantidadDePuntosEsperada, comprador.totalPuntos());
    }
}
