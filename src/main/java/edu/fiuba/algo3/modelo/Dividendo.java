package edu.fiuba.algo3.modelo;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Dividendo {
    private final Color color;
    private final Map<Recurso, Integer> recursos = new HashMap<>(); // usa tu enum

    public Dividendo(Color color, RecursoBase... recursosEntregables) {
        this.color = color;
        if(recursosEntregables != null){
            for (RecursoBase r : recursosEntregables) {
                agregar(r);
            }
        }
    }



    public void agregar(RecursoBase r) {
        recursos.put(
                r.tipo(),
                recursos.getOrDefault(r.tipo(), 0) + r.cantidad()
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

}
