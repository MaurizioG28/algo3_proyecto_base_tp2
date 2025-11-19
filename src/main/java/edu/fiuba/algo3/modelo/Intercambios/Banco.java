package edu.fiuba.algo3.modelo.Intercambios;

import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Banco {
    private final Map<TipoDeRecurso, Integer> stock = new HashMap<>();

    public Banco() {
    }

    public void inicializarStock(Map<TipoDeRecurso, Integer> inicial) {
        if (inicial == null) return;
        inicial.forEach((r, cant) -> stock.merge(r, cant, Integer::sum));
    }

    public boolean tieneStock(TipoDeRecurso recurso, int cantidad) {
        return stock.getOrDefault(recurso, 0) >= cantidad;
    }

    public void recibir(TipoDeRecurso recurso, int cantidad) {
        stock.merge(recurso, cantidad, Integer::sum);
    }

    public void entregar(TipoDeRecurso recurso, int cantidad) {
        if (!tieneStock(recurso, cantidad)) {
            throw new IllegalStateException("El banco no tiene suficiente " + recurso.nombre());
        }
        stock.put(recurso, stock.get(recurso) - cantidad);
    }
}
