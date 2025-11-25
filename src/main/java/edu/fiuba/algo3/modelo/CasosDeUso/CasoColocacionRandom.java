package edu.fiuba.algo3.modelo.CasosDeUso;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Tablero;

import java.util.Random;

public class CasoColocacionRandom {
    Tablero tablero;





    public Tablero iniciarTablero(long seed) {
        Random random = new Random(seed);
        Catan catan=new Catan(random);
        return catan.crearTablero();
    }

    public Produccion buscarDesierto(Tablero tablero) {
        return tablero.getProduccionDelLadron();
    }
}
