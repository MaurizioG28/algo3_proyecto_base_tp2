package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazoOculto {
    private Random azar;
    public MazoOculto(Random random) {
        this.azar = random;
    }

    public CartaDesarrollo comprarCarta(Jugador comprador, int turno) {
        if (!comprador.tiene(0, 0, 1, 1, 1)) {
            throw new RecursosInsuficientesException("No se tienen suficientes recursos para comprar una carta.");
        }
        comprador.removerRecurso(Recurso.LANA, 1);
        comprador.removerRecurso(Recurso.GRANO, 1);
        comprador.removerRecurso(Recurso.MINERAL, 1);

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
