package edu.fiuba.algo3.modelo.Intercambios;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

public interface PoliticaDeIntercambio {

    /**
     * ¿Esta política aplica para este jugador entregando este recurso?
     * (Por ahora el jugador casi no se usa, pero lo dejo por si en el futuro
     * hay políticas que dependen de puntos de victoria, cartas, etc.)
     */
    boolean aplicaA(Jugador jugador, TipoDeRecurso recursoEntregado);

    /**
     * Tasa de intercambio: cuántas cartas de recursoEntregado
     * hay que entregar para recibir 1 unidad de otro recurso.
     * Ej: 4 (banco base), 3 (puerto genérico), 2 (puerto específico).
     */
    int tasa();
}
