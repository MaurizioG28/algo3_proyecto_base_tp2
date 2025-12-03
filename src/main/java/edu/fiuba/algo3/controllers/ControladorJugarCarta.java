package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Cartas.*;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.vistas.vistas.VistaTablero2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

import java.util.List;
import java.util.Optional;

public class ControladorJugarCarta implements EventHandler<ActionEvent> {

    private final Catan catan;
    private final VistaTablero2 vista;
    private final int indiceCarta;

    public ControladorJugarCarta(Catan catan, VistaTablero2 vista, int indiceCarta) {
        this.catan = catan;
        this.vista = vista;
        this.indiceCarta = indiceCarta;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            Jugador jugadorActual = catan.getManagerTurno().getJugadorActual();


            CartaDesarrollo carta = jugadorActual.verCarta(indiceCarta);

            boolean configuracionExitosa = configurarCartaSegunTipo(carta, jugadorActual);

            if (configuracionExitosa) {
                catan.getManagerTurno().usarUnaCarta(indiceCarta);

                mostrarAlerta(Alert.AlertType.INFORMATION, "Carta Jugada", "El efecto se aplicó correctamente.");
                vista.actualizarInventario();
            }

        } catch (RuntimeException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Movimiento Inválido", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean configurarCartaSegunTipo(CartaDesarrollo carta, Jugador jugadorActual) {

        if (carta instanceof CartaCaballero) {
            Integer idDestino = pedirEntero("Mover Ladrón", "Ingrese ID del hexágono destino (1-19):");
            if (idDestino == null) return false;

            List<Jugador> oponentes = List.of();
            Jugador victima = pedirOpcion("Robar", "Elija víctima:", oponentes);

            ((CartaCaballero) carta).setOpciones(idDestino, victima);
            return true;

        } else if (carta instanceof CartaMonopolio) {
            TipoDeRecurso r = pedirRecurso("Monopolio", "Elija el recurso a monopolizar:");
            if (r == null) return false;

            ((CartaMonopolio) carta).setRecursoElegido(r);
            return true;

        } else if (carta instanceof CartaDescubrimiento) {
            TipoDeRecurso r1 = pedirRecurso("Año de Abundancia", "Elija el 1er recurso:");
            if (r1 == null) return false;

            TipoDeRecurso r2 = pedirRecurso("Año de Abundancia", "Elija el 2do recurso:");
            if (r2 == null) return false;

            ((CartaDescubrimiento) carta).setRecursosDeseados(List.of(r1, r2));
            return true;

        } else if (carta instanceof CartaConstruccionCarreteras) {
            Coordenada c1 = pedirCoordenada("Carretera 1");
            if (c1 == null) return false;

            Coordenada c2 = pedirCoordenada("Carretera 2");
            if (c2 == null) return false;

            ((CartaConstruccionCarreteras) carta).setCoordenadas(c1, c2);
            return true;
        }

        return true;
    }


    private TipoDeRecurso pedirRecurso(String titulo, String mensaje) {
        List<TipoDeRecurso> opciones = List.of(
                new Madera(0), new Ladrillo(0), new Lana(0), new Grano(0), new Mineral(0)
        );
        return pedirOpcion(titulo, mensaje, opciones);
    }

    private Coordenada pedirCoordenada(String titulo) {
        TextInputDialog dialog = new TextInputDialog("x,y");
        dialog.setTitle(titulo);
        dialog.setHeaderText("Ingrese coordenadas");
        dialog.setContentText("Formato x,y (ej: 3,4):");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                String[] partes = result.get().split(",");
                return new Coordenada(
                        Integer.parseInt(partes[0].trim()),
                        Integer.parseInt(partes[1].trim())
                );
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Formato incorrecto.");
            }
        }
        return null;
    }

    private Integer pedirEntero(String titulo, String mensaje) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(titulo);
        dialog.setContentText(mensaje);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                return Integer.parseInt(result.get());
            } catch (NumberFormatException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Debe ser un número.");
            }
        }
        return null;
    }

    private <T> T pedirOpcion(String titulo, String mensaje, List<T> opciones) {
        if (opciones.isEmpty()) return null;

        ChoiceDialog<T> dialog = new ChoiceDialog<>(opciones.get(0), opciones);
        dialog.setTitle(titulo);
        dialog.setHeaderText(null);
        dialog.setContentText(mensaje);

        Optional<T> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
