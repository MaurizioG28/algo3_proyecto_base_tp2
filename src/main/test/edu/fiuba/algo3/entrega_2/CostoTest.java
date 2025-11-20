package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Tablero.Costo;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CostoTest {
    @Test
    public void test01CostosIgualesDebenSerIguales() {
        // Arrange
        Map<Recurso, Integer> mapa1 = new HashMap<>();
        mapa1.put(Recurso.MADERA, 1);
        mapa1.put(Recurso.LADRILLO, 2);

        Map<Recurso, Integer> mapa2 = new HashMap<>();
        mapa2.put(Recurso.MADERA, 1);
        mapa2.put(Recurso.LADRILLO, 2);

        Costo costo1 = new Costo(mapa1);
        Costo costo2 = new Costo(mapa2);

        // Act
        boolean resultado = costo1.esIgualA(costo2);

        // Assert
        assertTrue(resultado);
    }

    @Test
    public void test02CostosDiferentesNoSonIguales() {
        // Arrange
        Map<Recurso, Integer> mapa1 = new HashMap<>();
        mapa1.put(Recurso.MADERA, 1);

        Map<Recurso, Integer> mapa2 = new HashMap<>();
        mapa2.put(Recurso.MADERA, 2); // cambio la cantidad

        Costo costo1 = new Costo(mapa1);
        Costo costo2 = new Costo(mapa2);

        // Act
        boolean resultado = costo1.esIgualA(costo2);

        // Assert
        assertFalse(resultado);
    }

    @Test
    public void test03ModificarElMapaOriginalNoModificaElObjetoCosto() {
        // Arrange
        Map<Recurso, Integer> mapaOriginal = new HashMap<>();
        mapaOriginal.put(Recurso.LANA, 3);

        Costo costo = new Costo(mapaOriginal);

        // modifico el mapa ORIGINAL (no debería afectar al Costo)
        mapaOriginal.put(Recurso.MADERA, 10);

        // Act
        Map<Recurso, Integer> requerido = costo.requeridos();

        // Assert
        assertFalse(requerido.containsKey(Recurso.MADERA));
    }


}
