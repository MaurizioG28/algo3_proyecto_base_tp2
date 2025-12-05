package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.vistas.vistas.VistaTablero2;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorPanelJugadores {
    
    private VistaTablero2 vista;
    private final Map<Jugador, HBox> panelesPorJugador = new HashMap<>();
    private Catan juego = Catan.getInstance();
    private Jugador liderCamino=null;
    private Jugador liderCaballeria=null;

    public ControladorPanelJugadores(VistaTablero2 vistaTablero2) {
        this.vista = vistaTablero2;


    }

    public void agregarPanelyJugador(HBox infoJugador, Jugador j) {
        this.panelesPorJugador.put(j, infoJugador);
    }

    public void actualizarRutaComercial() {
        Jugador lider = juego.getManagerTurno().getRutaComercialLider();

        if (liderCamino != null && !liderCamino.equals(lider)) {
            HBox panelLiderAntiguo = panelesPorJugador.get(liderCamino);
            if (panelLiderAntiguo != null) {
                vista.actualizarRutaComercial(panelLiderAntiguo, 0.3); // panel
                vista.actualizarLogroCaballeriaEnBanner(liderCamino, 0.3); // banner
            }
        }

        //ACTUALIZAR NUEVO LÍDER
        liderCamino = lider;

        if (lider != null) {
            HBox panelLider = panelesPorJugador.get(lider);
            if (panelLider != null) {
                vista.actualizarRutaComercial(panelLider, 1.0);     // panel
                vista.actualizarLogroCaballeriaEnBanner(lider, 1.0);  // banner
            }
        }
    }


    public void actualizarGranCaballeria() {

        Jugador lider = juego.getManagerTurno().getGranCaballeriaLider();

        if (liderCaballeria != null && !liderCaballeria.equals(lider)) {

            HBox panelLiderAntiguo = panelesPorJugador.get(liderCaballeria);
            if (panelLiderAntiguo != null) {
                vista.actualizarGranCaballeria(panelLiderAntiguo, 0.3);
            }

            // BANNER SUPERIOR
            vista.actualizarLogroCaminoEnBanner(liderCaballeria, 0.3);
        }

        //ACTUALIZAR NUEVO LÍDER
        liderCaballeria = lider;

        if (lider != null) {

            // PANEL LATERAL
            HBox panelLider = panelesPorJugador.get(lider);
            if (panelLider != null) {
                vista.actualizarGranCaballeria(panelLider, 1.0);
            }

            // BANNER SUPERIOR
            vista.actualizarLogroCaminoEnBanner(lider, 1.0);
        }
    }



}
