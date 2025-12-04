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
        Jugador lider= juego.getManagerTurno().getRutaComercialLider();
        if(lider==null)return;
        if (liderCamino==null) {
            liderCamino=lider;
        }
        if( !lider.esDelColor(liderCamino.getColor())  ){
            HBox panelLiderAntiguo= panelesPorJugador.get(liderCamino);
            vista.actualizarRutaComercial( panelLiderAntiguo,0.3);
        }
        liderCamino=lider;

        if (lider!= null) {
            HBox panelLider = panelesPorJugador.get(lider);
            if (panelLider != null) {
                vista.actualizarRutaComercial(panelLider, 1.0); // Opacidad total
            }
        }
    }

    public void actualizarGranCaballeria() {
        Jugador lider= juego.getManagerTurno().getGranCaballeriaLider();
        if(lider==null)return;
        if(liderCaballeria==null) {liderCaballeria=lider;}
        if(!lider.esDelColor(liderCaballeria.getColor())   ){
            HBox panelLiderAntiguo= panelesPorJugador.get(liderCaballeria);
            vista.actualizarGranCaballeria( panelLiderAntiguo,0.3);
        }
        liderCaballeria=lider;
        if (lider != null) {
            HBox panelLider = panelesPorJugador.get(lider);
            if (panelLider != null) {
                vista.actualizarGranCaballeria(panelLider, 1.0);
            }
        }
    }


}
