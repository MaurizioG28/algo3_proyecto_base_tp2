package edu.fiuba.algo3.modelo;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import java.util.*;

public class AlmacenDeRecursos {

    private final EnumMap<Recurso, Integer> cantidades = new EnumMap<>(Recurso.class);
    private final Random azar;

    public AlmacenDeRecursos() {
        this(new Random());
    }

    public AlmacenDeRecursos(Random azar) {
        this.azar = Objects.requireNonNull(azar, "El generador de azar no puede ser null");
        for (Recurso r : Recurso.values()) {
            cantidades.put(r, 0);  // evita nulls
        }
    }

    private Recurso recursoAleatorio() {
        int total = totalRecursos();
        if (total == 0) return null;
        int k = azar.nextInt(total); // [0,total)
        for (Recurso r : Recurso.values()) {
            int c = cantidades.get(r);
            if (k < c) return r;
            k -= c;
        }
        return null; // no debería pasar
    }

    public void agregarRecurso(Recurso recurso, int cantidad) {
        if (recurso == null) throw new IllegalArgumentException("Recurso no puede ser null");
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser > 0");
        cantidades.put(recurso, cantidades.get(recurso) + cantidad);
    }

    public void agregarTodos(Map<Recurso, Integer> paquete) {
        if (paquete == null) throw new IllegalArgumentException("Paquete no puede ser null");
        paquete.forEach(this::agregarRecurso);
    }

    public int cantidadDe(Recurso recurso) {
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

    public boolean quitar(Recurso recurso, int cantidad) {
        if (recurso == null) throw new IllegalArgumentException("Recurso no puede ser null");
        if (cantidad <= 0) return true;
        int actual = cantidades.get(recurso);
        if (actual < cantidad) return false;
        cantidades.put(recurso, actual - cantidad);
        return true;
    }

    public boolean quitarUno(Recurso recurso) {
        return quitar(recurso, 1);
    }

    /** Roba 1 recurso al azar (ponderado) y lo quita del almacén. */
    public Recurso robarRecursoAleatorio() {
        Recurso r = recursoAleatorio();
        if (r != null) quitarUno(r);
        return r;
    }

    /** Descarta floor(total/2) recursos al azar. Devuelve lo descartado por tipo. */
    public Map<Recurso, Integer> descartarPorReglaDelSiete() {
        int total = totalRecursos();
        if (total <= 7) return Collections.emptyMap();

        int aDescartar = total / 2; // redondeo hacia abajo
        EnumMap<Recurso, Integer> descartados = new EnumMap<>(Recurso.class);
        for (Recurso r : Recurso.values()) descartados.put(r, 0);

        for (int i = 0; i < aDescartar; i++) {
            Recurso r = robarRecursoAleatorio(); // ya quita 1 del almacén
            if (r == null) break;
            descartados.put(r, descartados.get(r) + 1);
        }
        return descartados;
    }


}
