package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Tablero.Dados;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DadosTest {

    @Test
    public void test01DadosDevuelveUnEntero() {
        // Arrange
        Dados dados = new Dados();

        // Act
        int resultado = dados.tirar();

        // Assert
        assertDoesNotThrow(() -> dados.tirar());
    }


}
