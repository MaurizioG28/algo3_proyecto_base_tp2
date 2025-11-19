package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import java.util.*;

public class AlmacenDeRecursos {

    private final Map<TipoDeRecurso, Integer> cantidades = new HashMap<>();
    private final Random azar;

    public AlmacenDeRecursos() {
        this(new Random());
    }

    public AlmacenDeRecursos(Random azar) {
        this.azar = Objects.requireNonNull(azar, "El generador de azar no puede ser null");
    }

    private TipoDeRecurso  recursoAleatorio() {
        int total = totalRecursos();
        if (total == 0) return null;
        int k = azar.nextInt(total); // [0,total)
        for (Map.Entry<TipoDeRecurso, Integer> e : cantidades.entrySet()) {
            int c = e.getValue();
            if (k < c) return e.getKey();
            k -= c;
        }
        return null; // no debería pasar
    }

    public void agregarRecurso(TipoDeRecurso recurso, int cantidad) {
        if (recurso == null) throw new IllegalArgumentException("Recurso no puede ser null");
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser > 0");
        cantidades.merge(recurso, cantidad, Integer::sum);
    }

    public void agregarTodos(Map<TipoDeRecurso, Integer> paquete) {
        if (paquete == null) throw new IllegalArgumentException("Paquete no puede ser null");
        paquete.forEach(this::agregarRecurso);
    }

    public int cantidadDe(TipoDeRecurso recurso) {
        if (recurso == null) throw new IllegalArgumentException("Recurso no puede ser null");
        return cantidades.getOrDefault(recurso, 0);
    }

    public int totalRecursos() {
        int total = 0;
        for (int v : cantidades.values()) total += v;
        return total;
    }

    public boolean estaVacio() {
        return totalRecursos() == 0;
    }

    public boolean quitar(TipoDeRecurso recurso, int cantidad) {
        if (recurso == null) throw new IllegalArgumentException("Recurso no puede ser null");
        if (cantidad <= 0) return true;
        int actual = cantidades.get(recurso);
        if (actual < cantidad) return false;
        cantidades.put(recurso, actual - cantidad);
        return true;
    }

    public boolean quitarUno(TipoDeRecurso recurso) {
        return quitar(recurso, 1);
    }

    /** Roba 1 recurso al azar (ponderado) y lo quita del almacén. */
    public TipoDeRecurso robarRecursoAleatorio() {
        TipoDeRecurso r = recursoAleatorio();
        if (r != null) quitarUno(r);
        return r;
    }

    /** Descarta floor(total/2) recursos al azar. Devuelve lo descartado por tipo. */
    public Map<TipoDeRecurso, Integer> descartarPorReglaDelSiete() {
        int total = totalRecursos();
        if (total <= 7) return Collections.emptyMap();

        int aDescartar = total / 2; // floor
        Map<TipoDeRecurso, Integer> descartados = new HashMap<>();

        for (int i = 0; i < aDescartar; i++) {
            TipoDeRecurso r = robarRecursoAleatorio();
            if (r == null) break;
            descartados.merge(r, 1, Integer::sum);
        }
        return descartados;
    }
}


