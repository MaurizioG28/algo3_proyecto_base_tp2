package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;

import java.util.Random;

public class MazoOculto {
    private Random azar;
    public MazoOculto(Random random) {
        this.azar = random;
    }

    public CartaDesarrollo comprarCarta(Jugador comprador, int turno) {
        if (comprador.tiene(0, 0, 1, 1, 1)) {
            throw new RecursosInsuficientesException("No se tienen suficientes recursos para comprar una carta.");
        }
        comprador.removerRecurso(Recurso.LANA, 1);
        comprador.removerRecurso(Recurso.GRANO, 1);
        comprador.removerRecurso(Recurso.MINERAL, 1);

        CartaDesarrollo cartaCaballero = new CartaDesarrollo(turno);
        return cartaCaballero;
    }
}
