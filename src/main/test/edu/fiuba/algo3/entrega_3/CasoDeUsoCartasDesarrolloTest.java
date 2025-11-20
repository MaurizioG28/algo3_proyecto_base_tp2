package edu.fiuba.algo3.entrega_3;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CasoDeUsoCartasDesarrolloTest {

    @Test
    public void Test01UnJugadorDebeConsumirSusRecursosAlComprarUnaCarta() {
        int cantidadRecursosEsperada = 0;
        Random numeroRandom = new Random();
        MazoOculto servicio = new MazoOculto(numeroRandom);
        Jugador comprador = new Jugador();

        comprador.agregarRecurso(Recurso.LANA, 1);
        comprador.agregarRecurso(Recurso.GRANO, 1);
        comprador.agregarRecurso(Recurso.MINERAL, 1);

        servicio.comprarCarta(comprador, 0);

        assertEquals(cantidadRecursosEsperada, comprador.totalRecursos());
    }

    @Test
    public void Test02UnJugadorNoPuedeJugarUnaCartaDeDesarrolloEnElMismoTurnoQueLaCompra() {
        Random numeroRandom = new Random();
        MazoOculto unMazoOculto = new MazoOculto(numeroRandom);
        Jugador comprador = new Jugador();
        List<Jugador> jugadores = new ArrayList<>(4);
        jugadores.add(comprador);
        Tablero unTablero = new Tablero();
        ManagerTurno manager = new ManagerTurno(jugadores, unTablero, numeroRandom, unMazoOculto);

        comprador.agregarRecurso(Recurso.LANA, 1);
        comprador.agregarRecurso(Recurso.GRANO, 1);
        comprador.agregarRecurso(Recurso.MINERAL, 1);

        manager.comprarCarta();

        assertThrows(ReglaDeCompraYUsoException.class,
                () -> manager.usarUnaCarta(0));
    }

    @Test
    public void Test03UnJugadorDeberiaPoderUsarUnaCartaEnUnTurnoPosteriorALaCompra() {
        Random numeroRandom = new Random();
        MazoOculto unMazoOculto = new MazoOculto(numeroRandom);
        Jugador comprador = new Jugador();
        List<Jugador> jugadores = new ArrayList<>(4);
        jugadores.add(comprador);
        Tablero unTablero = new Tablero();
        ManagerTurno manager = new ManagerTurno(jugadores, unTablero, numeroRandom, unMazoOculto);

        comprador.agregarRecurso(Recurso.LANA, 1);
        comprador.agregarRecurso(Recurso.GRANO, 1);
        comprador.agregarRecurso(Recurso.MINERAL, 1);

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
}
