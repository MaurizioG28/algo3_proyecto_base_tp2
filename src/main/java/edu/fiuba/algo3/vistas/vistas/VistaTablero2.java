package edu.fiuba.algo3.vistas.vistas;

import edu.fiuba.algo3.controllers.*;
import edu.fiuba.algo3.modelo.Cartas.CartaCaballero;
import edu.fiuba.algo3.modelo.Cartas.CartaConstruccionCarreteras;
import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Catan;

import edu.fiuba.algo3.modelo.Intercambios.PoliticaDeIntercambio;
import edu.fiuba.algo3.modelo.Intercambios.Puerto;
import edu.fiuba.algo3.modelo.Jugador;

import edu.fiuba.algo3.modelo.ManagerTurno;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Dados;
import edu.fiuba.algo3.modelo.Tablero.Factory.Axial;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;


import edu.fiuba.algo3.modelo.Intercambios.PoliticaDeIntercambio;
import edu.fiuba.algo3.modelo.Intercambios.PuertoEspecifico;
import edu.fiuba.algo3.modelo.Intercambios.PuertoGenerico;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

import edu.fiuba.algo3.modelo.Tablero.Factory.ReglaConstruccionException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;
import edu.fiuba.algo3.vistas.PantallaPrincipal;
import edu.fiuba.algo3.vistas.botones.BotonGenericoAccionUsuario;
import edu.fiuba.algo3.vistas.botones.BotonLanzarDados;
import edu.fiuba.algo3.vistas.botones.BotonTerminarTurno;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;


import java.util.*;

public class VistaTablero2 extends BorderPane { //
    private static final double ANCHO_VENTANA = 1280;
    private static final double ALTO_VENTANA = 720;
    private static final String IMAGEN_RUTA = "imagenes/mapafondotablero.png";


    private HBox contenedorDadosVisuales;
    private Stage stage;
    private PantallaPrincipal pantallaPrincipal;
    private FlowPane contenedorCartasDesarrollo;
    private Label lblNombreJugadorActual;
    private VBox contenedorInfoJugadores;

    private Group grupoConstrucciones; // Edificios reales (fijos)
    private Group grupoSugestiones;    // Puntos/Líneas grises (temporales)
    private Group grupoPuertos;

    // HUD superior
    private HBox contenedorRecursosSuperior;
    private HBox barraAOE;


    // botones
    private BotonGenericoAccionUsuario btnConstruirPoblado;
    private BotonGenericoAccionUsuario btnConstruirCamino;
    private BotonGenericoAccionUsuario btnConstruirCiudad;
    private BotonGenericoAccionUsuario btnBanca;
    private BotonGenericoAccionUsuario btnIntercambioJugadores;
    private BotonGenericoAccionUsuario btnJugarCarta;
    private BotonGenericoAccionUsuario btnMoverLadron;
    private BotonGenericoAccionUsuario btnComprarCarta;
    private BotonLanzarDados btnLanzar;
    private BotonTerminarTurno btnTerminar;

    // estado y logica visual
    private Map<Coordenada, Point2D> mapaVisualVertices = new HashMap<>();
    private boolean enFaseInicial = true;
    private String cartaSeleccionada = null;
    private Circle ladronVisual;
    private boolean esperandoSeleccionHexagono = false;
    private boolean cartaDesarrolloJugadaEnTurno = false;
    private int carreterasGratisPendientes = 0;

    private CartaConstruccionCarreteras cartaCarreterasActiva = null;
    // Estado para "Caballero" (Saber si el robo viene de una carta o de los dados)
    private CartaDesarrollo cartaCaballeroActiva = null;

    //RadioGloblal
    private double radioGlobal = 70;

    private ControladorPanelJugadores controladorJugadores;

    public VistaTablero2(Stage stage, PantallaPrincipal pantallaPrincipal) {
        this.setBackground(new Background(new BackgroundFill(Color.web("#233850"), null, null)));

        this.setPrefSize(ANCHO_VENTANA, ALTO_VENTANA);
        stage.setMinWidth(ANCHO_VENTANA);
        stage.setMinHeight(ALTO_VENTANA);
        configurarFondo();
        this.stage = stage;
        this.pantallaPrincipal = pantallaPrincipal;

        // --- CENTRO: MAPA ---
        this.controladorJugadores = new ControladorPanelJugadores(this);
        StackPane contenedorMapa = new StackPane(agregarTerrenos());

        contenedorMapa.setAlignment(Pos.TOP_LEFT);
        contenedorMapa.setPadding(new Insets(0, 0, 0, 50));

        contenedorMapa.setTranslateY(-90);
        this.setCenter(contenedorMapa);
        // --- ARRIBA ---
        this.setTop(crearTopConBanderines());

        // --- INICIALIZAR BOTONES ---
        inicializarBotonesAcciones();

        this.setRight(crearPanelLateralDerecho());

        Platform.runLater(this::actualizarInventario);

        if (btnLanzar != null) btnLanzar.setDisable(true);
        if (btnTerminar != null) btnTerminar.setDisable(true);
        deshabilitarBotonesJuegoNormal();
        gestionarFlujoFaseInicial();
    }

    private Group agregarTerrenos() {
        Tablero tablero = Catan.getInstance().getTablero();
        if (tablero == null) tablero = Catan.getInstance().crearTablero();

        Map<Integer, Terreno> terrenos = tablero.getTerrenos();
        Group root = new Group();

        // Inicializar capas
        this.grupoPuertos = new Group();
        this.grupoConstrucciones = new Group();
        this.grupoSugestiones = new Group();

        double hexRadius = radioGlobal;
        double xDesierto = 0;
        double yDesierto = 0;

        for (Terreno t : terrenos.values()) {
            Axial pos = t.getPosicion();
            double x = hexRadius * Math.sqrt(3) * (pos.q + pos.r / 2.0);
            double y = hexRadius * 1.5 * pos.r;

            // DIBUJAR HEXÁGONO
            Polygon hexagon = createHexagon(x, y, hexRadius, t);
            root.getChildren().add(hexagon);

            // DIBUJAR FICHA DE NÚMERO (Si produce algo)
            if (!t.esDesierto() && t.getProduccion() != null) {

                // Círculo beige de fondo
                Circle ficha = new Circle(x, y, 15);
                ficha.setFill(Color.BEIGE);
                ficha.setStroke(Color.BLACK);
                ficha.setStrokeWidth(1);
                ficha.setMouseTransparent(true);

                // Obtener el número
                String textoNum = "";
                int numero = 0;

                try {
                    numero = t.getProduccion().valor();
                    textoNum = String.valueOf(numero);
                } catch (Exception e) {
                    textoNum = "?";
                }

                // Crear la etiqueta
                Label lblNum = new Label(textoNum);
                lblNum.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
                lblNum.setMouseTransparent(true);

                double ajusteX = (numero > 9) ? 9 : 5;
                lblNum.setTranslateX(x - ajusteX);
                lblNum.setTranslateY(y - 9);

                if (numero == 6 || numero == 8) {
                    lblNum.setTextFill(Color.RED);
                } else {
                    lblNum.setTextFill(Color.BLACK);
                }

                root.getChildren().addAll(ficha, lblNum);
            }

            // Guardar posición del desierto para el ladrón
            if (t.esDesierto()) {
                xDesierto = x;
                yDesierto = y;
            }
        }

        dibujarPuertos(hexRadius);

        root.getChildren().add(this.grupoPuertos);
        root.getChildren().add(this.grupoConstrucciones);
        root.getChildren().add(this.grupoSugestiones);

        // Ladrón
        ladronVisual = new Circle(15, Color.web("#333333"));
        ladronVisual.setStroke(Color.BLACK);
        ladronVisual.setStrokeWidth(2);
        ladronVisual.setMouseTransparent(true);
        ladronVisual.setTranslateX(xDesierto);
        ladronVisual.setTranslateY(yDesierto);

        root.getChildren().add(ladronVisual);

        return root;
    }


    private Polygon createHexagon(double x, double y, double radius, Terreno terreno) {
        Polygon hexagon = new Polygon();
        double angleOffset = Math.PI / 6;

        for (int i = 0; i < 6; i++) {
            double angle = (2.0 * Math.PI * i / 6) + angleOffset;
            double xPos = x + radius * Math.cos(angle);
            double yPos = y + radius * Math.sin(angle);
            hexagon.getPoints().addAll(xPos, yPos);
        }

        if (terreno != null) {
            String nombreImg = terreno.getTipoTerreno().toLowerCase();

            try {
                java.net.URL url = getClass().getResource("/imagenes/celdas/" + nombreImg + ".png");

                if (url != null) {
                    Image img = new Image(url.toExternalForm());
                    hexagon.setFill(new ImagePattern(img));
                } else {
                    hexagon.setFill(Color.BROWN);
                }

            } catch (Exception e) {
                hexagon.setFill(Color.BROWN);
            }

        } else {
            hexagon.setFill(Color.LIGHTGRAY);
        }


        hexagon.setStroke(Color.BLACK);
        hexagon.setStrokeWidth(2);

        hexagon.setOnMouseClicked(e -> {
            if (this.esperandoSeleccionHexagono) {
                moverLadronVisualmente(x, y);

                if (cartaCaballeroActiva != null) {
                    // --- CASO CABALLERO ---
                    CartaCaballero caballero = (CartaCaballero) cartaCaballeroActiva;

                    caballero.setOpciones(terreno.getId(), null); // null = víctima al azar según tu lógica

                    caballero.ejecutarEfecto(Catan.getInstance().getManagerTurno().getJugadorActual(),
                            Catan.getInstance().getTablero(),
                            Catan.getInstance().getJugadores());

                    cartaCaballeroActiva = null; // Consumida
                    marcarCartaJugada(); // Bloquear otras cartas
                    mostrarAlerta("Caballero", "Ladrón movido y recurso robado con éxito.");

                } else {
                    // --- CASO LADRÓN NORMAL (DADOS 7) ---
                    Catan.getInstance().getManagerTurno().moverLadron(terreno.getId());
                }

                this.esperandoSeleccionHexagono = false;
                this.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
                setModoRobo(false);
                actualizarInventario();
                verificarGanador();
                btnLanzar.setDisable(true);
                btnTerminar.setDisable(false);
            }
        });

        return hexagon;
    }

    public void moverLadronVisualmente(double x, double y) {
        if (ladronVisual != null) {
            ladronVisual.setTranslateX(x);
            ladronVisual.setTranslateY(y);
            ladronVisual.toFront();
        }
    }


    private void actualizarPanelJugadores() {
        if (this.controladorJugadores != null) {
            this.controladorJugadores.actualizarGranCaballeria();
            this.controladorJugadores.actualizarRutaComercial();
        }
        if (this.contenedorInfoJugadores == null) return;

        this.contenedorInfoJugadores.getChildren().clear();

        List<Jugador> jugadores = Catan.getInstance().getJugadores();

        ControladorPanelJugadores controladorPanel = new ControladorPanelJugadores(this);
        this.controladorJugadores = controladorPanel;

        for (Jugador j : jugadores) {
            HBox infoJugador = agregarJugador(j);
            controladorPanel.agregarPanelyJugador(infoJugador, j);
            this.contenedorInfoJugadores.getChildren().add(infoJugador);
        }

        controladorPanel.actualizarGranCaballeria();
        controladorPanel.actualizarRutaComercial();
    }


    private HBox agregarJugador(Jugador jugador) {
        HBox jugadorBox = new HBox();
        jugadorBox.setPadding(new Insets(10));
        jugadorBox.setAlignment(Pos.CENTER_LEFT);

        jugadorBox.setMinHeight(35);
        jugadorBox.setPrefHeight(35);
        jugadorBox.setMaxHeight(35);

        Color colorFondoJavaFX = Color.web(jugador.getColor().getColor());
        jugadorBox.setBackground(new Background(new BackgroundFill(colorFondoJavaFX, new CornerRadii(8), null)));
        jugadorBox.setStyle("-fx-border-color: rgba(255,255,255,0.3); -fx-border-radius: 8; -fx-border-width: 1;");

        Label nombreJugador = new Label(jugador.getNombre());
        nombreJugador.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        nombreJugador.setTextFill(obtenerColorTextoContraste(colorFondoJavaFX));

        jugadorBox.getChildren().add(nombreJugador);
        return jugadorBox;
    }


    private VBox crearPanelLogros(Color colorTexto) {
        VBox logrosBox = new VBox(5);
        logrosBox.setAlignment(Pos.CENTER);

        java.net.URL url = getClass().getResource("/imagenes/caballero.jpg");
        Image img = new Image(url.toExternalForm());
        ImageView iconoCaballeria = new ImageView(img);
        iconoCaballeria.setFitWidth(20);
        iconoCaballeria.setFitHeight(20);
        iconoCaballeria.setId("caballeria");

        java.net.URL url1 = getClass().getResource("/imagenes/carreteras.jpg");
        Image img1 = new Image(url1.toExternalForm());
        ImageView iconoCamino = new ImageView(img1);
        iconoCamino.setFitWidth(20);
        iconoCamino.setFitHeight(20);
        iconoCamino.setId("camino");

        HBox iconosBox = new HBox(5, iconoCaballeria, iconoCamino);
        iconosBox.setAlignment(Pos.CENTER);
        iconoCaballeria.setOpacity(0.3);
        iconoCamino.setOpacity(0.3);

        Label labelLogros = new Label("Logros");
        labelLogros.setFont(Font.font("Verdana", 10));
        labelLogros.setTextFill(colorTexto);

        logrosBox.getChildren().addAll(labelLogros, iconosBox);
        return logrosBox;
    }


    public void actualizarEstadoBotones() {
        try {
            Jugador jugador = Catan.getInstance().getManagerTurno().getJugadorActual();
            boolean tieneCamino = jugador.cantidadMadera() >= 1 && jugador.cantidadLadrillo() >= 1;
            this.btnConstruirCamino.setDisable(!tieneCamino);

            boolean tienePoblado = jugador.cantidadMadera() >= 1 && jugador.cantidadLadrillo() >= 1 &&
                    jugador.cantidadLana() >= 1 && jugador.cantidadGrano() >= 1;
            this.btnConstruirPoblado.setDisable(!tienePoblado);

            boolean tieneCiudad = jugador.cantidadMineral() >= 3 && jugador.cantidadGrano() >= 2;
            this.btnConstruirCiudad.setDisable(!tieneCiudad);

            // Validar compra carta (Lana + Grano + Mineral)
            boolean tieneRecursosCarta = jugador.cantidadLana() >= 1 && jugador.cantidadGrano() >= 1 && jugador.cantidadMineral() >= 1;
            this.btnComprarCarta.setDisable(!tieneRecursosCarta);

        } catch(Exception e) {
        }
    }


    private BotonGenericoAccionUsuario crearBotonAccion(String texto, EventHandler<ActionEvent> event) {
        BotonGenericoAccionUsuario btn = new BotonGenericoAccionUsuario (event,texto);
        return btn;
    }

    private String toHex(Color c) {
        return String.format("#%02X%02X%02X", (int)(c.getRed()*255), (int)(c.getGreen()*255), (int)(c.getBlue()*255));
    }
    private Color obtenerColorTextoContraste(Color colorFondo) {

        double luminancia = 0.2126 * colorFondo.getRed() +
                0.7152 * colorFondo.getGreen() +
                0.0722 * colorFondo.getBlue();


        return (luminancia > 0.55) ? Color.BLACK : Color.WHITE;
    }

    private StackPane crearDadoVisual(int valor) {

        StackPane dado = new StackPane();
        dado.setPrefSize(70, 70);

        Rectangle fondo = new Rectangle(70, 70);
        fondo.setArcWidth(18);
        fondo.setArcHeight(18);

        fondo.setFill(Color.web("#4A6370"));

        fondo.setStroke(Color.web("#FFFFFF"));
        fondo.setStrokeWidth(3);

        fondo.setEffect(new DropShadow(10, Color.color(0, 0, 0, 0.45)));

        dado.getChildren().add(fondo);

        GridPane puntosGrid = new GridPane();
        puntosGrid.setAlignment(Pos.CENTER);
        puntosGrid.setHgap(6);
        puntosGrid.setVgap(6);

        if (valor % 2 != 0) {
            puntosGrid.add(crearPunto(), 1, 1);
        }
        if (valor > 1) {
            puntosGrid.add(crearPunto(), 0, 0);
            puntosGrid.add(crearPunto(), 2, 2);
        }
        if (valor > 3) {
            puntosGrid.add(crearPunto(), 2, 0);
            puntosGrid.add(crearPunto(), 0, 2);
        }
        if (valor == 6) {
            puntosGrid.add(crearPunto(), 0, 1);
            puntosGrid.add(crearPunto(), 2, 1);
        }

        dado.getChildren().add(puntosGrid);
        return dado;
    }

    private Circle crearPunto() {
        Circle punto = new Circle(7);
        punto.setFill(Color.WHITE);
        return punto;
    }


    public void actualizarDadosVisuales(int valor1, int valor2) {

        soloDibujarDados(valor1, valor2);

        int suma = valor1 + valor2;
        ManagerTurno manager = Catan.getInstance().getManagerTurno();

        String mensajeResultado = manager.manejarLanzamientoDados(suma);
        this.cartaDesarrolloJugadaEnTurno = false;
        this.cartaSeleccionada = null;

        if (suma == 7) {
            mostrarAlerta("¡LADRÓN ACTIVO! (7)",
                    "El ladrón se ha activado.\n" + mensajeResultado);
            setModoRobo(true);
        } else {
            mostrarAlerta("Producción", "Salió el " + suma);
            actualizarEstadoBotones();
            habilitarBotonesJuegoNormal();
        }
        actualizarInventario();
    }
    public void marcarCartaJugada() {
        this.cartaDesarrolloJugadaEnTurno = true;
        this.cartaSeleccionada = null;
        actualizarInventario();
    }

    private void habilitarBotonesJuegoNormal() {
        btnIntercambioJugadores.setDisable(false);
        btnBanca.setDisable(false);
        btnJugarCarta.setDisable(false);
        if(btnTerminar != null) btnTerminar.setDisable(false);
    }
    public void deshabilitarBotonesJuegoNormal() {

        btnIntercambioJugadores.setDisable(true);
        btnBanca.setDisable(true);
        btnJugarCarta.setDisable(true);
        btnConstruirCamino.setDisable(true);
        btnConstruirPoblado.setDisable(true);
        btnConstruirCiudad.setDisable(true);
        btnComprarCarta.setDisable(true);

    }



    private VBox crearFichaConImagen(String nombre, int cantidad, String nombreImagen, String colorFondoHex) {
        VBox ficha = new VBox(2);
        ficha.setPrefSize(85, 100);
        ficha.setAlignment(Pos.CENTER);

        ficha.setStyle("-fx-background-color: " + colorFondoHex + ";" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 8;");

        StackPane contenedorImagen = new StackPane();
        contenedorImagen.setPrefSize(60, 60);

        try {
            java.net.URL url = getClass().getResource("/imagenes/cartas/" + nombreImagen);
            if (url != null) {
                ImageView imgView = new ImageView(new Image(url.toExternalForm()));
                imgView.setFitWidth(55);
                imgView.setFitHeight(55);
                imgView.setPreserveRatio(true);
                contenedorImagen.getChildren().add(imgView);
            }
        } catch (Exception e) {}

        Label lblNombre = new Label(nombre.length() > 6 ? nombre.substring(0, 6) + "." : nombre);
        lblNombre.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px;");

        Label lblCantidad = new Label("x" + cantidad);
        lblCantidad.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        ficha.getChildren().addAll(contenedorImagen, lblNombre, lblCantidad);
        return ficha;
    }




    public void actualizarInventario() {
        if (this.contenedorCartasDesarrollo == null || Catan.getInstance() == null) return;

        this.contenedorCartasDesarrollo.getChildren().clear();

        Jugador jugadorActual;
        try {
            jugadorActual = this.enFaseInicial ?
                    Catan.getInstance().getManagerTurno().getJugadorActualInicial() :
                    Catan.getInstance().getManagerTurno().getJugadorActual();
        } catch(Exception e) { return; }

        if (this.lblNombreJugadorActual != null) {
            this.lblNombreJugadorActual.setText(jugadorActual.getNombre());
        }

        // Actualizar recursos en la barra
        actualizarRecursosSuperiores(jugadorActual);
        actualizarPanelJugadores();

        boolean turnoHabilitado = !this.cartaDesarrolloJugadaEnTurno;

        this.contenedorCartasDesarrollo.getChildren().addAll(
                crearCartaSmart("Caballero", jugadorActual.cantidadCartasCaballero(), "caballero.png", "#A9A9A9",
                        turnoHabilitado && jugadorActual.tieneCartaHabilitada("Caballero")),

                crearCartaSmart("Monopolio", jugadorActual.cantidadCartasMonopolio(), "monopolio.png", "#90EE90",
                        turnoHabilitado && jugadorActual.tieneCartaHabilitada("Monopolio")),

                crearCartaSmart("Descubrimiento", jugadorActual.cantidadCartasDescubrimiento(), "descubrimiento.png", "#FFD700",
                        turnoHabilitado && jugadorActual.tieneCartaHabilitada("Descubrimiento")),

                crearCartaSmart("Carreteras", jugadorActual.cantidadCartasCarreteras(), "carreteras.png", "#FFD700",
                        turnoHabilitado && jugadorActual.tieneCartaHabilitada("Carreteras")),

                crearCartaSmart("Punto Vic.", jugadorActual.cantidadCartasPuntoVictoria(), "PV.png", "#FFD700", false)
        );

        verificarGanador();
    }

    private VBox crearCartaSmart(String nombre, int cantidad, String img, String color, boolean habilitada) {
        VBox carta = crearFichaConImagen(nombre, cantidad, img, color);

        if (cantidad <= 0 || !habilitada) {
            carta.setDisable(true);
            carta.setOpacity(cantidad <= 0 ? 0.3 : 0.6);
            return carta;
        }

        carta.setCursor(Cursor.HAND);
        carta.setOnMouseClicked(e -> {
            if (nombre.equals(this.cartaSeleccionada)) {
                this.cartaSeleccionada = null;
                carta.setStyle(carta.getStyle().replace("-fx-border-color: yellow;", "-fx-border-color: white;"));
            } else {
                this.cartaSeleccionada = nombre;

                for (javafx.scene.Node n : contenedorCartasDesarrollo.getChildren()) {
                    n.setStyle(n.getStyle().replace("-fx-border-color: yellow;", "-fx-border-color: white;"));
                }
                carta.setStyle(carta.getStyle().replace("-fx-border-color: white;", "-fx-border-color: yellow;"));
            }
        });
        return carta;
    }


    public String getCartaSeleccionada() {
        return this.cartaSeleccionada;
    }


    public void mostrarAlerta(String titulo, String mensaje) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    /**
     * Activa o desactiva el "Modo Robo".
     * Si activar es true: Bloquea todo excepto el botón del ladrón.
     * Si activar es false: Restaura los botones según los recursos del jugador.
     */
    public void setModoRobo(boolean activar) {
        if (activar) {
            // Bloquear TODAS las acciones de construcción y fin de turno
            btnConstruirCamino.setDisable(true);
            btnConstruirPoblado.setDisable(true);
            btnConstruirCiudad.setDisable(true);
            btnComprarCarta.setDisable(true);
            btnIntercambioJugadores.setDisable(true);
            btnJugarCarta.setDisable(true);
            btnBanca.setDisable(true);

            if (btnTerminar != null) btnTerminar.setDisable(true);

            //  Habilitar SOLO lo relacionado al ladrón
            if (btnMoverLadron != null) btnMoverLadron.setDisable(false);

            mostrarAlerta("¡LADRÓN ACTIVO!", "Se han bloqueado las acciones.\nDebes mover el ladrón para continuar.");

        } else {
            // Deshabilitar botón ladrón (ya se usó)
            if (btnMoverLadron != null) btnMoverLadron.setDisable(true);

            //  Habilitar botón terminar
            if (btnTerminar != null) btnTerminar.setDisable(false);

            // Restaurar botones de construcción según recursos
            actualizarEstadoBotones();
            if(btnLanzar !=null)btnLanzar.setDisable(true);
            // Reactivar otros botones genéricos si corresponde
            btnIntercambioJugadores.setDisable(false);
            btnJugarCarta.setDisable(false);
            btnBanca.setDisable(false);
        }
    }



    private void dibujarPuertos(double hexRadius) {

        grupoPuertos.getChildren().clear();
        Tablero tablero = Catan.getInstance().getTablero();

        // Evitar dibujar el mismo vértice-puerto más de una vez
        java.util.Set<Vertice> visitados = new java.util.HashSet<>();

        // Qué tan afuera del hexágono dibujamos el círculo del puerto
        double distanciaExtra = 25.0;

        for (Map.Entry<Coordenada, Vertice> entry : tablero.getMapaVertices().entrySet()) {

            Vertice v = entry.getValue();
            Coordenada coord = entry.getKey();

            // Solo vértices que sean puerto y que no hayamos procesado aún
            if (v == null || !v.esPuerto() || visitados.contains(v)) {
                continue;
            }
            visitados.add(v);

            Terreno t = tablero.getTerrenos().get(coord.numHex());
            if (t == null) continue;

            Axial pos = t.getPosicion();

            // Centro del hexágono al que pertenece este vértice
            double xCentro = hexRadius * Math.sqrt(3) * (pos.q + pos.r / 2.0);
            double yCentro = hexRadius * 1.5 * pos.r;

            // Índice del vértice (0..5)
            int i = coord.indice();

            // Mismo esquema de ángulos que usás en el resto de la vista
            double angle = (Math.PI / 2) + i * (Math.PI / 3) + Math.PI;

            // Posición del vértice donde iría el poblado/ciudad
            double xVertice = xCentro + hexRadius * Math.cos(angle);
            double yVertice = yCentro + hexRadius * Math.sin(angle);

            // Posición del puerto, un poco más afuera
            double radioPuerto = hexRadius + distanciaExtra;
            double xPuerto = xCentro + radioPuerto * Math.cos(angle);
            double yPuerto = yCentro + radioPuerto * Math.sin(angle);

            // --- DIBUJO ---

            // A. Línea marrón que conecta vértice con puerto (muelle)
            Line lineaConectora = new Line(xVertice, yVertice, xPuerto, yPuerto);
            lineaConectora.setStroke(Color.SADDLEBROWN);
            lineaConectora.setStrokeWidth(4);
            lineaConectora.setMouseTransparent(true);

            // B. Círculo del puerto
            Circle plataforma = new Circle(xPuerto, yPuerto, 14);
            plataforma.setFill(Color.SADDLEBROWN);
            plataforma.setStroke(Color.WHITE);
            plataforma.setStrokeWidth(2);

            // C. Texto: tasa + recurso (si es específico)
            String texto = "?";
            Color colorTexto = Color.WHITE;

            try {
                PoliticaDeIntercambio politica = v.obtenerPoliticaDeIntercambio();

                if (politica != null) {
                    int tasa = politica.tasa();

                    if (politica instanceof PuertoGenerico) {
                        // Puerto 3:1 genérico
                        texto = tasa + ":1";          // "3:1"
                        colorTexto = Color.WHITE;

                    } else if (politica instanceof PuertoEspecifico) {
                        PuertoEspecifico pe = (PuertoEspecifico) politica;

                        TipoDeRecurso recurso = pe.getRecurso();
                        String nombreRecurso = (recurso != null) ? recurso.nombre() : "";

                        // Ej: "2:1\nMadera"
                        texto = tasa + ":1\n" + nombreRecurso;

                        String lower = nombreRecurso.toLowerCase();
                        if (lower.contains("madera"))      colorTexto = Color.LIGHTGREEN;
                        else if (lower.contains("ladrillo")) colorTexto = Color.TOMATO;
                        else if (lower.contains("lana"))     colorTexto = Color.LIGHTGREEN;
                        else if (lower.contains("grano"))    colorTexto = Color.GOLD;
                        else if (lower.contains("mineral"))  colorTexto = Color.LIGHTGRAY;
                        else                                 colorTexto = Color.WHITE;

                    } else {
                        // Cualquier otra implementación rara de PoliticaDeIntercambio
                        texto = tasa + ":1";
                        colorTexto = Color.WHITE;
                    }
                } else {
                    texto = "";
                }

            } catch (Exception e) {
                texto = "?";
                colorTexto = Color.WHITE;
            }

            Label lbl = new Label(texto);
            lbl.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            lbl.setTextFill(colorTexto);
            lbl.setMouseTransparent(true);
            lbl.setTextAlignment(TextAlignment.CENTER);

            // Ajuste para centrar texto sobre el círculo
            lbl.setTranslateX(xPuerto - 14);
            lbl.setTranslateY(yPuerto - 10);

            grupoPuertos.getChildren().addAll(lineaConectora, plataforma, lbl);
        }
    }



    private void mostrarLugaresPoblado() {
        grupoSugestiones.getChildren().clear();
        Tablero tablero = Catan.getInstance().getTablero();
        double hexRadius = radioGlobal;

        Map<Coordenada, Vertice> mapaVertices = tablero.getMapaVertices();

        for (Map.Entry<Coordenada, Vertice> entry : mapaVertices.entrySet()) {
            Coordenada coord = entry.getKey();
            Vertice v = entry.getValue();

            // Si el vértice es válido y no tiene nada construido
            if (v != null && !v.tieneConstruccion()) {


                Point2D posVisual = calcularPosicionVisual(coord, hexRadius);
                if (posVisual == null) continue;

                Circle fantasma = new Circle(posVisual.getX(), posVisual.getY(), 12);
                fantasma.setFill(Color.rgb(128, 128, 128, 0.5)); // Gris transparente
                fantasma.setStroke(Color.WHITE);
                fantasma.getStrokeDashArray().addAll(5d, 5d);
                fantasma.setCursor(Cursor.HAND);

                fantasma.setOnMouseClicked(e -> ejecutarConstruccionPoblado(coord));

                grupoSugestiones.getChildren().add(fantasma);
            }
        }
    }

    private void mostrarLugaresCamino() {
        grupoSugestiones.getChildren().clear();
        Tablero tablero = Catan.getInstance().getTablero();
        ManagerTurno manager = Catan.getInstance().getManagerTurno();
        double hexRadius = radioGlobal;

        Map<Coordenada, edu.fiuba.algo3.modelo.Tablero.Factory.Lado> mapaLados = tablero.getMapaLados();

        Coordenada ultimoPoblado = null;
        if (this.enFaseInicial && !manager.estaEsperandoPobladoInicial()) {
            try {
                ultimoPoblado = manager.getUltimaCoordenadaPoblado();
            } catch (Exception e) {}
        }

        for (Map.Entry<Coordenada, edu.fiuba.algo3.modelo.Tablero.Factory.Lado> entry : mapaLados.entrySet()) {
            Coordenada coordLado = entry.getKey();
            edu.fiuba.algo3.modelo.Tablero.Factory.Lado lado = entry.getValue();

            // Solo mostramos si el lado está vacío
            if (lado != null && !lado.tieneConstruccion()) {

                // VALIDACIÓN FASE INICIAL: Solo mostrar si conecta con el poblado recién puesto
                if (this.enFaseInicial && ultimoPoblado != null) {
                    if (!tablero.ladoConectaConVertice(coordLado, ultimoPoblado)) {
                        continue;
                    }
                }

                // --- LÓGICA DE GEOMETRÍA CORREGIDA ---
                // Un lado en el índice 'i' conecta el vértice 'i' con el siguiente '(i+1)'
                int indexActual = coordLado.indice();
                int indexSiguiente = (indexActual + 1) % 6;

                //coordenadas de los DOS extremos del lado
                Coordenada coordV1 = new Coordenada(coordLado.numHex(), indexActual);
                Coordenada coordV2 = new Coordenada(coordLado.numHex(), indexSiguiente);

                // Calculamos la posición visual de AMBOS extremos usando la misma lógica que los vértices
                Point2D p1 = calcularPosicionVisual(coordV1, hexRadius);
                Point2D p2 = calcularPosicionVisual(coordV2, hexRadius);

                if (p1 != null && p2 != null) {
                    Line fantasma = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                    fantasma.setStrokeWidth(10);
                    fantasma.setStroke(Color.rgb(100, 100, 100, 0.5)); // Gris semitransparente
                    fantasma.setCursor(Cursor.HAND);

                    fantasma.setOnMouseClicked(e -> ejecutarConstruccionCamino(coordLado));

                    grupoSugestiones.getChildren().add(fantasma);
                }
            }
        }
    }

    private void mostrarLugaresCiudad() {
        grupoSugestiones.getChildren().clear();
        Tablero tablero = Catan.getInstance().getTablero();

        Jugador jugadorActual;
        try {
            jugadorActual = Catan.getInstance().getManagerTurno().getJugadorActual();
        } catch (Exception e) { return; }

        Map<Coordenada, Vertice> mapaVertices = tablero.getMapaVertices();

        for (Map.Entry<Coordenada, Vertice> entry : mapaVertices.entrySet()) {
            Coordenada coord = entry.getKey();
            Vertice v = entry.getValue();

            // VALIDACIÓN ESTRICTA:
            // 1. Debe existir el vértice.
            // 2. Debe tener construcción (Poblado).
            // 3. NO debe ser ya una ciudad.
            // 4. El dueño debe ser el jugador actual.
            if (v != null && v.tieneConstruccion() && !v.esCiudad()) {

                boolean esMio = false;
                try {
                    // Obtenemos el color de la construcción y lo comparamos con el del jugador
                    String colorConstruccion = v.obtenerConstruccion().getColorActual().getColor();
                    String colorJugador = jugadorActual.getColor().getColor();
                    esMio = colorConstruccion.equals(colorJugador);
                } catch (Exception e) { esMio = false; }

                if (esMio) {
                    // Usamos la calculadora centralizada para saber dónde dibujar
                    Point2D posVisual = calcularPosicionVisual(coord, radioGlobal); // radioGlobal es tu hexRadius
                    if (posVisual == null) continue;

                    Circle fantasma = new Circle(posVisual.getX(), posVisual.getY(), 20); // Más grande
                    fantasma.setFill(Color.TRANSPARENT);
                    fantasma.setStroke(Color.GOLD);
                    fantasma.setStrokeWidth(4);
                    fantasma.setCursor(Cursor.HAND);

                    // Efecto visual al pasar el mouse
                    fantasma.setOnMouseEntered(e -> fantasma.setFill(Color.rgb(255, 215, 0, 0.3)));
                    fantasma.setOnMouseExited(e -> fantasma.setFill(Color.TRANSPARENT));

                    fantasma.setOnMouseClicked(e -> ejecutarMejoraCiudad(coord));

                    grupoSugestiones.getChildren().add(fantasma);
                }
            }
        }
    }
    private void ejecutarConstruccionPoblado(Coordenada coord) {
        ManagerTurno manager = Catan.getInstance().getManagerTurno();
        try {
            if (!manager.haTerminadoFaseInicial()) {
                // Lógica Fase Inicial
                manager.colocacionInicial(coord);

                // Redibujar mapa real
                dibujarElementos();
                grupoSugestiones.getChildren().clear(); // Limpiar fantasmas viejos

                // **IMPORTANTE**: Llamamos al gestor para que pida el CAMINO
                gestionarFlujoFaseInicial();

            } else {
                // Lógica Juego Normal
                manager.construirPoblado(coord);
                grupoSugestiones.getChildren().clear();
                dibujarElementos();
                actualizarInventario();
                actualizarEstadoBotones();
                this.getScene().setCursor(Cursor.DEFAULT);
            }

        } catch (Exception | ReglaDistanciaException | ConstruccionExistenteException | ReglaConstruccionException e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void ejecutarConstruccionCamino(Coordenada coord) {
        ManagerTurno manager = Catan.getInstance().getManagerTurno();
        try {
            // --- CASO ESPECIAL: CARTA DE CARRETERAS ---
            if (carreterasGratisPendientes > 0) {
                Jugador actual = manager.getJugadorActual();

                // Truco: Cambiamos estrategia a gratis, construimos y restauramos
                actual.setEstrategiaDePago(new edu.fiuba.algo3.modelo.constructoresDeCarreteras.EstrategiaPagoGratuito());
                actual.construirCarretera(Catan.getInstance().getTablero(), coord);
                actual.setEstrategiaDePago(new edu.fiuba.algo3.modelo.constructoresDeCarreteras.EstrategiaPagoEstandar());

                carreterasGratisPendientes--;

                // Actualizar visuales
                grupoSugestiones.getChildren().clear();
                dibujarElementos();

                if (carreterasGratisPendientes > 0) {
                    mostrarAlerta("Carreteras", "¡Te queda 1 carretera gratis!");
                    mostrarLugaresCamino(); // Mostrar fantasmas de nuevo
                } else {
                    mostrarAlerta("Carreteras", "Carta finalizada.");
                    // Finalizar uso de carta
                    marcarCartaJugada();
                    actualizarInventario();
                    verificarGanador();
                }

                controladorJugadores.actualizarRutaComercial();

                return; // Salimos para no ejecutar lógica normal
            }

            if (!manager.haTerminadoFaseInicial()) {
                // ... lógica fase inicial ...
                manager.colocacionInicial(coord);
                grupoSugestiones.getChildren().clear();
                dibujarElementos();
                gestionarFlujoFaseInicial();
            } else {
                // ... lógica juego normal ...
                manager.construirCarretera(coord);
                grupoSugestiones.getChildren().clear();
                dibujarElementos();
                actualizarInventario();
                actualizarEstadoBotones();
                verificarGanador();
            }
            controladorJugadores.actualizarRutaComercial();

        } catch (Exception | ConstruccionExistenteException | ReglaConstruccionException e) {
            mostrarAlerta("Error", e.getMessage());
        } catch (ReglaDistanciaException e) {
            throw new RuntimeException(e);
        }
    }

    private void ejecutarMejoraCiudad(Coordenada coord) {
        ManagerTurno manager = Catan.getInstance().getManagerTurno();
        try {
            manager.mejorarACiudad(coord);

            grupoSugestiones.getChildren().clear();
            dibujarElementos();
            actualizarInventario();
            actualizarEstadoBotones();

        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    // Llama a esto cada vez que se construya algo con éxito para actualizar el mapa
    public void dibujarElementos() {
        if (this.grupoConstrucciones == null) {
            this.grupoConstrucciones = new Group();
        }
        this.grupoConstrucciones.getChildren().clear();

        Tablero tablero = Catan.getInstance().getTablero();
        double hexRadius = radioGlobal;

        // Sets para evitar dibujar el mismo vértice/lado múltiples veces
        java.util.Set<Vertice> verticesVisitados = new java.util.HashSet<>();
        java.util.Set<edu.fiuba.algo3.modelo.Tablero.Factory.Lado> ladosVisitados = new java.util.HashSet<>();

        for (Terreno t : tablero.getTerrenos().values()) {
            Axial pos = t.getPosicion();
            double x = hexRadius * Math.sqrt(3) * (pos.q + pos.r / 2.0);
            double y = hexRadius * 1.5 * pos.r;

            // ---  DIBUJAR POBLADOS Y CIUDADES ---
            for (int i = 0; i < 6; i++) {
                Coordenada coord = new Coordenada(t.getId(), i);
                Vertice v = tablero.obtenerVertice(coord);

                // Si v es null o ya lo dibujamos, saltar
                if (v == null || verticesVisitados.contains(v)) continue;
                verticesVisitados.add(v); // Marcar como visitado

                if (v.tieneConstruccion()) {
                    // Calc posición visual
                    double angle = (Math.PI / 2) + i * (Math.PI / 3) + Math.PI;
                    double vx = x + hexRadius * Math.cos(angle);
                    double vy = y + hexRadius * Math.sin(angle);

                    Color colorJugador = Color.WHITE;
                    try {
                        String hex = v.obtenerConstruccion().getColorActual().getColor();
                        colorJugador = Color.web(hex);
                    } catch (Exception e) { }

                    if (v.esCiudad()) {
                        javafx.scene.shape.Rectangle ciudad = new javafx.scene.shape.Rectangle(vx - 10, vy - 10, 20, 20);
                        ciudad.setFill(colorJugador);
                        ciudad.setStroke(Color.BLACK);
                        ciudad.setStrokeWidth(2);
                        grupoConstrucciones.getChildren().add(ciudad);
                    } else {
                        Circle poblado = new Circle(vx, vy, 10);
                        poblado.setFill(colorJugador);
                        poblado.setStroke(Color.BLACK);
                        poblado.setStrokeWidth(2);
                        grupoConstrucciones.getChildren().add(poblado);
                    }
                }
            }

            // --- DIBUJAR CAMINOS ---
            Map<Coordenada, edu.fiuba.algo3.modelo.Tablero.Factory.Lado> mapaLados = tablero.getMapaLados();
            for(int i = 0; i < 6; i++) {
                Coordenada coordLado = new Coordenada(t.getId(), i);
                edu.fiuba.algo3.modelo.Tablero.Factory.Lado lado = mapaLados.get(coordLado);

                // Si no hay lado lógico, o ya lo dibujamos, o no tiene construcción visual, saltar
                if (lado == null || ladosVisitados.contains(lado)) continue;
                ladosVisitados.add(lado);

                if (lado.tieneConstruccion()) {
                    double angle1 = (Math.PI / 2) + i * (Math.PI / 3) + Math.PI;
                    double angle2 = (Math.PI / 2) + ((i + 1) % 6) * (Math.PI / 3) + Math.PI;


                    double x1 = x + hexRadius * Math.cos(angle1);
                    double y1 = y + hexRadius * Math.sin(angle1);
                    double x2 = x + hexRadius * Math.cos(angle2);
                    double y2 = y + hexRadius * Math.sin(angle2);

                    Line camino = new Line(x1, y1, x2, y2);
                    camino.setStrokeWidth(6);


                    Color colorJugador = Color.BLACK;
                    try {
                        String hex = lado.getPropietario().getColor();
                        colorJugador = Color.web(hex);
                    } catch (Exception e) { }

                    // Color c = Color.web(lado.getConstruccion().getColorActual().getColor());
                    camino.setStroke(colorJugador); // Color por defecto si no tienes getter directo
                    grupoConstrucciones.getChildren().add(camino);
                }
            }
        }
    }

    private void gestionarFlujoFaseInicial() {
        ManagerTurno manager = Catan.getInstance().getManagerTurno();

        //CHEQUEO DE FINALIZACIÓN
        if (manager.haTerminadoFaseInicial()) {
            if(this.enFaseInicial) {
                this.enFaseInicial = false;
                this.btnLanzar.setDisable(false);
                this.btnTerminar.setDisable(true);

                // Actualizar inventario para mostrar al jugador que empieza la partida normal
                actualizarInventario();
                actualizarEstadoBotones();
                grupoSugestiones.getChildren().clear();
                mostrarAlerta("¡Juego Iniciado!", "Fase inicial completa. ¡Lanza los dados!");
            }
            return;
        }

        //  FORZAR LA ACTUALIZACIÓN VISUAL DEL JUGADOR
        // Esto lee manager.getJugadorActualInicial() y cambia el color/nombre en el panel
        actualizarInventario();

        //Configuración de botones y alertas
        this.btnLanzar.setDisable(true);
        Jugador jugadorActual = manager.getJugadorActualInicial();

        if (manager.estaEsperandoPobladoInicial()) {
            mostrarAlerta("Turno de " + jugadorActual.getNombre(), "Coloca tu POBLADO Inicial");
            mostrarLugaresPoblado();
        } else {
            mostrarAlerta("Turno de " + jugadorActual.getNombre(), "Coloca tu CAMINO conectado");
            mostrarLugaresCamino();
        }
    }

    private Point2D calcularPosicionVisual(Coordenada coord, double hexRadius) {
        Tablero tablero = Catan.getInstance().getTablero();
        Terreno t = tablero.getTerrenos().get(coord.numHex());

        if (t == null) return null;

        Axial pos = t.getPosicion();
        double xCentro = hexRadius * Math.sqrt(3) * (pos.q + pos.r / 2.0);
        double yCentro = hexRadius * 1.5 * pos.r;

        int i = coord.indice();

        double angle = (Math.PI / 2) + i * (Math.PI / 3) + Math.PI;

        double x = xCentro + hexRadius * Math.cos(angle);
        double y = yCentro + hexRadius * Math.sin(angle);

        return new Point2D(x, y);
    }

    private void soloDibujarDados(int valor1, int valor2) {
        this.contenedorDadosVisuales.getChildren().clear();
        StackPane d1 = crearDadoVisual(valor1);
        StackPane d2 = crearDadoVisual(valor2);
        this.contenedorDadosVisuales.getChildren().addAll(d1, d2);
    }

    public void activarModoCarreterasGratis(CartaConstruccionCarreteras carta) {
        this.carreterasGratisPendientes = 2; // Tienes 2 por colocar
        this.cartaCarreterasActiva = carta;

        // Muestra los fantasmas para que el usuario pueda hacer clic
        mostrarLugaresCamino();
    }

    public void setModoCaballero(CartaDesarrollo carta) {
        this.cartaCaballeroActiva = carta;
        controladorJugadores.actualizarGranCaballeria();
        // Nota: El controlador llamará a setModoRobo(true) inmediatamente después de esto.
    }

    public void verificarGanador() {
        try {
            Jugador actual = Catan.getInstance().getManagerTurno().getJugadorActual();

            // Obtenemos el total (Poblados + Ciudades + Bonos + Cartas PV Ocultas si aplica)
            int puntos = actual.totalPuntos();

            // Actualizamos el panel derecho para que se vea el puntaje nuevo
            actualizarPanelJugadores();

            // REGLA: Gana con 10 Puntos de Victoria
            if (puntos >= 10) {
                // --- CORRECCIÓN: CAMBIAR A VISTA GANADOR ---
                this.pantallaPrincipal.setCentro(new VistaGanador(stage, pantallaPrincipal, actual));
                // -------------------------------------------
            }
        } catch (Exception e) {
            System.out.println("Error verificando ganador: " + e.getMessage());
        }
    }


    public void actualizarRutaComercial(HBox panelLider, double opacidad) {
        ImageView iconoCamino = (ImageView) panelLider.lookup("#camino");
        if(iconoCamino != null){
            iconoCamino.setOpacity(opacidad);
        }

    }

    public void actualizarGranCaballeria(HBox panelLider,double opacidad) {
        ImageView iconoCaballero = (ImageView) panelLider.lookup("#caballeria");
        if(iconoCaballero != null){
            iconoCaballero.setOpacity(opacidad);
        }

    }

    //Banderines
    private HBox crearRecursoHUD(String nombreImagen, int cantidad) {
        HBox box = new HBox(6);
        box.setAlignment(Pos.CENTER_LEFT);

        String estilo = "-fx-background-color: rgba(0,0,0,0.40);"
                + "-fx-background-radius: 10;"
                + "-fx-border-radius: 10;"
                + "-fx-border-color: rgba(255,255,255,0.25);"
                + "-fx-border-width: 1;"
                + "-fx-padding: 6 10 6 10;";

        box.setStyle(estilo);

        ImageView icono;
        try {
            icono = new ImageView(new Image(
                    getClass().getResource("/imagenes/barraRecursos/" + nombreImagen).toExternalForm()
            ));
        } catch (Exception e) {
            icono = new ImageView();
        }

        icono.setFitWidth(28);
        icono.setFitHeight(28);
        icono.setPreserveRatio(true);

        Label lblCantidad = new Label(String.valueOf(cantidad));
        lblCantidad.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        lblCantidad.setTextFill(Color.WHITE);
        lblCantidad.setEffect(new DropShadow(2, Color.BLACK));

        Tooltip.install(box, new Tooltip(nombreImagen.replace(".png", "").toUpperCase()));

        box.getChildren().addAll(icono, lblCantidad);
        return box;
    }

    private HBox crearBarraAOE() {
        HBox barra = new HBox(20);
        barra.setPadding(new Insets(5, 15, 5, 15));
        barra.setAlignment(Pos.CENTER_LEFT);

        barra.setStyle(
                "-fx-background-color: rgba(0,0,0,0.35);" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(255,255,255,0.25);" +
                        "-fx-border-width: 1;"
        );

        barra.getChildren().add(crearRecursoHUD("madera.png", 0));
        barra.getChildren().add(crearRecursoHUD("ladrillo.png", 0));
        barra.getChildren().add(crearRecursoHUD("lana.png", 0));
        barra.getChildren().add(crearRecursoHUD("grano.png", 0));
        barra.getChildren().add(crearRecursoHUD("mineral.png", 0));

        return barra;
    }

    private HBox crearBarraSuperior() {
        HBox barra = new HBox(20);
        barra.setPadding(new Insets(4, 15, 4, 15));
        barra.setMinHeight(50);
        barra.setPrefHeight(50);
        barra.setMaxHeight(50);
        barra.setAlignment(Pos.CENTER_LEFT);
        barra.setStyle("-fx-background-color: rgba(0,0,0,0.25);");

        this.lblNombreJugadorActual = new Label("Jugador 1");
        this.lblNombreJugadorActual.setStyle(
                "-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;"
        );

        this.contenedorRecursosSuperior = new HBox(15);
        this.contenedorRecursosSuperior.setAlignment(Pos.CENTER_LEFT);

        cargarRecursosSuperioresIniciales();

        barra.getChildren().addAll(
                lblNombreJugadorActual,
                contenedorRecursosSuperior
        );

        return barra;
    }
    private StackPane crearTopConBanderines() {

        // --- BARRA SUPERIOR ---
        HBox barra = crearBarraSuperior();
        barra.setMinHeight(45);
        barra.setPrefHeight(45);
        barra.setMaxHeight(45);

        // --- CONTENEDOR SUPERFICIAL PARA BANDERINES ---
        HBox banners = new HBox(18);
        banners.setAlignment(Pos.TOP_RIGHT);
        banners.setPadding(new Insets(0, 25, 0, 0));

        banners.setPickOnBounds(false);

        for (Jugador j : Catan.getInstance().getJugadores()) {

            StackPane banner = crearBannerJugador(j);


            banner.setTranslateY(-5);

            banners.getChildren().add(banner);
        }

        // --- SUPERPOSICIÓN ---
        StackPane stack = new StackPane();
        StackPane.setAlignment(barra, Pos.TOP_LEFT);
        StackPane.setAlignment(banners, Pos.TOP_RIGHT);

        stack.getChildren().addAll(barra, banners);

        return stack;
    }



    private StackPane crearBannerJugador(Jugador jugador) {
        Color colorJugador = Color.web(jugador.getColor().getColor(), 0.9);

        Rectangle cuerpo = new Rectangle(110, 100);
        cuerpo.setArcWidth(8);
        cuerpo.setArcHeight(8);
        cuerpo.setFill(colorJugador);
        cuerpo.setStroke(Color.BLACK);
        cuerpo.setStrokeWidth(2);

        Polygon punta = new Polygon(
                0.0, 0.0,
                110.0, 0.0,
                55.0, 35.0
        );
        punta.setFill(colorJugador);
        punta.setStroke(Color.BLACK);
        punta.setStrokeWidth(2);

        VBox forma = new VBox(cuerpo, punta);
        forma.setAlignment(Pos.TOP_CENTER);

        // Efecto de brillo para el jugador actual
        if (jugador == Catan.getInstance().getManagerTurno().getJugadorActual()) {
            DropShadow glow = new DropShadow(20, Color.GOLD);
            glow.setSpread(0.3);
            forma.setEffect(glow);
        }

        //Contenido del Texto
        VBox texto = new VBox(2);
        texto.setAlignment(Pos.CENTER);

        Label nombre = new Label(jugador.getNombre());
        nombre.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        nombre.setTextFill(Color.WHITE);

        Label pv = new Label(String.valueOf(jugador.totalPuntos()));
        pv.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));
        pv.setTextFill(Color.WHITE);

        VBox logros = crearPanelLogros(Color.WHITE);

        logros.setId("panel_logros_" + jugador.getNombre());

        texto.getChildren().addAll(nombre, pv, logros);

        return new StackPane(forma, texto);
    }
    private void cargarRecursosSuperioresIniciales() {
        try {
            Jugador jugadorActual = Catan.getInstance()
                    .getManagerTurno()
                    .getJugadorActualInicial();

            actualizarRecursosSuperiores(jugadorActual);
        } catch (Exception e) {
        }
    }

    private void actualizarRecursosSuperiores(Jugador jugadorActual) {
        if (contenedorRecursosSuperior == null || jugadorActual == null) return;

        contenedorRecursosSuperior.getChildren().setAll(
                crearRecursoHUD("madera.png", jugadorActual.cantidadMadera()),
                crearRecursoHUD("ladrillo.png", jugadorActual.cantidadLadrillo()),
                crearRecursoHUD("lana.png", jugadorActual.cantidadLana()),
                crearRecursoHUD("grano.png", jugadorActual.cantidadGrano()),
                crearRecursoHUD("mineral.png", jugadorActual.cantidadMineral())
        );
    }
    public void actualizarLogroCaballeriaEnBanner(Jugador jugador, double opacidad) {
        ImageView icono = (ImageView) this.lookup("#caballeria_banner_" + jugador.getNombre());
        if (icono != null) icono.setOpacity(opacidad);
    }

    public void actualizarLogroCaminoEnBanner(Jugador jugador, double opacidad) {
        ImageView icono = (ImageView) this.lookup("#camino_banner_" + jugador.getNombre());
        if (icono != null) icono.setOpacity(opacidad);
    }
    private void estilizarBotonesAccion() {

        btnLanzar.setStyle(
                "-fx-background-color: #4A6370;" +      // primario
                        "-fx-background-radius: 14;" +
                        "-fx-padding: 10 25;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 14;"
        );

        btnLanzar.setOnMouseEntered(e ->
                btnLanzar.setStyle(
                        "-fx-background-color: #5B7482;" +
                                "-fx-background-radius: 14;" +
                                "-fx-padding: 10 25;" +
                                "-fx-text-fill: white;" +
                                "-fx-font-size: 16;" +
                                "-fx-font-weight: bold;" +
                                "-fx-border-color: white;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 14;"
                )
        );
        btnLanzar.setOnMouseExited(e -> estilizarBotonesAccion());


        // 🎨 BOTÓN SECUNDARIO: TERMINAR
        btnTerminar.setStyle(
                "-fx-background-color: #3B4E59;" +      // secundario
                        "-fx-background-radius: 14;" +
                        "-fx-padding: 10 25;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-color: #A0B0BB;" +           // borde más suave
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 14;"
        );

        btnTerminar.setOnMouseEntered(e ->
                btnTerminar.setStyle(
                        "-fx-background-color: #4A6370;" + // hover igual al primario
                                "-fx-background-radius: 14;" +
                                "-fx-padding: 10 25;" +
                                "-fx-text-fill: white;" +
                                "-fx-font-size: 16;" +
                                "-fx-font-weight: bold;" +
                                "-fx-border-color: white;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 14;"
                )
        );
        btnTerminar.setOnMouseExited(e -> estilizarBotonesAccion());
    }



    private javafx.scene.Node crearPanelLateralDerecho() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(15));

        panel.setPrefWidth(550);

        panel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-border-color: rgba(255, 255, 255, 0.1); -fx-border-width: 0 0 0 1;");

        // ---  DADOS ---
        Label lblTurno = new Label("CONTROL DE TURNO");
        lblTurno.setTextFill(Color.LIGHTGRAY);
        lblTurno.setFont(Font.font("Arial", FontWeight.BOLD, 10));

        this.contenedorDadosVisuales = new HBox(15);
        this.contenedorDadosVisuales.setAlignment(Pos.CENTER);
        soloDibujarDados(1, 1);

        if (this.btnLanzar == null) this.btnLanzar = new BotonLanzarDados(new ControladorLanzarDados(new Dados(), this));
        if (this.btnTerminar == null) this.btnTerminar = new BotonTerminarTurno(new ControladorTerminarTurno(btnLanzar, this));
        estilizarBotonesAccion();

        HBox boxTurno = new HBox(15, btnLanzar, btnTerminar);
        boxTurno.setAlignment(Pos.CENTER);

        // ---  ACCIONES ---
        Label lblAcciones = new Label("ACCIONES");
        lblAcciones.setTextFill(Color.LIGHTGRAY);
        lblAcciones.setFont(Font.font("Arial", FontWeight.BOLD, 10));

        GridPane grid = new GridPane();
        grid.setHgap(15); grid.setVgap(8);
        grid.setAlignment(Pos.CENTER);

        btnConstruirPoblado.setMaxWidth(Double.MAX_VALUE);
        btnConstruirCiudad.setMaxWidth(Double.MAX_VALUE);

        grid.add(btnConstruirPoblado, 0, 0); grid.add(btnConstruirCiudad, 1, 0);
        grid.add(btnConstruirCamino, 0, 1);  grid.add(btnComprarCarta, 1, 1);
        grid.add(btnIntercambioJugadores, 0, 2); grid.add(btnBanca, 1, 2);
        grid.add(btnMoverLadron, 0, 3);      grid.add(btnJugarCarta, 1, 3);

        // --- CARTAS ---
        Label lblCartas = new Label("MIS CARTAS");
        lblCartas.setTextFill(Color.LIGHTGRAY);
        lblCartas.setFont(Font.font("Arial", FontWeight.BOLD, 10));

        this.contenedorCartasDesarrollo = new FlowPane();
        this.contenedorCartasDesarrollo.setHgap(10);
        this.contenedorCartasDesarrollo.setVgap(10);
        this.contenedorCartasDesarrollo.setAlignment(Pos.TOP_CENTER);

        this.contenedorCartasDesarrollo.setPrefWrapLength(430);

        ScrollPane scrollCartas = new ScrollPane(contenedorCartasDesarrollo);
        scrollCartas.setFitToWidth(true);
        scrollCartas.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-padding: 0;");
        VBox.setVgrow(scrollCartas, Priority.ALWAYS);

        panel.getChildren().addAll(
                lblTurno, contenedorDadosVisuales, boxTurno, new Separator(),
                lblAcciones, grid, new Separator(),
                lblCartas, scrollCartas
        );

        return panel;
    }

    private void inicializarBotonesAcciones() {
        this.btnConstruirPoblado = crearBotonAccion("Poblado", e -> {
            mostrarAlerta("Modo Construcción", "Selecciona un punto gris.");
            mostrarLugaresPoblado();
        });
        this.btnConstruirCamino = crearBotonAccion("Camino", e -> {
            mostrarAlerta("Modo Construcción", "Selecciona una línea gris.");
            mostrarLugaresCamino();
        });
        this.btnConstruirCiudad = crearBotonAccion("Ciudad", e -> {
            mostrarAlerta("Modo Ciudad", "Mejora un poblado existente.");
            mostrarLugaresCiudad();
        });

        this.btnBanca = crearBotonAccion("Banca", new ControladorBanca(Catan.getInstance(), this));
        this.btnIntercambioJugadores = crearBotonAccion("Intercambio", new ControladorIntercambioEntreJugadores(Catan.getInstance(), this));
        this.btnJugarCarta = crearBotonAccion("Jugar Carta", new ControladorJugarCarta(Catan.getInstance(), this));

        this.btnMoverLadron = crearBotonAccion("Mover Ladrón", e -> {
            this.esperandoSeleccionHexagono = true;
            mostrarAlerta("Mover Ladrón", "Haz clic en un hexágono.");
            this.getScene().setCursor(javafx.scene.Cursor.HAND);
        });
        this.btnMoverLadron.setDisable(true);

        this.btnComprarCarta = crearBotonAccion("Comprar Carta", new ControladorComprarCarta(this));
    }

    private void configurarFondo() {
        Image imagen = new Image(IMAGEN_RUTA);
        BackgroundImage fondoImagen = new BackgroundImage(imagen,
                BackgroundRepeat.ROUND,
                BackgroundRepeat.SPACE,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));
        Background fondo = new Background(fondoImagen);
        super.setBackground(fondo);
    }
}
