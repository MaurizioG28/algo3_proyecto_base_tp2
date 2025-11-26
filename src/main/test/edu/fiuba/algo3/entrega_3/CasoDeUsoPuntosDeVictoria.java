package edu.fiuba.algo3.entrega_3;

import edu.fiuba.algo3.modelo.CasosDeUso.CasoDeUsoPV;
import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.PuntajeDeVictoria;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CasoDeUsoPuntosDeVictoria {

    @Test
    public void test01PuntosDeVictoriaDespuesDeColocacionInicial(){
        Catan catan = new Catan();
        Tablero tablero=catan.crearTablero();
        Jugador jugador=new Jugador("azul",new Color("azul"));
        CasoDeUsoPV casoDeUsoPV=new CasoDeUsoPV(tablero);

        casoDeUsoPV.colocacionInicial(jugador,new Coordenada(1,0),new Coordenada(2,3));
        PuntajeDeVictoria puntosEsperados=new PuntajeDeVictoria();
        puntosEsperados.setPuntosPublicos(2);

        PuntajeDeVictoria puntajeObtenido=casoDeUsoPV.obtenerPuntosDeVictoria(jugador);
        assertEquals(puntosEsperados,puntajeObtenido);

    }

    @Test
    public void test02PuntosDeVictoriaAlMejorarConstruccion(){
        Catan catan = new Catan();
        Tablero tablero=catan.crearTablero();
        Jugador jugador=new Jugador("rojo",new Color("rojo"));
        CasoDeUsoPV casoDeUsoPV=new CasoDeUsoPV(tablero);


        casoDeUsoPV.mejorarPoblado(jugador);
        PuntajeDeVictoria puntosEsperados=new PuntajeDeVictoria();
        puntosEsperados.setPuntosPublicos(3);

        PuntajeDeVictoria puntajeObtenido=casoDeUsoPV.obtenerPuntosDeVictoria(jugador);
        assertEquals(puntosEsperados,puntajeObtenido);

    }
    @Test
    public void test03PuntosDeVictoriaSinConstrucciones(){
        Catan catan = new Catan();
        Tablero tablero=catan.crearTablero();
        Jugador jugador=new Jugador("azul",new Color("azul"));
        CasoDeUsoPV casoDeUsoPV=new CasoDeUsoPV(tablero);


        PuntajeDeVictoria puntosEsperados=new PuntajeDeVictoria();


        PuntajeDeVictoria puntajeObtenido=casoDeUsoPV.obtenerPuntosDeVictoria(jugador);
        assertEquals(puntosEsperados,puntajeObtenido);

    }
    @Test
    public void test04PuntosDeVictoriaDespuesDeColocacionInicial2Jugadors(){
        Catan catan = new Catan();
        Tablero tablero=catan.crearTablero();
        Jugador jugador=new Jugador("azul",new Color("azul"));
        Jugador jugador2=new Jugador("rojo",new Color("rojo"));
        CasoDeUsoPV casoDeUsoPV=new CasoDeUsoPV(tablero);


        casoDeUsoPV.mejorarPoblado(jugador);
        casoDeUsoPV.colocacionInicial(jugador2,new Coordenada(1,3),new Coordenada(6,2));
        PuntajeDeVictoria puntosEsperadosAzul=new PuntajeDeVictoria();
        puntosEsperadosAzul.setPuntosPublicos(3);

        PuntajeDeVictoria puntajeObtenido=casoDeUsoPV.obtenerPuntosDeVictoria(jugador);
        assertEquals(puntosEsperadosAzul,puntajeObtenido);

        PuntajeDeVictoria puntosEsperadosRojo=new PuntajeDeVictoria();
        puntosEsperadosRojo.setPuntosPublicos(2);

        PuntajeDeVictoria puntajeObtenidoRojo=casoDeUsoPV.obtenerPuntosDeVictoria(jugador2);
        assertEquals(puntosEsperadosRojo,puntajeObtenidoRojo);

    }
    @Test
    public void test05jugadorGanaCon10Puntos() {
        Catan catan = new Catan();
        Jugador j = new Jugador("Jugador1", new Color("Azul"));
        catan.agregarJugador(j);


        for(int i = 0; i < 10; i++) {
            j.sumarPuntoDeVictoriaPublico();
        }

        assertEquals(j,catan.hayGanador());
    }



}
