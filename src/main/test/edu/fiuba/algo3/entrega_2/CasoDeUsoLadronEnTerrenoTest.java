package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Hexagono;
import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Bosque;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Desierto;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CasoDeUsoLadronEnTerrenoTest {
    //Verificar que el terreno no pueda producir recursos cuando hay un ladron.



    @Test
    public void Test01TerrenoNoPuedeProducirSiUnLadronEsColocado() throws ConstruccionExistenteException {
        Terreno terreno = new Bosque();
        Hexagono hexagono = new Hexagono();
        Jugador jugador = new Jugador("nombre1",new Color("Azul"));

        Vertice vertice = new Vertice();

        Poblado poblado = new Poblado(jugador.getColor());
        vertice.colocar(poblado);
        hexagono.agregarVertice(vertice);
        terreno.asignarHexagono(hexagono);
        terreno.setProduccion(new Produccion(2));
        hexagono.ponerLadron();



        List<Dividendo> divendos= terreno.verificarYProducir(2);




        assertNull(divendos);
    }

    @Test
    public void Test02TerrenoDeberiaProducirNormalmenteSiNoTieneUnLadronBloqueando() throws ConstruccionExistenteException {
        Terreno terreno = new Bosque();
        Hexagono hexagono = new Hexagono();
        Jugador jugador = new Jugador("nombre1",new Color("Azul"));
        Vertice vertice = new Vertice();
        List<Dividendo> dividendoEsperado =List.of( new Dividendo(jugador.getColor(), new Madera(1)));

        Poblado poblado = new Poblado(jugador.getColor());
        vertice.colocar(poblado);
        hexagono.agregarVertice(vertice);
        terreno.asignarHexagono(hexagono);
        terreno.setProduccion(new Produccion(2));

        List<Dividendo> dividendos= terreno.verificarYProducir(2);


//        terreno.producirRecurso();
//
        assertEquals(dividendoEsperado,dividendos);
    }

    @Test
    public void Test03TerrenoDeserticoNoDeberiaProducirRecursosBajoNingunaCircustancia() throws ConstruccionExistenteException {
        Terreno terreno = new Desierto();
        Hexagono hexagono = new Hexagono();
        Jugador jugador = new Jugador("nombre1",new Color("Azul"));
        Vertice vertice = new Vertice();
        Integer totalRecursosEsperados = 0;

        Poblado poblado = new Poblado(jugador.getColor());
        vertice.colocar(poblado);
        hexagono.agregarVertice(vertice);
        terreno.asignarHexagono(hexagono);
        terreno.setProduccion(new Produccion(2));


        List<Dividendo> dividendos= terreno.verificarYProducir(2);

//        terreno.producirRecurso();
//
        assertNull(dividendos);
    }

}
