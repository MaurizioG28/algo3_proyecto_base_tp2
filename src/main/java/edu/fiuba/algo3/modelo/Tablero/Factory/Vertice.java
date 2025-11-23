package edu.fiuba.algo3.modelo.Tablero.Factory;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.Contruccion.Ciudad;
import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Vertice implements IVertice {

    private Jugador propietario;
    private ArrayList<Vertice> adyacentes = new ArrayList<>();
    private Construccion tipo = null;
    //private List<ICelda> celdasAdyacentes;

    public boolean tieneConstruccion() {
        return (propietario != null || tipo != null);
    }

//    @Override
//    public List<ICelda> obtenerCeldasAdyacentes() {
//        return celdasAdyacentes;
//    }
//    @Override
//    public List<Recurso> darRecursos() {
//        List<Recurso> recursos = new LinkedList<>();
//        for (ICelda celda : celdasAdyacentes) {
//            recursos.add(celda.darRecurso());
//        }
//        return recursos;
//    }

    public boolean tieneConstruccionAdyacente() {
        for (Vertice v : adyacentes) {
            if (v.tieneConstruccion()) return true;
        }
        return false;
    }

    public void colocarPoblado(Jugador jugador) {
        this.propietario = jugador;
        this.tipo = new Poblado(new Color("negro"));
    }
    public void mejorarACiudad() {
        if (this.propietario == null) throw new IllegalStateException("No hay poblado para mejorar");
        String color =this.tipo.getColor();
        this.tipo = new Ciudad(new Color(color));
    }
    public Jugador getPropietario() { return propietario; }
    public Construccion getTipoConstruccion() { return tipo; }

    public void agregarAdyacente(Vertice v2) {
        this.adyacentes.add(v2);
    }

    public boolean esVerticeAdyacente(Vertice otroVertice){
        return this.adyacentes.contains(otroVertice);
    }

    public void colocar(Construccion pieza) throws ConstruccionExistenteException {
        if (this.tipo != null) {
            throw new ConstruccionExistenteException("El vértice ya tiene una construcción.");
        }
        this.tipo = pieza;

    }

//    public int obtenerFactorProduccion() {
//        return this.tipo.obtenerFactorProduccion();
//    }
}
