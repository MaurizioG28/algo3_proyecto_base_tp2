package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import java.util.*;

import static edu.fiuba.algo3.modelo.Recurso.*;

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

    public void removerRecurso(Recurso recurso, int cantidad) {
        if (recurso == null) throw new IllegalArgumentException("Recurso no puede ser null");
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser < 0");
        if (cantidades.get(recurso) < cantidad) throw new CantidadIncorrectaException("La cantidad a sacar es mayor a la cantidad que se tiene.");
        cantidades.put(recurso, cantidades.get(recurso) - cantidad);
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

    // maderaReq, ladrilloReq, lanaReq, cerealReq, mineralReq
    public boolean tiene(int madera, int ladrillo, int lana, int grano, int mineral) {
        return  cantidades.getOrDefault(MADERA, 0)   >= madera   &&
                cantidades.getOrDefault(LADRILLO, 0) >= ladrillo &&
                cantidades.getOrDefault(LANA, 0)     >= lana     &&
                cantidades.getOrDefault(GRANO, 0)   >= grano   &&
                cantidades.getOrDefault(MINERAL, 0)  >= mineral;
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


    public Construccion comprarPoblado(Color color) {
        if(cantidadDe(Recurso.MADERA) >= 1 &&
            cantidadDe(Recurso.LADRILLO) >= 1 &&
            cantidadDe(Recurso.LANA) >= 1 &&
            cantidadDe(Recurso.GRANO) >= 1){

            quitarUno(Recurso.MADERA);
            quitarUno(Recurso.LADRILLO);
            quitarUno(Recurso.LANA);
            quitarUno(Recurso.GRANO);

            return new Poblado(color);
        }
        return null;
    }
}
