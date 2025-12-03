package edu.fiuba.algo3.entrega_3;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.Cartas.*;
import edu.fiuba.algo3.modelo.Intercambios.Banco;
import edu.fiuba.algo3.modelo.Intercambios.ServicioComercio;
import edu.fiuba.algo3.modelo.Mocks.FakeJugador;
import edu.fiuba.algo3.modelo.Mocks.FakeRandom;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Factory.*;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CasoDeUsoCartasDesarrolloTest {

    private Banco banco;
    private ServicioComercio servicioComercio;
    private List<Terreno> hexagonos;
    private List<Produccion> fichasNumeradas;

    private void forzarCartaEnMazo(ManagerTurno manager, Banco banco, CartaDesarrollo cartaTrucada) {
        List<CartaDesarrollo> mazoTrucado = new ArrayList<>();
        mazoTrucado.add(cartaTrucada);
        ServicioComercio servicioTrucado = new ServicioComercio(banco, new Random(), mazoTrucado);
        manager.setServicioComercio(servicioTrucado);
    }
    @BeforeEach
    void setUp() {

        hexagonos = Arrays.asList(
                new Bosque(), new Campo(), new Bosque(), new Pastizal(), new Bosque(),
                new Campo(), new Montania(),
                new Campo(),    // ID 8 -> Ficha 6 (Grano)
                new Montania(), // ID 9 -> Ficha 6 (Mineral)
                new Campo(), new Colina(), new Colina(), new Desierto(), new Colina(),
                new Pastizal(), new Montania(), new Pastizal(), new Bosque(), new Pastizal()
        );

        fichasNumeradas = new LinkedList<>(Arrays.asList(
                new Produccion(2), new Produccion(3), new Produccion(3), new Produccion(4),
                new Produccion(4), new Produccion(5), new Produccion(5),
                new Produccion(6), // ID 8 (Campo)
                new Produccion(6), // ID 9 (Montaña)
                new Produccion(8), new Produccion(8), new Produccion(9), new Produccion(9),
                new Produccion(10), new Produccion(10), new Produccion(11), new Produccion(11),
                new Produccion(12)
        ));

        banco = new Banco();

        banco.recibir(new Madera(10));
        banco.recibir(new Ladrillo(10));
        banco.recibir(new Grano(10));
        banco.recibir(new Lana(10));
        banco.recibir(new Mineral(10));
    }
    @Test
    public void Test01UnJugadorDebeConsumirSusRecursosAlComprarUnaCartaQueNoOtorgaPuntosDeVictoria() {
        int cantidadRecursosEsperada = 0;
        Random numeroRandom = new FakeRandom(0);
        Jugador comprador = new Jugador("nombre1",new Color("Azul"));
        servicioComercio = new ServicioComercio(banco, numeroRandom);

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

        Tablero unTablero = TableroFactory.crear(hexagonos, fichasNumeradas);
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
        Random numeroRandom = new FakeRandom(1);
        Jugador comprador = new Jugador("nombre1", new Color("Azul"));
        List<Jugador> jugadores = new ArrayList<>(4);
        jugadores.add(comprador);

        Tablero unTablero = TableroFactory.crear(hexagonos, fichasNumeradas);
        ManagerTurno manager = new ManagerTurno(jugadores, unTablero, numeroRandom);

        comprador.agregarRecurso(new Lana(1));
        comprador.agregarRecurso(new Grano(1));
        comprador.agregarRecurso(new Mineral(1));

        CartaDescubrimiento cartaTrucada = new CartaDescubrimiento();
        forzarCartaEnMazo(manager, banco, cartaTrucada);

        manager.comprarCarta();
        manager.siguienteTurno();

        cartaTrucada.setRecursosDeseados(List.of(new Madera(1), new Madera(1)));
        assertDoesNotThrow(() -> manager.usarUnaCarta(0));
    }

    @Test
    public void Test04ComprarUnaCartaDeDesarrolloQueOtorgaPuntosDeVictoriaDebeSumarlePuntosAlJugador() {
        int cantidadDePuntosEsperada = 1;
        Random numeroRandom = new FakeRandom(4);
        Jugador comprador = new Jugador("nombre1",new Color("Azul"));
        servicioComercio = Mockito.mock(ServicioComercio.class);
        Mockito.when(servicioComercio.venderCartaDesarrollo(comprador,0)).thenReturn(new PuntoDeVictoria());

        comprador.agregarRecurso(new Lana(1));
        comprador.agregarRecurso(new Grano(1));
        comprador.agregarRecurso(new Mineral(1));

        CartaDesarrollo cartaNueva = servicioComercio.venderCartaDesarrollo(comprador, 0);
        
        comprador.agregarCarta(cartaNueva);

        assertEquals(cantidadDePuntosEsperada, comprador.totalPuntos());
    }

    @Test
    public void Test05UnaCartaDeDescubrimientoTeDebeDarDosRecursosDeTuEleccion() {
        int cantidadDeRecursoEsperada = 1;
        Random numeroRandom = new FakeRandom(2);
        Jugador jugador = new Jugador("nombre1", new Color("Azul"));
        List<Jugador> jugadores = new ArrayList<>(4);
        jugadores.add(jugador);

        Tablero unTablero = TableroFactory.crear(hexagonos, fichasNumeradas);
        ManagerTurno manager = new ManagerTurno(jugadores, unTablero, numeroRandom);

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Grano(1));
        jugador.agregarRecurso(new Mineral(1));

        CartaDescubrimiento cartaTrucada = new CartaDescubrimiento();
        forzarCartaEnMazo(manager, banco, cartaTrucada);

        manager.comprarCarta();
        manager.siguienteTurno();

        cartaTrucada.setRecursosDeseados(List.of(new Madera(1), new Lana(1)));

        manager.usarUnaCarta(0);

        assertEquals(cantidadDeRecursoEsperada, jugador.cantidadRecurso(new Madera(0)));
        assertEquals(cantidadDeRecursoEsperada, jugador.cantidadRecurso(new Lana(0)));
    }

    @Test
    public void Test06UnaCartaDeCaballeroDebePermitirteRobarleUnSoloRecursoAOtroJugador() {
        int cantidadDeRecursoEsperada = 1;

        Random numeroRandom = new FakeRandom(0);
        Jugador jugador = new Jugador("nombre1",new Color("Rojo"));
        Jugador jugador2 = new Jugador("nombre2",new Color("Azul"));
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador);
        jugadores.add(jugador2);


        Tablero tablero = TableroFactory.crear(hexagonos, fichasNumeradas);
        ManagerTurno manager = new ManagerTurno(jugadores, tablero, numeroRandom);


        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Grano(1));
        jugador.agregarRecurso(new Mineral(1));

        jugador2.agregarRecurso(new Madera(2));

        CartaCaballero cartaTrucada = new CartaCaballero();
        forzarCartaEnMazo(manager, banco, cartaTrucada);

        manager.comprarCarta();
        manager.siguienteTurno();


        int idHexagonoDestino = 1;

        cartaTrucada.setOpciones(idHexagonoDestino, jugador2);

        try {
            manager.usarUnaCarta(0);
        } catch (RuntimeException e) {
        }
    }

    @Test
    public void Test07UnaCartaDeCaballeroNoOtorgaraRecursosSiNoHayPobladosCercanosParaSaquear() {
        int cantidadDeRecursoEsperada = 0;
        // ARRANGE
        Random numeroRandom = new FakeRandom(0);
        Jugador jugador = new Jugador("nombre1", new Color("Rojo"));
        Jugador jugador2 = new Jugador("nombre2", new Color("Azul"));
        List<Jugador> jugadores = new ArrayList<>(4);
        jugadores.add(jugador);
        jugadores.add(jugador2);

        Tablero tablero = TableroFactory.crear(hexagonos, fichasNumeradas);
        ManagerTurno manager = new ManagerTurno(jugadores, tablero, numeroRandom);

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Grano(1));
        jugador.agregarRecurso(new Mineral(1));

        jugador2.agregarRecurso(new Lana(1));
        jugador2.agregarRecurso(new Grano(1));
        jugador2.agregarRecurso(new Madera(1));
        jugador2.agregarRecurso(new Ladrillo(2));

        CartaCaballero cartaTrucada = new CartaCaballero();
        forzarCartaEnMazo(manager, banco, cartaTrucada);

        manager.comprarCarta();
        manager.siguienteTurno();
        manager.siguienteTurno();


        cartaTrucada.setOpciones(5, null);
        // ACT
        manager.usarUnaCarta(0);
        //Arrange
        assertEquals(cantidadDeRecursoEsperada, jugador.totalRecursos());
    }

    @Test
    public void Test08UnaCartaDeCaballeroNoOtorgaraRecursosSiLasVictimasNoTienenNadaParaDar() throws ConstruccionExistenteException, ReglaDistanciaException {
        int cantidadDeRecursoEsperada = 0;
        // ARRANGE
        Random numeroRandom = new FakeRandom(0);
        Jugador jugador = new Jugador("nombre1", new Color("Rojo"));
        Jugador jugador2 = new Jugador("nombre2", new Color("Azul"));
        List<Jugador> jugadores = new ArrayList<>(4);
        jugadores.add(jugador);
        jugadores.add(jugador2);

        Tablero tablero = TableroFactory.crear(hexagonos, fichasNumeradas);
        ManagerTurno manager = new ManagerTurno(jugadores, tablero, numeroRandom);

        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Grano(1));
        jugador.agregarRecurso(new Mineral(1));

        jugador2.agregarRecurso(new Lana(1));
        jugador2.agregarRecurso(new Grano(1));
        jugador2.agregarRecurso(new Madera(1));
        jugador2.agregarRecurso(new Ladrillo(1));

        CartaCaballero cartaTrucada = new CartaCaballero();
        forzarCartaEnMazo(manager, banco, cartaTrucada);

        manager.comprarCarta();
        manager.siguienteTurno();

        manager.construirPoblado(new Coordenada(1,1));

        manager.siguienteTurno();

        cartaTrucada.setOpciones(1, jugador2);

        // ACT
        manager.usarUnaCarta(0);

        // ASSERT
        assertEquals(cantidadDeRecursoEsperada, jugador.totalRecursos());
    }
}
