package edu.fiuba.algo3.entrega_3;

import edu.fiuba.algo3.modelo.Cartas.CartaConstruccionCarreteras;
import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Cartas.EstadoCartaDisponible;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ManagerTurno;
import edu.fiuba.algo3.modelo.Mocks.FakeRandom;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.RecursosInsuficientesException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Factory.TableroFactory;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Bosque;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Desierto;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CartaConstruccionDeCarreterasPermiteDosConstruccionesGratuitas {
    @Test
    public void Test01CartaConstruccionDeCarreterasPermiteDosConstruccionesGratuitas() {
        //ARRANGE
        Random numeroRandom = new FakeRandom(0);
        Jugador jugador = new Jugador("Constructor", new Color("Amarillo"));
        //Arrange
        TableroFactory tableroFactory = new TableroFactory();
        List<Terreno> hexagonos = new ArrayList<>();
        //Agrega 18 Hexagonos de Bosque
        for (int i = 0; i < 18; i++) {
            hexagonos.add(new Bosque());
        }

        hexagonos.add(new Desierto());

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
        Tablero tablero = tableroFactory.crear(hexagonos, fichasNumeradas);

        List<Jugador> jugadores = List.of(jugador);
        ManagerTurno manager = new ManagerTurno(jugadores, tablero, numeroRandom);

        Coordenada coordPoblado = new Coordenada(4, 4);
        Coordenada coordCalle1 = new Coordenada(4, 4);  // Adyacente al poblado
        Coordenada coordCalle2 = new Coordenada(4, 3);  // Adyacente a la calle 1


        jugador.agregarRecurso(new Madera(1));
        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Grano(1));
        jugador.agregarRecurso(new Lana(1));
        manager.construirPoblado(coordPoblado);


        assertEquals(0, jugador.cantidadRecurso(new Madera(0)));
        assertEquals(0, jugador.cantidadRecurso(new Ladrillo(0)));

        CartaDesarrollo cartaCarreteras = new CartaConstruccionCarreteras(new EstadoCartaDisponible());
        jugador.agregarCarta(cartaCarreteras);

        manager.usarUnaCarta(0);

        assertDoesNotThrow(() -> manager.construirCarretera(coordCalle1),
                "La primera carretera debe ser gratuita. El jugador no tiene recursos pero la estrategia lo permite.");

        assertDoesNotThrow(() -> manager.construirCarretera(coordCalle2),
                "La segunda carretera debe ser gratuita. Al finalizar, la estrategia debe autodestruirse.");

        assertTrue(jugador.tiene(new Madera(0),new Ladrillo(0),new Lana(0),new Mineral(0),new Grano(0)));
    }

}
