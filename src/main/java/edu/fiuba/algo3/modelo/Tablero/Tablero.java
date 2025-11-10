package edu.fiuba.algo3.modelo.Tablero;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;

public class Tablero {

    private static final int CANTIDAD_HEXAGONOS = 19;
    private static final int CANTIDAD_TERRENOS = 6;
    private static final int CANTIDAD_MAXIMA_PASTIZALES = 4;
    private static final int CANTIDAD_MAXIMA_BOSQUES = 4;
    private static final int CANTIDAD_MAXIMA_COLINAS = 3;
    private static final int CANTIDAD_MAXIMA_CAMPOS = 4;
    private static final int CANTIDAD_MAXIMA_MONTANIAS = 3;
    private static final int CANTIDAD_MAXIMA_DESIERTOS = 1;
    private Map<Integer, TipoTerreno> asignacionPorPosicion = Map.of(
        0, TipoTerreno.BOSQUE,
        1, TipoTerreno.COLINA,
        2, TipoTerreno.PRADERA,
        3, TipoTerreno.CAMPO,
        4, TipoTerreno.MONTANIA,
        5, TipoTerreno.DESIERTO
    );

    boolean tableroInicializado = false;
    //la distribucion de fichas numeradas son las siguientes: un 2 y un 12, luego dos de cada una entre 3 y 11, el 7 está excluido
    private Integer[] fichasNumeradas = {2,3,3,4,4,5,5,6,6,8,8,9,9,10,10,11,11,12};

    private int bosquesColocados = 0;
    private int colinasColocados = 0;
    private int pastizalesColocados = 0;
    private int camposColocados = 0;
    private int montaniasColocados = 0;
    private int desiertosColocados = 0;
    private int hexagonosAsignados = 0;

    private final ArrayList<Hexagono> hexagonos = new ArrayList<>();

    public Tablero(){
    }

    public void setUp(){
        while(hexagonosAsignados != CANTIDAD_HEXAGONOS){
            Random rand = new Random();
            int numeroAleatorio = rand.nextInt(CANTIDAD_TERRENOS);
            var terreno = asignacionPorPosicion.get(numeroAleatorio);
            asignarTerreno(terreno);
        }
        this.tableroInicializado = true;
    }

    private void asignarTerreno(TipoTerreno terreno) {
        if(terrenoAlMaximo(terreno)){
            return;
        }

        if(terreno == TipoTerreno.BOSQUE){bosquesColocados += 1;}
        if(terreno == TipoTerreno.COLINA){colinasColocados += 1;}
        if(terreno == TipoTerreno.PRADERA){pastizalesColocados += 1;}
        if(terreno == TipoTerreno.CAMPO ){camposColocados += 1;}
        if(terreno == TipoTerreno.MONTANIA){montaniasColocados += 1;}
        if(terreno == TipoTerreno.DESIERTO){desiertosColocados += 1;}


        //si el terreno es el desierto no hace falta sacar una ficha numerada
        int fichaSorteada = 0;
        if(terreno != TipoTerreno.DESIERTO){
            Random rand = new Random();
            int numeroAleatorio = rand.nextInt(fichasNumeradas.length);
            fichaSorteada = fichasNumeradas[numeroAleatorio];
            fichasNumeradas = ArrayUtils.remove(fichasNumeradas, numeroAleatorio);
        }

        Hexagono hexagonoAColocar = new Hexagono(terreno, fichaSorteada);
        hexagonos.add(hexagonoAColocar);
        hexagonosAsignados += 1;
    }

    private boolean terrenoAlMaximo(TipoTerreno terreno){
        return (terreno == TipoTerreno.BOSQUE && bosquesColocados == CANTIDAD_MAXIMA_BOSQUES) ||
                (terreno == TipoTerreno.COLINA && colinasColocados == CANTIDAD_MAXIMA_COLINAS) ||
                (terreno == TipoTerreno.PRADERA && pastizalesColocados == CANTIDAD_MAXIMA_PASTIZALES) ||
                (terreno == TipoTerreno.CAMPO && camposColocados == CANTIDAD_MAXIMA_CAMPOS) ||
                (terreno == TipoTerreno.MONTANIA && montaniasColocados == CANTIDAD_MAXIMA_MONTANIAS) ||
                (terreno == TipoTerreno.DESIERTO && desiertosColocados ==  CANTIDAD_MAXIMA_DESIERTOS);
    }

    public boolean tableroCorrectamenteInicializado(){
        boolean correcto = true;
        if(!tableroInicializado){correcto = false;}
        if(bosquesColocados != CANTIDAD_MAXIMA_BOSQUES){correcto = false;}
        if(colinasColocados != CANTIDAD_MAXIMA_COLINAS){correcto = false;}
        if(pastizalesColocados != CANTIDAD_MAXIMA_PASTIZALES){correcto = false;}
        if(camposColocados != CANTIDAD_MAXIMA_CAMPOS){correcto = false;}
        if(montaniasColocados != CANTIDAD_MAXIMA_MONTANIAS){correcto = false;}
        if(desiertosColocados != CANTIDAD_MAXIMA_DESIERTOS){correcto = false;}

        return correcto;
    }
}
