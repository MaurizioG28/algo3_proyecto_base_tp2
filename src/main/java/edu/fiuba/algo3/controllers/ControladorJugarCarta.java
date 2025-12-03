package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Cartas.*;
import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Cartas.*;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.ReglaConstruccionException;
import edu.fiuba.algo3.modelo.constructoresDeCarreteras.EstrategiaPagoEstandar;
import edu.fiuba.algo3.modelo.constructoresDeCarreteras.EstrategiaPagoGratuito;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.vistas.vistas.VistaTablero2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;

import java.util.List;
import java.util.Optional;

public class ControladorJugarCarta implements EventHandler<ActionEvent> {

    private final Catan catan;
    private final VistaTablero2 vista;
    private final int indiceCarta;

    private int caminosColocadosExitosamente = 0;

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
        // 1. Obtener qué carta seleccionó el usuario visualmente
        String nombreCarta = vista.getCartaSeleccionada();

        if (nombreCarta == null) {
            mostrarAlerta("Atención", "Debes seleccionar una carta habilitada primero.");
            return;
        }

        try {
            Jugador jugador = Catan.getInstance().getManagerTurno().getJugadorActual();

            // 2. Pedir al modelo la carta exacta (polimorfismo puro)
            // Si la carta es "nueva" y no se puede usar, este método lanzará error o devolverá null
            // según cómo lo definimos en el Mazo.
            CartaDesarrollo carta = jugador.buscarCartaParaJugar(nombreCarta);

            if (carta == null) {
                mostrarAlerta("Error", "No puedes jugar esa carta en este turno.");
                return;
            }

            boolean exito = false;

            // 3. Lógica de Interfaz según el tipo concreto de carta
            // (El modelo no sabe de JavaFX, así que el controlador debe pedir los datos)

            if (carta instanceof CartaCaballero) {
                vista.mostrarAlerta("Caballero", "Mueve el ladrón para activar el efecto.");
                vista.setModoCaballero(carta);
                vista.setModoRobo(true);
                exito = true;

            } else if (carta instanceof CartaMonopolio) {
                TipoDeRecurso recurso = pedirRecurso("Elige el recurso a monopolizar:");
                if (recurso != null) {
                    ((CartaMonopolio) carta).setRecursoElegido(recurso);
                    carta.ejecutarEfecto(jugador, Catan.getInstance().getTablero(), Catan.getInstance().getJugadores());
                    mostrarAlerta("Monopolio", "Has robado todos los recursos de tipo " + recurso.nombre());
                    exito = true;
                }

            } else if (carta instanceof CartaDescubrimiento) {
                List<TipoDeRecurso> recursos = pedirDosRecursos();
                if (recursos != null) {
                    ((CartaDescubrimiento) carta).setRecursosDeseados(recursos);
                    carta.ejecutarEfecto(jugador, null, null);
                    mostrarAlerta("Descubrimiento", "Has recibido 2 recursos del banco.");
                    exito = true;
                }

            } else if (carta instanceof CartaConstruccionCarreteras) {
                vista.activarModoCarreterasGratis((CartaConstruccionCarreteras) carta);
                vista.mostrarAlerta("Carreteras", "Construye 2 carreteras gratis.");
                exito = true;
            }

            // 4. Finalización
            if (exito) {
                // Marca en la vista que ya se jugó una carta (bloquea las demás)
                vista.marcarCartaJugada();
                vista.actualizarInventario();
                vista.verificarGanador();
            }

        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    // --- Métodos Auxiliares para Diálogos ---

    private TipoDeRecurso pedirRecurso(String mensaje) {
        List<String> opciones = List.of("Madera", "Ladrillo", "Lana", "Grano", "Mineral");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Madera", opciones);
        dialog.setTitle("Selección");
        dialog.setHeaderText(mensaje);
        Optional<String> result = dialog.showAndWait();
        return result.map(this::stringARecurso).orElse(null);
    }

    private List<TipoDeRecurso> pedirDosRecursos() {
        TipoDeRecurso r1 = pedirRecurso("Elige el 1er Recurso:");
        if (r1 == null) return null;
        TipoDeRecurso r2 = pedirRecurso("Elige el 2do Recurso:");
        if (r2 == null) return null;
        return List.of(r1, r2);
    }

    private TipoDeRecurso stringARecurso(String s) {
        switch (s) {
            case "Madera": return new Madera(0);
            case "Ladrillo": return new Ladrillo(0);
            case "Lana": return new Lana(0);
            case "Grano": return new Grano(0);
            case "Mineral": return new Mineral(0);
            default: return null;
        }
    }

    private void mostrarAlerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
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
            TipoDeRecurso r1Seleccion = pedirRecurso("Año de Abundancia", "Elija el 1er recurso:");
            if (r1Seleccion == null) return false;

            TipoDeRecurso r2Seleccion = pedirRecurso("Año de Abundancia", "Elija el 2do recurso:");
            if (r2Seleccion == null) return false;

            TipoDeRecurso r1Real = r1Seleccion.nuevo(1);
            TipoDeRecurso r2Real = r2Seleccion.nuevo(1);

            ((CartaDescubrimiento) carta).setRecursosDeseados(List.of(r1Real, r2Real));
            return true;

        } else if (carta instanceof CartaConstruccionCarreteras) {


            jugadorActual.setEstrategiaDePago(new EstrategiaPagoGratuito());
            this.caminosColocadosExitosamente = 0;

            vista.activarModoSeleccionCaminos((coordenadaClickeada) -> {
                procesarIntentoDeConstruccion(coordenadaClickeada, jugadorActual);
            });

            return false;
        }

        return true;
    }


    private void procesarIntentoDeConstruccion(Coordenada coord, Jugador jugador) {
        try {

            jugador.construirCarretera(catan.getTablero(), coord);

            caminosColocadosExitosamente++;

            vista.dibujarElementos();
            vista.actualizarInventario();

            if (caminosColocadosExitosamente < 2) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "¡Bien!", "Camino colocado. Selecciona 1 más.");
            } else {
                finalizarUsoDeCarta(jugador);
            }

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Posición Inválida", e.getMessage());
        } catch (ConstruccionExistenteException e) {
            throw new RuntimeException(e);
        } catch (ReglaConstruccionException e) {
            throw new RuntimeException(e);
        }
    }


    private void finalizarUsoDeCarta(Jugador jugador) {
        jugador.setEstrategiaDePago(new EstrategiaPagoEstandar());

        vista.desactivarModoSeleccion();

        try {
            jugador.descartarCarta(indiceCarta);
        } catch (Exception e) {
            e.printStackTrace();
        }

        vista.actualizarInventario();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Completo", "Has construido tus 2 carreteras gratuitas.");
    }


    private TipoDeRecurso pedirRecurso(String titulo, String mensaje) {
        List<TipoDeRecurso> opciones = List.of(
                new Madera(0), new Ladrillo(0), new Lana(0), new Grano(0), new Mineral(0)
        );
        return pedirOpcion(titulo, mensaje, opciones);
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