package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Recursos.*;
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
        for (TipoDeRecurso tipo : tiposBase()) {
            recursos.put(tipo, tipo.nuevo(0));
        }
    }
    private List<TipoDeRecurso> tiposBase() {
        return List.of(
                new Madera(0),
                new Grano(0),
                new Ladrillo(0),
                new Lana(0),
                new Mineral(0)
        );
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
    public boolean tiene(Map<TipoDeRecurso, Integer> requeridos) {
        if (requeridos == null) {
            throw new IllegalArgumentException("La lista de requeridos no puede ser null");
        }

        for (Map.Entry<TipoDeRecurso, Integer> entry : requeridos.entrySet()) {

            TipoDeRecurso tipo = entry.getKey();
            int cantidadRequerida = entry.getValue();

            if (cantidadRequerida <= 0) {
                continue; // no es necesario tener este recurso
            }

            // Normaliza tipo → new Tipo(0)
            int disponible = cantidadDe(tipo);

            if (disponible < cantidadRequerida) {
                throw new IllegalArgumentException("El jugador no tiene la suficiente cantidad de Recursos");
            }
        }
        System.out.println(this.toString());

        return true;
    }
    public boolean tieneExactamente(Map<TipoDeRecurso, Integer> requeridos) {

        if (requeridos == null) {
            throw new IllegalArgumentException("La lista de requeridos no puede ser null");
        }

        // 1) Verificar que cada recurso requerido coincide EXACTAMENTE
        for (Map.Entry<TipoDeRecurso, Integer> entry : requeridos.entrySet()) {

            TipoDeRecurso tipo = entry.getKey().nuevo(0); // normalización
            int cantidadRequerida = entry.getValue();
            int disponible = cantidadDe(tipo);

            if (disponible != cantidadRequerida) {
                return false; // falta o sobra
            }
        }

        // 2) Verificar que NO tiene recursos extra
        for (Map.Entry<TipoDeRecurso, TipoDeRecurso> entry : recursos.entrySet()) {

            TipoDeRecurso tipo = entry.getKey();     // Madera(0), Grano(0), etc.
            int cantidadDisponible = entry.getValue().obtenerCantidad();
            int cantidadRequerida = requeridos.getOrDefault(tipo, 0);

            if (cantidadDisponible != cantidadRequerida) {
                return false;
            }
        }

        return true;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Almacén de Recursos:\n");

        recursos.forEach((clave, recurso) -> {
            sb.append(" - ")
                    .append(recurso.nombre())
                    .append(": ")
                    .append(recurso.obtenerCantidad())
                    .append("\n");
        });

        return sb.toString();
    }


    public boolean tieneSuficiente(TipoDeRecurso recursoRequerido) {
        if (recursoRequerido == null) {
            throw new IllegalArgumentException("El recurso requerido no puede ser null");
        }
        int cantidadDisponible = this.cantidadDe(recursoRequerido);

        return cantidadDisponible >= recursoRequerido.obtenerCantidad();
    }


    public int entregarTodo(TipoDeRecurso tipo) {
        //Para Carta Monopolio
        int cantidad = this.cantidadDe(tipo);

        if (cantidad > 0) {
            this.quitar(tipo, cantidad);
        }
        return cantidad;
    }
}




