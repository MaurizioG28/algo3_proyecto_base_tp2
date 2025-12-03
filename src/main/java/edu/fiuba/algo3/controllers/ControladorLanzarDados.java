package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.Tablero.Dados;
import edu.fiuba.algo3.vistas.botones.BotonLanzarDados;
import edu.fiuba.algo3.vistas.botones.BotonTerminarTurno;
import edu.fiuba.algo3.vistas.vistas.VistaTablero2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControladorLanzarDados implements EventHandler<ActionEvent> {
    private final Dados dados;
    private final VistaTablero2 vista;
    private BotonLanzarDados botonLanzar;
    private BotonTerminarTurno botonTerminar;// Referencia al botón para bloquearlo

    public ControladorLanzarDados(Dados dadosModelo, VistaTablero2 vista) {
        this.dados = dadosModelo;
        this.vista = vista;
    }

    // Setter para conectar el botón después de crearlo
    public void setBoton(BotonLanzarDados boton) {
        this.botonLanzar = boton;
    }

    public void setBotonTerminar(BotonTerminarTurno boton) {
        this.botonTerminar = boton;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        // 1. Lógica del Modelo
        Catan catan = Catan.getInstance();

        int suma = dados.tirar();
        System.out.println("Dados lanzados: " + dados.getDado1() + " y " + dados.getDado2() + " (Suma: " + suma + ")");

        // 2. Lógica de la Vista (Aquí la vista detecta el 7 y prepara el modo robo)
        vista.actualizarDadosVisuales(dados.getDado1(), dados.getDado2());

        // 3. Bloquear el botón de lanzar (siempre se bloquea tras tirar)
        if (botonLanzar != null) botonLanzar.setDisable(true);

        // --- CORRECCIÓN CRUCIAL ---
        // Solo habilitamos "Terminar Turno" si NO salió un 7.
        // Si salió 7, es obligación del jugador mover el ladrón antes de poder terminar.
        if (botonTerminar != null) {
            if (suma == 7) {
                botonTerminar.setDisable(true); // Bloqueo forzoso
            } else {
                botonTerminar.setDisable(false); // Turno normal
            }
        }


        // 4. Actualización de recursos y lógica de negocio
        // NOTA: Si sale 7, Catan NO reparte dividendos, reparte "castigos" (descarte),
        // pero eso depende de tu modelo. Asumo que repartirDividendos maneja eso internamente.
        catan.getManagerTurno().repartirDividendos(suma);

//        Jugador jugador = catan.getManagerTurno().getJugadorActual();
//        jugador.agregarRecurso(new Lana(2));
//        jugador.agregarRecurso(new Grano(2));
//        jugador.agregarRecurso(new Mineral(2));
//        jugador.agregarRecurso(new Madera(2));
//        jugador.agregarRecurso(new Ladrillo(2));
//        System.out.println("DEBUG: Se regalaron recursos al jugador para testear compra.");
        vista.actualizarInventario();
    }
}

