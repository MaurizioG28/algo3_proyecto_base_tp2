package edu.fiuba.algo3.entrega_3;

//import edu.fiuba.algo3.modelo.CasosDeUso.ConsumoRecursos;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Jugador;

import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CasoDeUsoConsumoRecursos {

//    @Test
//    public void tets01construirPobladoReduceCorrectamenteLosRecursos() {
//
//        Vertice vertice1 = new Vertice();
//        Vertice vertice2 = new Vertice();
//        Vertice vertice3 = new Vertice();
//
//        vertice1.agregarAdyacente(vertice2);
//        vertice1.agregarAdyacente(vertice3);
//
//        Jugador jugador1 = new Jugador(new Color("Azul"));
//        jugador1.agregarRecurso(Recurso.MADERA, 1);
//        jugador1.agregarRecurso(Recurso.GRANO, 1);
//        jugador1.agregarRecurso(Recurso.LANA, 1);
//        jugador1.agregarRecurso(Recurso.LADRILLO, 1);
//
//        ConsumoRecursos caso = new ConsumoRecursos();
//
//        int cantidadRecursosEsperados = 0;
//
//        try {
//            caso.colocarPoblado(jugador1, vertice1);
//        } catch (ConstruccionExistenteException | ReglaDistanciaException e) {
//            throw new RuntimeException(e);
//        }
//
//        assertEquals(cantidadRecursosEsperados, jugador1.totalRecursos());
//    }
//
//    @Test
//    public void tets02luegoDeColocarPobladoNoSePuedeConstruirNuevamente() {
//
//        Vertice vertice1 = new Vertice();
//        Vertice vertice2 = new Vertice();
//        Vertice vertice3 = new Vertice();
//
//        vertice1.agregarAdyacente(vertice2);
//        vertice1.agregarAdyacente(vertice3);
//
//        Jugador jugador1 = new Jugador(new Color("Azul"));
//        jugador1.agregarRecurso(Recurso.MADERA, 1);
//        jugador1.agregarRecurso(Recurso.GRANO, 1);
//        jugador1.agregarRecurso(Recurso.LANA, 1);
//        jugador1.agregarRecurso(Recurso.LADRILLO, 1);
//        Poblado poblado = new Poblado(new Color("Verde"));
//
//        ConsumoRecursos caso = new ConsumoRecursos();
//        try {
//            caso.colocarPoblado(jugador1, vertice1);
//        } catch (ConstruccionExistenteException | ReglaDistanciaException e) {
//            throw new RuntimeException(e);
//        }
//
//        assertThrows(ConstruccionExistenteException.class, ()-> vertice1.colocar(poblado));
//    }
//
//    @Test
//    public void tets03NoSePuedeConstruirSiHayConstruccionAdyacente() {
//
//        Vertice vertice1 = new Vertice();
//        Vertice vertice2 = new Vertice();
//        Vertice vertice3 = new Vertice();
//
//        Jugador jugador1 = new Jugador(new Color("Azul"));
//        jugador1.agregarRecurso(Recurso.MADERA, 1);
//        jugador1.agregarRecurso(Recurso.GRANO, 1);
//        jugador1.agregarRecurso(Recurso.LANA, 1);
//        jugador1.agregarRecurso(Recurso.LADRILLO, 1);
//        Poblado poblado = new Poblado(new Color("Verde"));
//
//        ConsumoRecursos caso = new ConsumoRecursos();
//        try {
//            vertice2.colocar(poblado);
//        } catch (ConstruccionExistenteException e) {
//            throw new RuntimeException(e);
//        }
//
//        vertice1.agregarAdyacente(vertice2);
//        vertice1.agregarAdyacente(vertice3);
//
//        assertThrows(ReglaDistanciaException.class, ()-> caso.colocarPoblado(jugador1, vertice1));
//    }
}
