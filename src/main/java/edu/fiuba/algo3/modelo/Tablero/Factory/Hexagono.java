package edu.fiuba.algo3.modelo.Tablero.Factory;
import edu.fiuba.algo3.modelo.Contruccion.Contruccion;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;


import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Hexagono {
    //private final Terreno tipo;


    private boolean bloqueadoPorLadron = false;
    private final List<Vertice> vertices = new ArrayList<>(6);
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
    public boolean isBloqueadoPorLadron() {
        return (bloqueadoPorLadron);
    }
    //public Terreno  getTipo() { return tipo; }


    public List<Vertice> getVertices() { return vertices; }

    public void agregarVertice(Vertice v) { this.vertices.add(v); }
    public void agregarLado(Lado lado) { this.lados.add(lado); }

//    public void producirRecursoAContrucciones() {
//        for (Vertice v : vertices) {
//            List<TipoDeRecurso> recursos = v.;
//        }
//    }
}
