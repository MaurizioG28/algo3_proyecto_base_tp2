package edu.fiuba.algo3.modelo.CasosDeUso;

import edu.fiuba.algo3.modelo.Cartas.*;
import edu.fiuba.algo3.modelo.MazoDeCartas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class CasoDeUsoCartasParaUnaPartida {

    private static final int CANTIDAD_CABALLEROS = 14;
    private static final int CANTIDAD_PUNTOS_VICTORIA = 5;
    private static final int CANTIDAD_MONOPOLIO = 2;
    private static final int CANTIDAD_DESCUBRIMIENTO = 2;
    private static final int CANTIDAD_CONSTRUCCION_CARRETERAS = 2;

    public MazoDeCartas crearMazo(Random azar) {

        List<CartaDesarrollo> cartas = new ArrayList<>();

        agregarCartas(cartas, () -> new CartaCaballero(), CANTIDAD_CABALLEROS);
        agregarCartas(cartas, () -> new PuntoDeVictoria(), CANTIDAD_PUNTOS_VICTORIA);
        agregarCartas(cartas, () -> new CartaMonopolio(), CANTIDAD_MONOPOLIO);
        agregarCartas(cartas, () -> new CartaDescubrimiento(), CANTIDAD_DESCUBRIMIENTO);
        agregarCartas(cartas, () -> new CartaConstruccionCarreteras(), CANTIDAD_CONSTRUCCION_CARRETERAS);

        Collections.shuffle(cartas, azar);

        MazoDeCartas mazoFinal = new MazoDeCartas();

        for (CartaDesarrollo carta : cartas) {
            mazoFinal.agregarCarta(carta);
        }

        return mazoFinal;
    }

    private void agregarCartas(List<CartaDesarrollo> lista, Supplier<CartaDesarrollo> fabrica, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            lista.add(fabrica.get());
        }
    }
}
