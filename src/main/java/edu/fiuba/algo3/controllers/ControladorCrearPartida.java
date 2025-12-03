package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Cartas.*;
import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.vistas.PantallaPrincipal;
import edu.fiuba.algo3.vistas.vistas.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ControladorCrearPartida implements EventHandler<ActionEvent> {
    private final Stage stage   ;
    private final PantallaPrincipal pantallaPrincipal;
    private final ArrayList<TextField> nombres;
    private final ArrayList<ColorPicker> colores;


    public ControladorCrearPartida(Stage stage, PantallaPrincipal pantallaPrincipal, ArrayList<TextField> nombresIngresados, ArrayList<ColorPicker> coloresElegidos) {
        this.stage = stage;
        this.pantallaPrincipal = pantallaPrincipal;
        this.nombres = nombresIngresados;
        this.colores = coloresElegidos;

    }

    private void crearJugadores(Catan cantan){

        for (int i = 0; i < nombres.size(); i++) {

            String nombre = nombres.get(i).getText();

            // Validación simple: Si no puso nombre, le ponemos uno por defecto o lanzamos alerta
            if (nombre.trim().isEmpty()) {
                nombre = "Jugador " + (i + 1);
            }

            // Nota: toString() de ColorPicker devuelve algo como "0xff0000ff"
            String hexColor = colores.get(i).getValue().toString();
            Jugador nuevoJugador = new Jugador(nombre, new edu.fiuba.algo3.modelo.Color(hexColor));
            darKitDeDesarrollo(nuevoJugador);
            // Creamos el jugador y lo agregamos
            cantan.agregarJugador(nuevoJugador);
        }

    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Catan catan = Catan.getInstance();
        crearJugadores(catan);
        try {

            catan.iniciarPartida();
            VistaTablero2 vistaJuego = new VistaTablero2(stage, pantallaPrincipal);
            pantallaPrincipal.setCentro(vistaJuego);

        } catch (Exception e) {
            System.out.println("Error al iniciar la partida: " + e.getMessage());
            e.printStackTrace();
        }

    }
    private void darKitDeDesarrollo(Jugador jugador) {
        CartaCaballero caballero = new CartaCaballero();
        CartaMonopolio monopolio = new CartaMonopolio();
        CartaConstruccionCarreteras carreteras = new CartaConstruccionCarreteras();
        CartaDescubrimiento descubrimiento = new CartaDescubrimiento();

        caballero.nuevoTurno();
        monopolio.nuevoTurno();
        carreteras.nuevoTurno();
        descubrimiento.nuevoTurno();

        jugador.agregarCarta(caballero);
        jugador.agregarCarta(monopolio);
        jugador.agregarCarta(carreteras);
        jugador.agregarCarta(descubrimiento);

        jugador.agregarRecurso(new edu.fiuba.algo3.modelo.Recursos.Madera(5));
        jugador.agregarRecurso(new edu.fiuba.algo3.modelo.Recursos.Ladrillo(5));
    }

}
