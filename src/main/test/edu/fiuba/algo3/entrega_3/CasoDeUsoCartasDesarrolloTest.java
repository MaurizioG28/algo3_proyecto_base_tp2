package edu.fiuba.algo3.entrega_3;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.Mocks.FakeRandom;
import edu.fiuba.algo3.modelo.Recursos.Grano;
import edu.fiuba.algo3.modelo.Recursos.Lana;
import edu.fiuba.algo3.modelo.Recursos.Mineral;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CasoDeUsoCartasDesarrolloTest {

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
        Tablero unTablero = new Tablero();
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
        Tablero unTablero = new Tablero();
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

        servicio.comprarCarta(comprador, 0);

        assertEquals(cantidadDePuntosEsperada, comprador.totalPuntos());
    }
}
