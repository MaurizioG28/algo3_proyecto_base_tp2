package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.CasosDeUso.ColocacionInicial;
import edu.fiuba.algo3.modelo.Contruccion.Ciudad;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Recursos.Grano;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Recursos.Mineral;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Factory.*;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class CasoDeUsoProduccionRecursos {

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

    @Test
    public void test01PobladoProduceUnRecursoYCiudadProduceDos() throws ConstruccionExistenteException {
        // 1. ARRANGE (Preparación del escenario)

        // Jugadores
        Jugador jugadorPoblado = new Jugador("Jugador 1", new Color("Rojo"));
        Poblado poblado = new Poblado(jugadorPoblado.getColor());
        Jugador jugadorCiudad = new Jugador("Jugador 2", new Color("Azul"));
        Poblado ciudad = new Poblado(jugadorCiudad.getColor());

        // Terreno: Bosque que produce con el número 6
        Bosque bosque = new Bosque();
        bosque.setProduccion(new Produccion(6));
        bosque.setId(1); // ID arbitrario para el mapa

        // Hexágono y Vértices (Conectamos manualmente para no depender de la Factory completa)
        Hexagono hexagono = new Hexagono();
        bosque.asignarHexagono(hexagono);

        // Vértice 1: Tiene un POBLADO del Jugador 1
        Vertice v1 = new Vertice();
        v1.colocar(poblado);
        // Simula que el poblado ya está construido

        // Vértice 2: Tiene una CIUDAD del Jugador 2
        Vertice v2 = new Vertice();
        v2.colocar(ciudad);
        v2.mejorarACiudad();

        // Conectamos vértices al hexágono
        hexagono.agregarVertice(v1);
        hexagono.agregarVertice(v2);

        // Armamos el Tablero con este único terreno
        Map<Integer, Terreno> terrenos = new HashMap<>();
        terrenos.put(1, bosque);

        // Mockeamos  mapas vacíos para lo que no usaremos en este test (lados, etc)
        Tablero tablero = new Tablero(terrenos, new HashMap<>(), new HashMap<>());

        // Manager de Turno
        ManagerTurno manager = new ManagerTurno(
                List.of(jugadorPoblado, jugadorCiudad),
                tablero,
                new Random(),
                mock(MazoOculto.class)
        );

        // 2. ACT (Salió el 6 en los dados)
        manager.repartirDividendos(6);

        // 3. ASSERT (Verificaciones)

        // Jugador 1 (Poblado) debería tener 1 Madera

        assertEquals(1, jugadorPoblado.cantidadMadera(), "El poblado debería dar 1 recurso");

        // Jugador 2 (Ciudad) debería tener 2 Maderas
        assertEquals(2, jugadorCiudad.cantidadMadera(), "La ciudad debería dar 2 recursos");
    }

    @Test
    public void test02SiSaleOtroNumeroNadieCobra() {
        // 1. ARRANGE
        Jugador jugador = new Jugador("Jugador 1", new Color("Rojo"));

        // Montaña produce Mineral con el 8
        Montania montania = new Montania();
        montania.setProduccion(new Produccion(8));
        montania.setId(1);

        Hexagono hexagono = new Hexagono();
        montania.asignarHexagono(hexagono);

        Vertice v1 = new Vertice();
        Poblado poblado = new Poblado(jugador.getColor());
        try {
            v1.colocar(poblado);
        } catch (ConstruccionExistenteException e) {
            throw new RuntimeException(e);
        }
        hexagono.agregarVertice(v1);

        Map<Integer, Terreno> terrenos = new HashMap<>();
        terrenos.put(1, montania);
        Tablero tablero = new Tablero(terrenos, new HashMap<>(), new HashMap<>());

        ManagerTurno manager = new ManagerTurno(List.of(jugador), tablero, new Random(), mock(MazoOculto.class));

        // 2. ACT (Sale el 5, pero la montaña necesita un 8)
        manager.repartirDividendos(5);

        // 3. ASSERT
        assertEquals(0, jugador.cantidadMineral(), "No se debería producir si el número no coincide");
    }

    @Test
    public void test03JugadorEnInterseccionCobraDeDosTerrenos() {
        // Este test verifica que si un poblado está entre dos terrenos y sale el número, cobra de ambos.
        // O si tienen el mismo número, cobra doble.

        // 1. ARRANGE
        Jugador jugador = new Jugador("Jugador Multi", new Color("Verde"));

        // Terreno 1: Campo (Grano) con el 4
        Campo campo = new Campo();
        campo.setProduccion(new Produccion(4));
        campo.setId(1);
        Hexagono hexCampo = new Hexagono();
        campo.asignarHexagono(hexCampo);

        // Terreno 2: Bosque (Madera) con el 4
        Bosque bosque = new Bosque();
        bosque.setProduccion(new Produccion(4));
        bosque.setId(2);
        Hexagono hexBosque = new Hexagono();
        bosque.asignarHexagono(hexBosque);

        // Vértice COMPARTIDO
        Vertice verticeCompartido = new Vertice();
        Poblado poblado = new Poblado(jugador.getColor());
        try {
            verticeCompartido.colocar(poblado);
        } catch (ConstruccionExistenteException e) {
            throw new RuntimeException(e);
        }


        // Agregamos el MISMO vértice a ambos hexágonos
        hexCampo.agregarVertice(verticeCompartido);
        hexBosque.agregarVertice(verticeCompartido);

        Map<Integer, Terreno> terrenos = new HashMap<>();
        terrenos.put(1, campo);
        terrenos.put(2, bosque);

        Tablero tablero = new Tablero(terrenos, new HashMap<>(), new HashMap<>());
        ManagerTurno manager = new ManagerTurno(List.of(jugador), tablero, new Random(), mock(MazoOculto.class));

        // 2. ACT (Sale el 4)
        manager.repartirDividendos(4);

        // 3. ASSERT
        assertEquals(1, jugador.cantidadGrano(), "Debe recibir 1 Grano del Campo");
        assertEquals(1, jugador.cantidadMadera(), "Debe recibir 1 Madera del Bosque");
    }


    @Test
    public void testProduccionDeRecursosEnTableroCompleto() throws ConstruccionExistenteException {
        // 1. ARRANGE (Preparación)
        Tablero tablero = TableroFactory.crear(hexagonos, fichasNumeradas);

        Jugador jugadorAzul = new Jugador("Azul", new Color("Azul"));
        Jugador jugadorRojo = new Jugador("Rojo", new Color("Rojo"));

        ManagerTurno manager = new ManagerTurno(
                List.of(jugadorAzul, jugadorRojo),
                tablero,
                new Random(),
                mock(MazoOculto.class)
        );

        // --- CONSTRUCCIÓN ---

        // JUGADOR AZUL: Construye POBLADO en el Campo (ID 8, Ficha 6)

        Vertice verticeCampo = tablero.obtenerVertice(new Coordenada(8, 0));
        Poblado poblado1 = new Poblado(jugadorAzul.getColor());
        verticeCampo.colocar(poblado1);
        // Al usar colocarPoblado, se asigna el dueño y el tipo Poblado (Factor 1)


        // JUGADOR ROJO: Construye CIUDAD en la Montaña (ID 9, Ficha 6)
        Vertice verticeMontania = tablero.obtenerVertice(new Coordenada(9, 3));
        Poblado poblado = new Poblado(jugadorRojo.getColor());
        verticeMontania.colocar(poblado);
        verticeMontania.mejorarACiudad();
        // Ahora es dueño 'Rojo' y tipo Ciudad (Factor 2)


        // 2. ACT (Acción)
        // Simulamos que los dados dieron 6
        manager.repartirDividendos(6);


        // 3. ASSERT (Verificación)

        // Verificamos al AZUL (Poblado en Campo)
        assertEquals(1, jugadorAzul.cantidadGrano(),
                "El jugador Azul debería tener 1 Grano (Poblado)");
        assertEquals(0, jugadorAzul.cantidadMineral(),
                "El jugador Azul NO debería tener Mineral");

        // Verificamos al ROJO (Ciudad en Montaña)
        assertEquals(2, jugadorRojo.cantidadMineral(),
                "El jugador Rojo debería tener 2 Minerales (Ciudad)");
        assertEquals(0, jugadorRojo.cantidadGrano(),
                "El jugador Rojo NO debería tener Grano");
    }
}
