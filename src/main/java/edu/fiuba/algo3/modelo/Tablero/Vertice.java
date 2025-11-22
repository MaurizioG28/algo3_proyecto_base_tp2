package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.Contruccion.TipoConstruccion;
import edu.fiuba.algo3.modelo.ICelda;
import edu.fiuba.algo3.modelo.IVertice;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Vertice implements IVertice {

    private Jugador propietario;
    private ArrayList<Vertice> adyacentes = new ArrayList<>();
    private TipoConstruccion tipo;
    private List<ICelda> celdasAdyacentes;

    public boolean tieneConstruccion() {
        return propietario != null;
    }

    @Override
    public List<ICelda> obtenerCeldasAdyacentes() {
        return celdasAdyacentes;
    }
    @Override
    public List<TipoDeRecurso> darRecursos() {
        List<TipoDeRecurso> recursos = new LinkedList<>();
        for (ICelda celda : celdasAdyacentes) {
            recursos.add(celda.darRecurso());
        }
        return recursos;
    }

    public boolean tieneConstruccionAdyacente() {
        for (Vertice v : adyacentes) {
            if (v.tieneConstruccion()) return true;
        }
        return false;
    }

    public void colocarPoblado(Jugador jugador) {
        this.propietario = jugador;
        this.tipo = TipoConstruccion.POBLADO;
    }
    public void mejorarACiudad() {
        if (this.propietario == null) throw new IllegalStateException("No hay poblado para mejorar");
        this.tipo = TipoConstruccion.CIUDAD;
    }
    public Jugador getPropietario() { return propietario; }
    public TipoConstruccion getTipoConstruccion() { return tipo; }

    public void agregarAdyacente(Vertice v2) {
        this.adyacentes.add(v2);
    }
}
