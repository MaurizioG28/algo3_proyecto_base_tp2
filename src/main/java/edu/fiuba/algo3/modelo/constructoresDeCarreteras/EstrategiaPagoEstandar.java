package edu.fiuba.algo3.modelo.constructoresDeCarreteras;

import edu.fiuba.algo3.modelo.AlmacenDeRecursos;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.RecursosInsuficientesException;
import edu.fiuba.algo3.modelo.interfaces.FichaComprable;


import java.util.List;

public class EstrategiaPagoEstandar implements IEstrategiaDePago {

    @Override
    public EstrategiaPagoEstandar pagar(AlmacenDeRecursos almacen, List<TipoDeRecurso> costo) {
        System.out.println("Se ha pagao con esto");

        for (TipoDeRecurso recurso : costo) {
            if (!almacen.tieneSuficiente(recurso)) {
                throw new RecursosInsuficientesException("No tienes suficientes recursos: " + recurso.nombre());
            }
        }
        for (TipoDeRecurso recurso : costo) {

            almacen.quitar(recurso, recurso.obtenerCantidad());
        }
        return this;
    }

    @Override
    public boolean seDebePagar(FichaComprable comprable) {
        return true;
    }
}
