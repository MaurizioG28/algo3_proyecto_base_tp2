package edu.fiuba.algo3.entrega_3;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Intercambios.Banco;
import edu.fiuba.algo3.modelo.Intercambios.ServicioComercio;
import edu.fiuba.algo3.modelo.Mocks.FakeRandom;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.Lado;
import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CasoDeUsoCartasDesarrolloTest {

    private Banco banco;
    private ServicioComercio servicioComercio;
    @BeforeEach
    void setUp() {
        banco = new Banco();

        banco.recibir(new Madera(10));
        banco.recibir(new Ladrillo(10));
        banco.recibir(new Grano(10));
        servicioComercio = new ServicioComercio(banco);
    }
    @Test
    public void Test01UnJugadorDebeConsumirSusRecursosAlComprarUnaCartaQueNoOtorgaPuntosDeVictoria() {
        int cantidadRecursosEsperada = 0;
        Random numeroRandom = new FakeRandom(0);
        Jugador comprador = new Jugador("nombre1",new Color("Azul"));
        comprador.agregarRecurso(new Lana(1));
        comprador.agregarRecurso(new Grano(1));
        comprador.agregarRecurso(new Mineral(1));

        servicioComercio.venderCartaDesarrollo(comprador, 0);

        assertEquals(cantidadRecursosEsperada, comprador.totalRecursos());
    }

    @Test
    public void Test02UnJugadorNoPuedeJugarUnaCartaDeDesarrolloEnElMismoTurnoQueLaCompra() {
        Random numeroRandom = new FakeRandom(0);
        Jugador comprador = new Jugador("nombre1",new Color("Azul"));
        List<Jugador> jugadores = new ArrayList<>(4);
        jugadores.add(comprador);
        Map<Integer, Terreno> hexagonos = new HashMap<>();
        Map<Coordenada, Vertice> vertices= new HashMap<>();
        Map<Coordenada, Lado> ladosPorCoordenada= new HashMap<>();
        Tablero unTablero = new Tablero(hexagonos, vertices, ladosPorCoordenada);
        ManagerTurno manager = new ManagerTurno(jugadores, unTablero, numeroRandom);

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
        Jugador comprador = new Jugador("nombre1",new Color("Azul"));
        List<Jugador> jugadores = new ArrayList<>(4);
        jugadores.add(comprador);
        Map<Integer, Terreno> hexagonos = new HashMap<>();
        Map<Coordenada, Vertice> vertices= new HashMap<>();
        Map<Coordenada, Lado> ladosPorCoordenada= new HashMap<>();

        Tablero unTablero = new Tablero(hexagonos, vertices, ladosPorCoordenada);
        ManagerTurno manager = new ManagerTurno(jugadores, unTablero, numeroRandom);

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
        Jugador comprador = new Jugador("nombre1",new Color("Azul"));

        comprador.agregarRecurso(new Lana(1));
        comprador.agregarRecurso(new Grano(1));
        comprador.agregarRecurso(new Mineral(1));

        CartaDesarrollo cartaNueva = servicioComercio.venderCartaDesarrollo(comprador, 0);
        
        comprador.agregarCarta(cartaNueva);

        assertEquals(cantidadDePuntosEsperada, comprador.totalPuntos());
    }
}
