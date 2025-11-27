package edu.fiuba.algo3.modelo.constructoresDeCarreteras;

import edu.fiuba.algo3.modelo.AlmacenDeRecursos;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.RecursosInsuficientesException;


import java.util.List;

public class EstrategiaPagoEstandar implements IEstrategiaDePago {

    @Override
    public EstrategiaPagoEstandar pagar(AlmacenDeRecursos almacen, List<TipoDeRecurso> costo) {

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
}
