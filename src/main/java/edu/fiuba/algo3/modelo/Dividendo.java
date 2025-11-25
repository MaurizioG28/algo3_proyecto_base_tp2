package edu.fiuba.algo3.modelo;


import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Dividendo {
    private final Color color;
    private final Map<TipoDeRecurso, Integer> recursos = new HashMap<>(); // usa tu enum

    public Dividendo(Color color, TipoDeRecurso... recursosEntregables) {
        this.color = color;
        if(recursosEntregables != null){
            for (TipoDeRecurso r : recursosEntregables) {
                agregar(r);
            }
        }
    }



    public void agregar(TipoDeRecurso r) {
        recursos.put(
                r,
                r.obtenerCantidad()
        );
    }

    public static Dividendo vacio() {
        return null;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dividendo)) return false;
        Dividendo d = (Dividendo) o;
        return Objects.equals(color, d.color) &&
                Objects.equals(recursos, d.recursos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, recursos);
    }

    public Color getColor() {
        return this.color;
    }

    public List<TipoDeRecurso> getRecursos() {
        return List.copyOf(this.recursos.keySet());
    }
}
