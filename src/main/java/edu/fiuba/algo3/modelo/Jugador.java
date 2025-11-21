package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Intercambios.PoliticaDeIntercambio;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Jugador {

    private final AlmacenDeRecursos almacen = new AlmacenDeRecursos();
    private final List<PoliticaDeIntercambio> politicas = new ArrayList<>();

    public int CantidadRecurso(TipoDeRecurso tipo) {
        return almacen.cantidadDe(tipo);
    }

    public void agregarRecurso(TipoDeRecurso recurso) {
        almacen.agregarRecurso(recurso);
    }

    public void quitarRecurso(TipoDeRecurso tipo, int cantidad) {
        if (!almacen.quitar(tipo, cantidad)) {
            throw new IllegalStateException("El jugador no tiene suficientes " + tipo.nombre());
        }
    }

    public Map<TipoDeRecurso, Integer> descartarMitadDeRecursos() {
        return almacen.descartarPorReglaDelSiete();
    }

    public void agregarPolitica(PoliticaDeIntercambio politica) {
        politicas.add(politica);
    }

    public int mejorTasaPara(TipoDeRecurso recursoEntregado) {
        return politicas.stream()
                .filter(p -> p.aplicaA(this, recursoEntregado))
                .mapToInt(PoliticaDeIntercambio::tasa)
                .min()
                .orElse(4); // base 4:1
    }
}
