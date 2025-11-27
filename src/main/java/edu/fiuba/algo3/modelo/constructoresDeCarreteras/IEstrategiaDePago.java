package edu.fiuba.algo3.modelo.constructoresDeCarreteras;

import edu.fiuba.algo3.modelo.AlmacenDeRecursos;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

import java.util.List;

public interface IEstrategiaDePago {
    IEstrategiaDePago pagar(AlmacenDeRecursos arecurso, List<TipoDeRecurso> costo);
}

