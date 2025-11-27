package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Cartas.GranRutaComercial;
import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.PuntajeDeVictoria;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.Lado;
import edu.fiuba.algo3.modelo.Tablero.Factory.ReglaConstruccionException;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GranRutaTest {

    @Test
    public void test01_rutaSimpleDeTresLados() throws ConstruccionExistenteException, ReglaConstruccionException {
        Catan catan=new Catan();
        GranRutaComercial granRutaComercial=new GranRutaComercial();
        Jugador jugadorActual = new Jugador("j1", new Color("azul"));
        Tablero tablero=catan.crearTablero();
        try {
            tablero.colocarEnVertice(new Poblado(new Color("azul")),new Coordenada(1,0));
        } catch (ConstruccionExistenteException | ReglaDistanciaException e) {
            throw new RuntimeException(e);
        }
        tablero.colocarEnLado(new Carretera(new Color("azul")),new Coordenada(1,0));
        tablero.colocarEnLado(new Carretera(new Color("azul")),new Coordenada(1,1));
        tablero.colocarEnLado(new Carretera(new Color("azul")),new Coordenada(1,2));


        List<Lado> ladosJugador = tablero.obtenerLadosDeJugador(new Color("azul"));
        int longitudEsperada=3;
        int longitud = granRutaComercial.calcular(ladosJugador);
        //granRutaComercial.actualizarRutaDeJugador(jugadorActual, longitud);


        assertEquals(longitudEsperada, longitud);
    }
    @Test
    public void test02_NoDaBonusConRutaMenorA5() {
        GranRutaComercial grc = new GranRutaComercial();
        Jugador j1 = new Jugador("A", new Color("Azul"));

        grc.actualizarRutaDeJugador(j1, 3);
        PuntajeDeVictoria puntosEsperados = new PuntajeDeVictoria();

        assertTrue( j1.mismoPuntaje(puntosEsperados));
    }

    @Test
    public void test03_PrimerJugadorConRutaDe5ObtieneBonus() {
        GranRutaComercial grc = new GranRutaComercial();
        Jugador j1 = new Jugador("A", new Color("Azul"));

        grc.actualizarRutaDeJugador(j1, 5);

        PuntajeDeVictoria puntosEsperados = new PuntajeDeVictoria();
        puntosEsperados.agregarPuntos(2);
        assertTrue( j1.mismoPuntaje(puntosEsperados));
    }

    @Test
    public void test04_SuperacionTransfiereElBonus() {
        GranRutaComercial grc = new GranRutaComercial();
        Jugador j1 = new Jugador("A", new Color("Azul"));
        Jugador j2 = new Jugador("R", new Color("Rojo"));

        grc.actualizarRutaDeJugador(j1, 5);
        grc.actualizarRutaDeJugador(j2, 7);
        PuntajeDeVictoria puntosEsperadosLider = new PuntajeDeVictoria();
        PuntajeDeVictoria puntosEsperados = new PuntajeDeVictoria();

        puntosEsperadosLider.agregarPuntos(2);

        assertTrue( j2.mismoPuntaje(puntosEsperadosLider));
        assertTrue( j1.mismoPuntaje(puntosEsperados));
    }


}
