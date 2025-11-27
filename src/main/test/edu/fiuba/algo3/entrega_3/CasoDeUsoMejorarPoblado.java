package edu.fiuba.algo3.entrega_3;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Contruccion.Ciudad;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Intercambios.Banco;
import edu.fiuba.algo3.modelo.Intercambios.ServicioComercio;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ManagerTurno;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Factory.TableroFactory;
import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CasoDeUsoMejorarPoblado {

    List<Terreno> hexagonos = Arrays.asList(
            new Bosque(), new Campo(), new Bosque(), new Pastizal(), new Bosque(),
            new Campo(), new Montania(),
            new Campo(),    // ID 8 -> Ficha 6 (Grano)
            new Montania(), // ID 9 -> Ficha 6 (Mineral)
            new Campo(), new Colina(), new Colina(), new Desierto(), new Colina(),
            new Pastizal(), new Montania(), new Pastizal(), new Bosque(), new Pastizal()
    );

    List<Produccion> fichasNumeradas = new LinkedList<>(Arrays.asList(
            new Produccion(2), new Produccion(3), new Produccion(3), new Produccion(4),
            new Produccion(4), new Produccion(5), new Produccion(5),
            new Produccion(6), // ID 8 (Campo)
            new Produccion(6), // ID 9 (Montaña)
            new Produccion(8), new Produccion(8), new Produccion(9), new Produccion(9),
            new Produccion(10), new Produccion(10), new Produccion(11), new Produccion(11),
            new Produccion(12)
    ));
    Banco banco = new Banco();
    Jugador jugador = new Jugador("Constructor", new Color("Rojo"));
    @BeforeEach
    public void setUp() {
        banco.recibir(new Grano(20));
        banco.recibir(new Madera(20));
        banco.recibir(new Mineral(20));
        banco.recibir(new Lana(20));
        banco.recibir(new Ladrillo(20));


    }

    @Test
    public void test01JugadorMejoraPobladoACiudadExitosamente() throws Exception {
        // 1. ARRANGE
        Tablero tablero = TableroFactory.crear(hexagonos, fichasNumeradas);
        ServicioComercio servicio = new ServicioComercio(banco);

        Jugador jugador = new Jugador("Constructor", new Color("Rojo"));

        // Setup: El jugador YA tiene un poblado en el tablero
        Coordenada coord = new Coordenada(5, 0);
        Vertice vertice = tablero.obtenerVertice(coord);
        Poblado poblado = new Poblado(jugador.getColor());
        try {
            vertice.colocar(poblado); // Poblado inicial
        } catch (ConstruccionExistenteException e) {
            throw new RuntimeException(e);
        }

        // Le damos recursos para la CIUDAD (2 Grano, 3 Mineral)
        jugador.agregarRecurso(new Grano(2));
        jugador.agregarRecurso(new Mineral(3));

        ManagerTurno manager = new ManagerTurno(List.of(jugador), tablero, new Random());


        // 2. ACT
        manager.mejorarACiudad(coord);

        // 3. ASSERT

        //  Recursos gastados
        assertEquals(0, jugador.cantidadGrano());
        assertEquals(0, jugador.cantidadMineral());

        assertTrue(banco.tieneStock(new Mineral(0), 3));

        // El vértice ahora tiene una CIUDAD y produce DOBLE
        assertTrue(vertice.getTipoConstruccion() instanceof Ciudad);
        assertEquals(2, vertice.factorProduccion()); // Verifica que produce 2
    }

    @Test
    public void test02JugadorMejoraPobladoACiudadExitosamente() throws Exception {
        Tablero tablero = TableroFactory.crear(hexagonos, fichasNumeradas);
        ServicioComercio servicio = new ServicioComercio(banco);
        ManagerTurno manager = new ManagerTurno(List.of(jugador), tablero, new Random());
        manager.setServicioComercio(servicio);
        // Setup: El jugador YA tiene un poblado en el tablero
        Coordenada coord = new Coordenada(5, 0);
        Vertice vertice = tablero.obtenerVertice(coord);

        jugador.agregarRecurso(new Grano(5));
        jugador.agregarRecurso(new Mineral(5));
        jugador.agregarRecurso(new Madera(5));
        jugador.agregarRecurso(new Lana(5));
        jugador.agregarRecurso(new Ladrillo(5));

        manager.construirPoblado(coord);


        // Recursos gastados para construir el poblado
        assertEquals(4, jugador.cantidadGrano());
        assertEquals(5, jugador.cantidadMineral());
        assertEquals(4, jugador.cantidadLana());
        assertEquals(4, jugador.cantidadMadera());
        assertEquals(4, jugador.cantidadLadrillo());

        manager.mejorarACiudad(coord);
        // recursos gastados restantes por haber mejorado el pueblo a ciudad
        assertEquals(2, jugador.cantidadGrano());
        assertEquals(2, jugador.cantidadMineral());

        // Banco recibió los recursos (opcional si tienes getter en Banco)
        assertTrue(banco.tieneStock(new Mineral(0), 23));


        // El vértice ahora tiene una CIUDAD y produce DOBLE
        assertTrue(vertice.getTipoConstruccion() instanceof Ciudad);
        assertEquals(2, vertice.factorProduccion()); // Verifica que produce 2



    }
}
