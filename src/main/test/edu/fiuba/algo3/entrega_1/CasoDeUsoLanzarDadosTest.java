package edu.fiuba.algo3.entrega_1;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ManagerTurno;
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

import java.util.*;

import static org.junit.Assert.assertTrue;

public class CasoDeUsoLanzarDadosTest {

    @Test
    public void testCasoDeUsoLanzarDados() throws ConstruccionExistenteException, ReglaDistanciaException {
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
        Jugador jugadorMarcos = new Jugador("Marcos",new Color("rojo"));

        jugadorMarcos.agregarRecurso(new Madera(2));
        jugadorMarcos.agregarRecurso(new Ladrillo(2));
        jugadorMarcos.agregarRecurso(new Lana(2));
        jugadorMarcos.agregarRecurso(new Grano(2));

        ManagerTurno managerTurno = new ManagerTurno(List.of(jugadorMarcos),tablero,new Random());
        managerTurno.construirPoblado(new Coordenada(10,3));
        managerTurno.construirPoblado(new Coordenada(2,3));


        Dados dadosCargados = Mockito.mock(Dados.class);
        Mockito.when(dadosCargados.tirar()).thenReturn(10);
        //Act
        tablero.tirarDados(dadosCargados);
        //Assert
        assertTrue(jugadorMarcos.tiene(new Madera(2),new Ladrillo(0),new Lana(0),new Mineral(0),new Grano(0)));

    }

}

