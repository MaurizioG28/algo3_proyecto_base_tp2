package edu.fiuba.algo3.modelo.Tablero;

import java.util.*;

import org.apache.commons.lang3.ArrayUtils;

public class Tablero {

    private static final int CANTIDAD_HEXAGONOS = 19;
    private final Map<TipoTerreno, Terreno> terrenos = Map.of(
            TipoTerreno.BOSQUE, new Bosque(),
            TipoTerreno.COLINA, new Colina(),
            TipoTerreno.PRADERA, new Pastizal(),
            TipoTerreno.CAMPO, new Campo(),
            TipoTerreno.MONTANIA, new Montania(),
            TipoTerreno.DESIERTO, new Desierto()
        );

    boolean tableroInicializado = false;
    //la distribucion de fichas numeradas son las siguientes: un 2 y un 12, luego dos de cada una entre 3 y 11, el 7 está excluido
    private int[] fichasNumeradas = {2,3,3,4,4,5,5,6,6,8,8,9,9,10,10,11,11,12};


    private final ArrayList<Hexagono> hexagonos = new ArrayList<>();

    public Tablero(){
    }

    public void setUp(){
        while(hexagonos.size() < CANTIDAD_HEXAGONOS){
            Terreno terreno = sortearTerreno();

            terreno.agregarTerreno(hexagonos, fichasNumeradas);
        }
        this.tableroInicializado = true;
    }

    private Terreno sortearTerreno(){
        Random rand = new Random();
        int numeroAleatorio = rand.nextInt(terrenos.size());
        TipoTerreno tipoSorteado = (TipoTerreno) terrenos.keySet().toArray()[numeroAleatorio];

        return terrenos.get(tipoSorteado);
    }

    public boolean tableroCorrectamenteInicializado(){
        boolean correcto = true;
        Collection<Terreno> todosTerrenos = terrenos.values();
        for (Terreno terreno : todosTerrenos) {
            if (!terreno.todosColocados()) {
                correcto = false;
            }
        }

        return correcto;
    }
}
