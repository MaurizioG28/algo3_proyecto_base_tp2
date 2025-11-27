package edu.fiuba.algo3.modelo.constructoresDeCarreteras;

import edu.fiuba.algo3.modelo.AlmacenDeRecursos;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;


import java.util.List;

public class EstrategiaPagoGratuito implements IEstrategiaDePago {
    private int pagosGratuitos = 2;

    @Override
    public IEstrategiaDePago pagar(AlmacenDeRecursos almacen, List<TipoDeRecurso> costo) {
        this.pagosGratuitos--;
        if (this.pagosGratuitos <= 0) {
            return new EstrategiaPagoEstandar();
        } else {
            return this;
        }
    }

}
