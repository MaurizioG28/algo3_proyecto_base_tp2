package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.ReglaConstruccionException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.constructoresDeCarreteras.EstrategiaPagoEstandar;
import edu.fiuba.algo3.modelo.constructoresDeCarreteras.EstrategiaPagoGratuito;

import java.util.List;

public class CartaConstruccionCarreteras extends CartaDesarrollo {
    private Coordenada coordenada1;
    private Coordenada coordenada2;
    public CartaConstruccionCarreteras() {
        super();
    }
    public CartaConstruccionCarreteras(IEstadoCarta estado) {
        super(estado);
    }

    @Override
    public void ejecutarEfecto(Jugador jugadorActivo, Tablero tablero, List<Jugador> oponentes) {
        this.usar();

        if (coordenada1 == null || coordenada2 == null) {
            throw new IllegalStateException("Faltan coordenadas para construir carreteras.");
        }

        if (tablero.tieneCarreteraEn(coordenada1) || tablero.tieneCarreteraEn(coordenada2)) {
            throw new RuntimeException("Uno de los lugares seleccionados ya está ocupado. Seleccione lugares vacíos.");
        }

        jugadorActivo.setEstrategiaDePago(new EstrategiaPagoGratuito());

        try {
            jugadorActivo.construirCarretera(tablero, coordenada1);

            try {
                jugadorActivo.construirCarretera(tablero, coordenada2);
            } catch (Exception e) {


                System.out.println("Advertencia: La segunda carretera no pudo construirse (" + e.getMessage() + "), pero la primera sí. Carta consumida.");
            }

        } catch (Exception | ConstruccionExistenteException | ReglaConstruccionException e) {

            throw new RuntimeException(e.getMessage());
        } finally {
            jugadorActivo.setEstrategiaDePago(new EstrategiaPagoEstandar());
        }
    }

    public void setCoordenadas(Coordenada coord1, Coordenada coord2) {
        this.coordenada1 = coord1;
        this.coordenada2 = coord2;
    }
}
