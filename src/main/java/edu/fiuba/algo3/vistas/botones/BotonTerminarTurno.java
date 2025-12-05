package edu.fiuba.algo3.vistas.botones;

import edu.fiuba.algo3.controllers.ReproductorEfectos;
import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.ManagerTurno;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BotonTerminarTurno extends Button {
    private ReproductorEfectos sfx = new ReproductorEfectos();


    public BotonTerminarTurno(EventHandler<ActionEvent> controlador) {
        super("Terminar");

        // Estilo Rojo Catan
        super.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        super.setTextFill(Color.WHITE);

        String estiloNormal = "-fx-background-color: #d9534f;" +
                "-fx-background-radius: 15;" +
                "-fx-border-color: #c9302c;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 15;";

        super.setStyle(estiloNormal);
        super.setPrefSize(110, 60);

        // Efectos
        DropShadow sombra = new DropShadow();
        sombra.setColor(Color.rgb(0,0,0,0.3));
        super.setEffect(sombra);
        super.setCursor(Cursor.HAND);

        super.setOnAction(e -> {
            if (!this.isDisabled()) {
                System.out.println("Fin de turno");
                System.out.println("puntaje jugador actual"+Catan.getInstance().getManagerTurno().getJugadorActual().totalPuntos());
                if(Catan.getInstance().getManagerTurno().getGranRutaComercial().getLider()!=null)
                    System.out.println("lider camino "+Catan.getInstance().getManagerTurno().getGranRutaComercial().getLider().getNombre()+" "+Catan.getInstance().getManagerTurno().getGranRutaComercial().getLider().totalPuntos());
                sfx.reproducirClick();
                controlador.handle(e);

            }
        });

        super.setOnMouseEntered(e -> {
            if (!this.isDisabled()) super.setStyle("-fx-background-color: #e27c79; -fx-background-radius: 15; -fx-border-color: white; -fx-border-radius: 15;");
        });
        super.setOnMouseExited(e -> {
            if (!this.isDisabled()) super.setStyle(estiloNormal);
        });
    }
}
