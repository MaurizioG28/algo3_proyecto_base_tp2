package edu.fiuba.algo3.modelo.constructoresDeCarreteras;

import edu.fiuba.algo3.modelo.AlmacenDeRecursos;
import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.interfaces.FichaComprable;


import java.util.List;

public class EstrategiaPagoGratuito implements IEstrategiaDePago {
    private int pagosGratuitos = 2;

    @Override
    public IEstrategiaDePago pagar(AlmacenDeRecursos almacen, List<TipoDeRecurso> costo) {
        this.pagosGratuitos--;
        System.out.println("Se ha pagao");
        if (this.pagosGratuitos <= 0) {
            return new EstrategiaPagoEstandar();
        } else {
            return this;
        }
    }

    @Override
    public boolean seDebePagar(FichaComprable comprable) {
        return !((pagosGratuitos > 0) & (comprable instanceof Carretera));
    }

}
