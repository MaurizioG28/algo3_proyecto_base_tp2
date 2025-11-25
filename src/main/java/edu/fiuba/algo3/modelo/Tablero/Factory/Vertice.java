package edu.fiuba.algo3.modelo.Tablero.Factory;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.Contruccion.Ciudad;
import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Contruccion.Productor;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;

import java.util.ArrayList;

public class Vertice{

    private Color propietario;
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


    public void mejorarACiudad() {
        if (this.propietario == null) throw new IllegalStateException("No hay poblado para mejorar");
        if (!(this.tipo instanceof Poblado)) {
            throw new IllegalStateException("Solo se pueden mejorar Poblados.");
        }
        String color =this.tipo.getColor();
        this.tipo = new Ciudad(new Color(color));
    }
    public Color getPropietario() { return propietario; }
    public Construccion getTipoConstruccion() { return tipo; }

    public void agregarAdyacente(Vertice vertice) {
        if (vertice == this) return; // No auto-adyacencia
        if (adyacentes.contains(vertice)) return; // Ya existe
        if (adyacentes.size() >= 3) {
            throw new IllegalStateException("Vértice no puede tener más de 3 adyacentes");
        }

        adyacentes.add(vertice);
        // Conexión bidireccional OPcional - si la quitas, quita esta parte
        if (!vertice.esVerticeAdyacente(this)) {
            vertice.agregarAdyacente(this);
        }
    }

    public boolean esVerticeAdyacente(Vertice otroVertice){
        return this.adyacentes.contains(otroVertice);
    }

    public void colocar(Construccion pieza) throws ConstruccionExistenteException {
        if (this.tipo != null) {
            throw new ConstruccionExistenteException("El vértice ya tiene una construcción.");
        }
        this.tipo = pieza;
        this.propietario=pieza.getColorActual();

    }

    public int factorProduccion() {
        if (tipo instanceof Productor) {
            return ((Productor) tipo).obtenerFactorProduccion();
        }
        return 0;
    }

    public Color colorDeConstruccion() {
        if (this.tipo != null) {
            return this.tipo.getColorActual();
        }
        return null;
    }

//    public int obtenerFactorProduccion() {
//        return this.tipo.obtenerFactorProduccion();
//    }
    public Dividendo cosechar(Terreno terrenoOrigen) {
        // Si no hay dueño, no hacemos nada
        if (this.propietario == null || this.tipo == null) return null;

        // Esto devuelve 1 si es Poblado, 2 si es Ciudad
        int cantidad = this.factorProduccion();

        if (cantidad > 0) {
            Dividendo dividendo = new Dividendo(this.propietario);
            // El terreno crea los recursos (Madera, Grano, etc.)
            TipoDeRecurso recurso = terrenoOrigen.recursoOtorgado(cantidad);
            dividendo.agregar(recurso);
            return dividendo;
        }
        return null;
    }
}
