package edu.fiuba.algo3.entrega_3;

import edu.fiuba.algo3.modelo.Cartas.CartaMonopolio;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ManagerTurno;
import edu.fiuba.algo3.modelo.Mocks.FakeRandom;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Factory.TableroFactory;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Bosque;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Desierto;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;
import edu.fiuba.algo3.modelo.constructoresDeCarreteras.EstrategiaPagoGratuito;
import org.junit.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CasoDeUsoCartaMonopolioRobaTodosLosRecursosDelTipoElegido {
    @Test
    public void Test01CartaMonopolioRobaTodosLosRecursosDelTipoElegido(){
        Random numeroRandom = new FakeRandom(0);
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

        Jugador ladron = new Jugador("Ladrón", new Color("Rojo"));
        Jugador victimaRica = new Jugador("Rico", new Color("Azul"));
        Jugador victimaAstuta = new Jugador("Astuto", new Color("Verde"));

        List<Jugador> jugadores = List.of(ladron, victimaRica, victimaAstuta);
        ManagerTurno manager = new ManagerTurno(jugadores, tablero, numeroRandom);

        victimaRica.agregarRecurso(new Madera(3));
        victimaAstuta.agregarRecurso(new Madera(2));

        victimaRica.agregarRecurso(new Ladrillo(5));


        victimaAstuta.setEstrategiaDePago(new EstrategiaPagoGratuito());

        CartaMonopolio cartaMonopolio = new CartaMonopolio(1);
        cartaMonopolio.setRecursoElegido(new Madera(0));
        ladron.agregarCarta(cartaMonopolio);


        manager.usarUnaCarta(0);

        assertEquals(5, ladron.cantidadRecurso(new Madera(0)));

        assertEquals(0, victimaRica.cantidadRecurso(new Madera(0)));

        assertEquals(5, victimaRica.cantidadRecurso(new Ladrillo(0)));

    //La estrategia gratuita no debería proteger contra el robo directo del Monopolio
        assertEquals(0, victimaAstuta.cantidadRecurso(new Madera(0)));
    }
}
