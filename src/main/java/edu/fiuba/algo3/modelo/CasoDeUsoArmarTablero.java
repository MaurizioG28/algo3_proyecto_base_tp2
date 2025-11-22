package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Factory.TableroFactory;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;

import java.util.List;

public class CasoDeUsoArmarTablero {
    private List<Terreno> hexagonos;
    private List<Produccion> fichas;
    public CasoDeUsoArmarTablero(List<Terreno> hexagonos, List<Produccion> fichas) {
        this.hexagonos = hexagonos;
        this.fichas = fichas;
    }

    public Tablero crearTablero(){
       return  TableroFactory.crear(hexagonos, fichas);
    }
}
