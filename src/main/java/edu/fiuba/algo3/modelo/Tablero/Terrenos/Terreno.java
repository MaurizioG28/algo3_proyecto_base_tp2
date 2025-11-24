package edu.fiuba.algo3.modelo.Tablero.Terrenos;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.Tablero.Factory.*;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class Terreno {

    protected String tipoTerreno;
    protected Produccion produccion;
    protected Hexagono hexagono;
    protected Axial posicion;
    protected int id;


    public String getTipoTerreno(){
        return this.getClass().getSimpleName();
    }
    public abstract TipoDeRecurso recursoOtorgado(Integer cantidad);



    public void setProduccion(Produccion produccion) {
        this.produccion = produccion;

    }

    public boolean esDesierto() {
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if(this.getClass() != object.getClass()){return false;}
        return mismaProduccion(((Terreno) object).getProduccion());
    }

    private Produccion getProduccion() {
        return this.produccion;
    }

    public boolean mismaProduccion(Produccion produccion){
        return  this.produccion.equals(produccion);
    }

    public void asignarHexagono(Hexagono hexagono) {
        this.hexagono = hexagono;
    }

    public Hexagono getHexagono() {
        return this.hexagono;
    }



    public void setPosicion(Axial axial) {
        this.posicion = axial;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void agregarVertices(Map<Coordenada, Vertice> verticesPorCoordenada) {
        for (int i = 0; i < 6; i++) {
            Coordenada coord = new Coordenada(this.id, i);
            Vertice vertice = verticesPorCoordenada.get(coord);
            if (vertice != null) {
                this.hexagono.agregarVertice(vertice);
            }
        }
    }

    public void agregarLados(Map<Coordenada, Lado> ladosPorCoordenada) {
        for (int i = 0; i < 6; i++) {
            Coordenada coord = new Coordenada(this.id, i);
            Lado lado = ladosPorCoordenada.get(coord);
            if (lado != null) {
                this.hexagono.agregarLado(lado);
            }
        }
    }

    public boolean tieneVertice(Vertice v) {
        return hexagono.tieneVertice(v);
    }

    public boolean sePuedeProducir() {
        return hexagono.sePuedeProducir();
    }

    public Axial getPosicion() {
        return this.posicion;
    }

    public int getId() {
        return this.id;
    }


    public void moverLadron() {
        this.hexagono.moverLadron();
    }

    public void moverLadronQuitar() {
        this.hexagono.sacarLadron();
    }

    public void moverLadronPoner() {
        this.hexagono.ponerLadron();
    }

    public List<Color> jugadoresAfectadosPorElLadron(Jugador jugadorActual) {
        return this.hexagono.jugadoresAfectadosPorElLadron(jugadorActual);
    }
}
