package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import java.util.*;

import java.util.*;

import java.util.*;

import java.util.*;

public class AlmacenDeRecursos {

    // 1 entrada por tipo: Madera, Grano, etc.
    private final Map<TipoDeRecurso, TipoDeRecurso> recursos = new HashMap<>();
    private final Random azar;

    public AlmacenDeRecursos() {
        this(new Random());
    }

    public AlmacenDeRecursos(Random azar) {
        this.azar = Objects.requireNonNull(azar);
    }

    /** Normaliza: siempre usamos tipo.nuevo(0) como "representante" de la clave. */
    private TipoDeRecurso claveDe(TipoDeRecurso r) {
        return r.nuevo(0);
    }

    /** El recurso trae la cantidad: new Madera(4) suma 4 al almacén. */
    public void agregarRecurso(TipoDeRecurso recurso) {
        if (recurso == null) throw new IllegalArgumentException("Recurso no puede ser null");
        if (recurso.obtenerCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser > 0");
        }

        TipoDeRecurso clave = claveDe(recurso);
        recursos.merge(
                clave,
                recurso,                         // si no existe, guardo este mismo objeto
                (actual, nuevo) -> {             // si existe, acumulo sobre el existente
                    actual.sumar(nuevo.obtenerCantidad());
                    return actual;
                }
        );
    }

    public int cantidadDe(TipoDeRecurso tipo) {
        if (tipo == null) throw new IllegalArgumentException("Recurso no puede ser null");
        TipoDeRecurso clave = claveDe(tipo);
        TipoDeRecurso r = recursos.get(clave);
        return (r == null) ? 0 : r.obtenerCantidad();
    }

    public boolean quitar(TipoDeRecurso tipo, int cantidad) {
        if (tipo == null) throw new IllegalArgumentException("Recurso no puede ser null");
        if (cantidad <= 0) return true;

        TipoDeRecurso clave = claveDe(tipo);
        TipoDeRecurso r = recursos.get(clave);
        if (r == null || r.obtenerCantidad() < cantidad) return false;

        r.restar(cantidad);
        if (r.obtenerCantidad() == 0) {
            recursos.remove(clave);
        }
        return true;
    }

    public boolean quitarUno(TipoDeRecurso tipo) {
        return quitar(tipo, 1);
    }

    public int totalRecursos() {
        int total = 0;
        for (TipoDeRecurso r : recursos.values()) {
            total += r.obtenerCantidad();
        }
        return total;
    }

    public boolean estaVacio() {
        return recursos.isEmpty();
    }

    private TipoDeRecurso recursoAleatorio() {
        int total = totalRecursos();
        if (total == 0) return null;
        int k = azar.nextInt(total); // [0,total)

        for (Map.Entry<TipoDeRecurso, TipoDeRecurso> e : recursos.entrySet()) {
            int c = e.getValue().obtenerCantidad();
            if (k < c) return e.getKey();
            k -= c;
        }
        return null;
    }

    /** Roba 1 recurso al azar (ponderado) y lo quita del almacén. */
    public TipoDeRecurso robarRecursoAleatorio() {
        TipoDeRecurso tipo = recursoAleatorio();
        if (tipo == null) return null;
        quitar(tipo, 1);
        return tipo.nuevo(1);
    }

    /** Descarta floor(total/2) recursos al azar. Devuelve lo descartado por tipo. */
    public Map<TipoDeRecurso, Integer> descartarPorReglaDelSiete() {
        int total = totalRecursos();
        if (total <= 7) return Collections.emptyMap();

        int aDescartar = total / 2;
        Map<TipoDeRecurso, Integer> descartados = new HashMap<>();

        for (int i = 0; i < aDescartar; i++) {
            TipoDeRecurso r = robarRecursoAleatorio();
            if (r == null) break;
            TipoDeRecurso clave = claveDe(r);
            descartados.merge(clave, 1, Integer::sum);
        }
        return descartados;
    }
}




