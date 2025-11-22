package edu.fiuba.algo3.modelo.Intercambios;


import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

public class PuertoEspecifico implements PoliticaDeIntercambio {

    private final TipoDeRecurso recurso; // ej: MADERA
    private final int tasa;        // normalmente 2

    public PuertoEspecifico(TipoDeRecurso recurso, int tasa) {
        this.recurso = recurso;
        this.tasa = tasa;
    }


    @Override
    public boolean aplicaA(Jugador jugador, TipoDeRecurso recursoEntregado) {
        if (recursoEntregado == null) return false;
        return recursoEntregado.nombre().equals(this.recurso.nombre());
    }

    @Override
    public int tasa() {
        return tasa;
    }
}
