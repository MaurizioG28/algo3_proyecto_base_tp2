package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.Contruccion.TipoConstruccion;
import edu.fiuba.algo3.modelo.Jugador;

import java.util.ArrayList;

public class Vertice {

    private Jugador propietario;
    private ArrayList<Vertice> adyacentes = new ArrayList<>();
    private TipoConstruccion tipo;

    public boolean tieneConstruccion() {
        return propietario != null;
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
