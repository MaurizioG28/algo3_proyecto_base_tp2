package edu.fiuba.algo3.modelo.Tablero.Factory;


import edu.fiuba.algo3.modelo.Dividendo;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.Color;

import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;

import java.util.*;


public class Hexagono {
    //private final Terreno tipo;


    private boolean bloqueadoPorLadron = false;
    private final List<Vertice> vertices = new ArrayList<>(6);
    //private final Vertice[] vertices = new Vertice[6];
    private final List<Lado> lados = new ArrayList<>(6);

    public Hexagono() {
        //this.tipo = tipo;


    }

    public void moverLadron() {
        bloqueadoPorLadron = !bloqueadoPorLadron;
    }



    public boolean sePuedeProducir() {
        return (!bloqueadoPorLadron);
    }



    public List<Vertice> getVertices() { return (vertices); }

    public void agregarVertice( Vertice v) { vertices.add(v) ; }
    public void agregarLado(Lado lado) {
        if (lados.size() < 6) {
            lados.add(lado);
        }
    }



    public Lado getLado(int indice) {
        return lados.get(indice);
    }





    public boolean tieneVertice(Vertice v) {
        return vertices.contains(v);
    }

    public List<Dividendo> activarVertices(Terreno terrenoOrigen) {
        if (bloqueadoPorLadron) return null;
        List<Dividendo> listaDividendos = new ArrayList<>();

        for (Vertice v : vertices) {
            Dividendo dividendo = v.cosechar(terrenoOrigen);
            
            if (dividendo != null)
             listaDividendos.add( dividendo); // Delega al vértice
        }
        return listaDividendos;
    }

//    public void producirRecurso(TipoDeRecurso recurso) {
//        if (sePuedeProducir()) {
//            List<Jugador> jugadoresLocales = new ArrayList<>();
//            for (Vertice v : vertices) {
//                if (!v.tieneConstruccion()) continue;
//                Jugador propietario = v.getPropietario();
//                if (propietario == null) continue;
//                for (int i = 0; i <= v.factorProduccion() - 1; i++) {
//                    propietario.agregarRecurso(recurso);
//                }
//            }
//        }
//    }
    public void sacarLadron() {
        this.bloqueadoPorLadron = false;
    }

    public void ponerLadron() {
        this.bloqueadoPorLadron = true;
    }

    public List<Color> jugadoresAfectadosPorElLadron(Jugador jugadorActual) {
        List<Color> victimas = new ArrayList<>();

        for (Vertice v : vertices) {
            if (!v.tieneConstruccion()) continue;

            Color propietario = v.colorDeConstruccion();
            if (propietario == null) continue;
            if (propietario.equals(jugadorActual.getColor())) continue;

            if (!victimas.contains(propietario))
                victimas.add(propietario);
        }

        return victimas;
    }

//    public void producirRecursoAContrucciones() {
//        for (Vertice v : vertices) {
//            List<TipoDeRecurso> recursos = v.;
//        }
//    }
}
