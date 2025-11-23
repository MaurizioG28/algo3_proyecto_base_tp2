package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.*;
import edu.fiuba.algo3.modelo.Recursos.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazoOculto {
    private Random azar;
    public MazoOculto(Random random) {
        this.azar = random;
    }

    public CartaDesarrollo comprarCarta(Jugador comprador, int turno) {
        if (!comprador.tiene(new Madera(0), new Ladrillo(0), new Lana(1), new Mineral(1), new Grano(1))) {
            throw new RecursosInsuficientesException("No se tienen suficientes recursos para comprar una carta.");
        }
        comprador.quitarRecurso(new Lana(1), 1);
        comprador.quitarRecurso(new Grano(1), 1);
        comprador.quitarRecurso(new Mineral(1), 1);

        //Agarra una carta aleatoria de todas las que podemos comprar.

        List<CartaDesarrollo> cartasDisponibles = new ArrayList<>();
        cartasDisponibles.add(new CartaCaballero(turno));
        cartasDisponibles.add(new CartaConstruccionCarreteras(turno));
        cartasDisponibles.add(new CartaDescubrimiento(turno));
        cartasDisponibles.add(new CartaMonopolio(turno));
        cartasDisponibles.add(new PuntoDeVictoria(turno));

        int NumeroAleatorio = azar.nextInt(cartasDisponibles.size());

        return cartasDisponibles.get(NumeroAleatorio);
    }
}
