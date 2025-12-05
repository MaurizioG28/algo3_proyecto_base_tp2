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
    try {
        int suma = dados.tirar();
        System.out.println("Dados lanzados: " + dados.getDado1() + " y " + dados.getDado2() + " (Suma: " + suma + ")");


        vista.actualizarDadosVisuales(dados.getDado1(), dados.getDado2());

    } catch (Exception e) {
        // Capturamos el error para que no congele la interfaz
        System.out.println("ADVERTENCIA: Error procesando el turno (posible error de Color): " + e.getMessage());
        e.printStackTrace(); // Útil para ver dónde falló exactamente
    } finally {

        // Deshabilitar botón lanzar para evitar loops infinitos
        if (botonLanzar != null) {
            botonLanzar.setDisable(true);
        }


        int sumaActual = dados.getDado1() + dados.getDado2();
        if (botonTerminar != null) {
            if (sumaActual == 7) {
                botonTerminar.setDisable(true);
            } else {
                botonTerminar.setDisable(false);
            }
        }
        // comentar estas lineas, son para testear
//        Catan.getInstance().getManagerTurno().getJugadorActual().agregarRecurso(new Madera(20));
//        Catan.getInstance().getManagerTurno().getJugadorActual().agregarRecurso(new Ladrillo(20));
//        Catan.getInstance().getManagerTurno().getJugadorActual().agregarRecurso(new Grano(20));
//        Catan.getInstance().getManagerTurno().getJugadorActual().agregarRecurso(new Lana(20));
//        Catan.getInstance().getManagerTurno().getJugadorActual().agregarRecurso(new Mineral(20));
        vista.actualizarInventario();
    }
}
}

