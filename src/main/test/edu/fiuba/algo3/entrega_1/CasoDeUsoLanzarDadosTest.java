package edu.fiuba.algo3.entrega_1;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ManagerTurno;
import edu.fiuba.algo3.modelo.MazoOculto;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Dados;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Factory.TableroFactory;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CasoDeUsoLanzarDadosTest {

    @Test
    public void testCasoDeUsoLanzarDados() throws ConstruccionExistenteException, ReglaDistanciaException {
        TableroFactory tableroFactory = new TableroFactory();
        List<Terreno> hexagonos = Arrays.asList(
                new Bosque(),
                new Bosque(),
                new Bosque(),
                new Bosque(),
                new Bosque(),
                new Bosque(),
                new Bosque(),
                new Bosque(),
                new Bosque(),
                new Bosque(),
                new Bosque(),
                new Bosque(),
                new Bosque(),
                new Bosque(),
                new Desierto(),
                new Bosque(),
                new Bosque(),
                new Bosque(),
                new Bosque()
        );
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
        Jugador jugadorMarcos = new Jugador("Marcos",new Color("rojo"));
        jugadorMarcos.agregarRecurso(new Madera(1));
        jugadorMarcos.agregarRecurso(new Grano(1));
        jugadorMarcos.agregarRecurso(new Ladrillo(1));
        jugadorMarcos.agregarRecurso(new Lana(1));
        jugadorMarcos.agregarRecurso(new Madera(1));
        jugadorMarcos.agregarRecurso(new Grano(1));
        jugadorMarcos.agregarRecurso(new Ladrillo(1));
        jugadorMarcos.agregarRecurso(new Lana(1));
        jugadorMarcos.tiene(1,1,1,1,0);
        ManagerTurno managerTurno = new ManagerTurno(List.of(jugadorMarcos),tablero,new Random(),new MazoOculto(new Random()));
        tablero.construirPoblado(jugadorMarcos,new Coordenada(10,3));
        jugadorMarcos.tiene(0,0,0,0,0);
        tablero.construirPoblado(jugadorMarcos,new Coordenada(2,3));
        Dados dadosCargados = Mockito.mock(Dados.class);
        Mockito.when(dadosCargados.tirar()).thenReturn(10);
        jugadorMarcos.tiene(0,0,0,0,0);
        tablero.tirarDados(dadosCargados);
        jugadorMarcos.tiene(0,0,0,0,0);
        assert (jugadorMarcos.tiene(0,0,0,0,0));

    }

}

//package edu.fiuba.algo3.entrega_1;
//
//import edu.fiuba.algo3.modelo.tablero.Tablero;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//public class CasoDeUsoLanzarDadosTest {
//
//    @Test
//    public void test() {
//        var unTablero = new Tablero();
//
//        CasoDeUsoLanzarDados caso = new CasoDeUsoLanzarDados(unTablero);
//
//        List<Dividendos> conjuntoDividendos = caso.lanzar( new DadoCargado( 10 /* valor que entregara el dado */) );
//
//        // Van a necesitar configurar un tablero en un estado que les permita saber el resultado del caso de uso lanzar.
//        // Nota:
//        //  Para configurar el tablero van a necesitar utilizar las acciones creadas anteriormente.
//
//    }
//}

//List<Terreno> hexagonos = Arrays.asList(
//        new Bosque(),
//        new Campo(),
//        new Bosque(),
//        new Pastizal(),
//        new Bosque(),
//        new Campo(),
//        new Montania(),
//        new Campo(),
//        new Montania(),
//        new Campo(),
//        new Colina(),
//        new Colina(),
//        new Desierto(),
//        new Colina(),
//        new Pastizal(),
//        new Montania(),
//        new Pastizal(),
//        new Bosque(),
//        new Pastizal()
//);
//
//List<Produccion> fichasNumeradas = new LinkedList<>(Arrays.asList(
//        new Produccion(2),
//        new Produccion(3),
//        new Produccion(3),
//        new Produccion(4),
//        new Produccion(4),
//        new Produccion(5),
//        new Produccion(5),
//        new Produccion(6),
//        new Produccion(6),
//        new Produccion(8),
//        new Produccion(8),
//        new Produccion(9),
//        new Produccion(9),
//        new Produccion(10),
//        new Produccion(10),
//        new Produccion(11),
//        new Produccion(11),
//        new Produccion(12)
//
//));
//
//CasoDeUsoArmarTablero caso = new CasoDeUsoArmarTablero(hexagonos, fichasNumeradas);
//
//var tablero = caso.crearTablero();
//var tableroEsperado = TableroFactory.crear(hexagonos, fichasNumeradas);