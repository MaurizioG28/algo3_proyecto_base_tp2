package edu.fiuba.algo3.modelo.Intercambios;

import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

import java.util.HashMap;
import java.util.Map;

public class Banco {

    private final Map<TipoDeRecurso, TipoDeRecurso> stock = new HashMap<>();

    private TipoDeRecurso claveDe(TipoDeRecurso r) {
        return r.nuevo(0);
    }

    public void recibir(TipoDeRecurso recurso) {
        if (recurso == null) throw new IllegalArgumentException("Recurso no puede ser null");
        if (recurso.obtenerCantidad() <= 0) throw new IllegalArgumentException("Cantidad > 0");

        TipoDeRecurso clave = claveDe(recurso);
        stock.merge(
                clave,
                recurso,
                (actual, nuevo) -> {
                    actual.sumar(nuevo.obtenerCantidad());
                    return actual;
                }
        );
    }

    public void inicializarStock(Map<TipoDeRecurso, Integer> inicial) {
        if (inicial == null) return;
        inicial.forEach((tipo, cant) -> recibir(tipo.nuevo(cant)));
    }

    public boolean tieneStock(TipoDeRecurso tipo, int cantidad) {
        TipoDeRecurso clave = claveDe(tipo);
        TipoDeRecurso r = stock.get(clave);
        return r != null && r.obtenerCantidad() >= cantidad;
    }

    public void entregar(TipoDeRecurso tipo, int cantidad) {
        TipoDeRecurso clave = claveDe(tipo);
        TipoDeRecurso r = stock.get(clave);
        if (r == null || r.obtenerCantidad() < cantidad) {
            throw new IllegalStateException("El banco no tiene suficiente " + tipo.nombre());
        }
        r.restar(cantidad);
        if (r.obtenerCantidad() == 0) {
            stock.remove(clave);
        }
    }
}

