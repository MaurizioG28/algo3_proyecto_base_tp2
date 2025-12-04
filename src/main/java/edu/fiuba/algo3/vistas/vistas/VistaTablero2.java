package edu.fiuba.algo3.vistas.vistas;

import edu.fiuba.algo3.controllers.*;
import edu.fiuba.algo3.modelo.Cartas.CartaCaballero;
import edu.fiuba.algo3.modelo.Cartas.CartaConstruccionCarreteras;
import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Catan;

import edu.fiuba.algo3.modelo.Jugador;

import edu.fiuba.algo3.modelo.ManagerTurno;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Dados;
import edu.fiuba.algo3.modelo.Tablero.Factory.Axial;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;

import edu.fiuba.algo3.modelo.Tablero.Factory.ReglaConstruccionException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;
import edu.fiuba.algo3.vistas.PantallaPrincipal;
import edu.fiuba.algo3.vistas.botones.BotonGenericoAccionUsuario;
import edu.fiuba.algo3.vistas.botones.BotonLanzarDados;
import edu.fiuba.algo3.vistas.botones.BotonTerminarTurno;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Label;
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

import java.util.*;

public class VistaTablero2 extends BorderPane { // CAMBIO: Ahora extendemos BorderPane
    private static final double ANCHO_VENTANA = 1280;
    private static final double ALTO_VENTANA = 720;


    private HBox contenedorDadosVisuales;
    private Stage stage;
    private PantallaPrincipal pantallaPrincipal;
    private HBox contenedorRecursos;
    private HBox contenedorCartasDesarrollo;
    private Label lblNombreJugadorActual;
    private VBox contenedorInfoJugadores;

    private Group grupoConstrucciones; // Edificios reales (fijos)
    private Group grupoSugestiones;    // Puntos/Líneas grises (temporales)
    private Group grupoPuertos;


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

    private ControladorPanelJugadores controladorJugadores;

    public VistaTablero2(Stage stage, PantallaPrincipal pantallaPrincipal) {

        this.setBackground(new Background(new BackgroundFill(Color.web("#233850"), null, null)));
        this.stage = stage;
        this.pantallaPrincipal = pantallaPrincipal;
        StackPane contenedorMapa = new StackPane(agregarTerrenos());
        contenedorMapa.setAlignment(Pos.CENTER);
        this.setCenter(contenedorMapa);

        // ABAJO: Inventario + Acciones
        this.setBottom(crearPanelInferior());

        // DERECHA: Jugadores + Dados + Fin Turno
        this.setRight(crearPanelDerecho());


        // 5. Initial Inventory Load
        actualizarInventario();

        btnLanzar.setDisable(true);
        btnTerminar.setDisable(true);
        deshabilitarBotonesJuegoNormal();
        gestionarFlujoFaseInicial();
    }

private Group agregarTerrenos() {
    Tablero tablero = Catan.getInstance().getTablero();
    if(tablero == null) tablero = Catan.getInstance().crearTablero();

    Map<Integer, Terreno> terrenos = tablero.getTerrenos();
    Group root = new Group();

    // Inicializar capas
    this.grupoPuertos = new Group();
    this.grupoConstrucciones = new Group();
    this.grupoSugestiones = new Group();

    double hexRadius = 50;
    double xDesierto = 0;
    double yDesierto = 0;

    for (Terreno t : terrenos.values()) {
        Axial pos = t.getPosicion();
        double x = hexRadius * Math.sqrt(3) * (pos.q + pos.r / 2.0);
        double y = hexRadius * 1.5 * pos.r;

        // 1. DIBUJAR HEXÁGONO
        Polygon hexagon = createHexagon(x, y, hexRadius, t);
        root.getChildren().add(hexagon);

        // 2. DIBUJAR FICHA DE NÚMERO (Si produce algo)
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
                // Aquí usamos tu lógica
                numero = t.getProduccion().valor();
                textoNum = String.valueOf(numero); // <--- FALTABA ESTO: Convertir int a String
            } catch (Exception e) {
                textoNum = "?";
            }

            // Crear la etiqueta
            Label lblNum = new Label(textoNum);
            lblNum.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
            lblNum.setMouseTransparent(true);

            // Centrar el texto visualmente (ajuste fino)
            // Si el número tiene 2 dígitos (10, 11, 12), lo movemos un poco más a la izquierda
            double ajusteX = (numero > 9) ? 9 : 5;
            lblNum.setTranslateX(x - ajusteX);
            lblNum.setTranslateY(y - 9);

            // Regla visual: 6 y 8 van en ROJO
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

    // 3. Agregar resto de capas
    dibujarPuertos(hexRadius);

    root.getChildren().add(this.grupoPuertos);
    root.getChildren().add(this.grupoConstrucciones);
    root.getChildren().add(this.grupoSugestiones);

    // 4. Ladrón
    ladronVisual = new Circle(15, Color.web("#333333"));
    ladronVisual.setStroke(Color.BLACK);
    ladronVisual.setStrokeWidth(2);
    ladronVisual.setMouseTransparent(true);
    ladronVisual.setTranslateX(xDesierto);
    ladronVisual.setTranslateY(yDesierto);

    root.getChildren().add(ladronVisual);

    return root;
}


    // He unificado tus métodos createHexagon y añadido lógica de Ladrón
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
                // Intento de carga seguro
                java.net.URL url = getClass().getResource("/imagenes/" + nombreImg + ".jpg");
                if (url != null) {
                    hexagon.setFill(new ImagePattern(new Image(url.toExternalForm())));
                } else {
                    hexagon.setFill(Color.BROWN);
                }
            } catch (Exception e) { hexagon.setFill(Color.BROWN); }
        } else {
            hexagon.setFill(Color.LIGHTGRAY);
        }

        hexagon.setStroke(Color.BLACK);
        hexagon.setStrokeWidth(2);

        hexagon.setOnMouseClicked(e -> {
            if (this.esperandoSeleccionHexagono) {
                // 1. Mover visualmente
                moverLadronVisualmente(x, y);

                // 2. Lógica de Negocio
                if (cartaCaballeroActiva != null) {
                    // --- CASO CABALLERO ---
                    CartaCaballero caballero = (CartaCaballero) cartaCaballeroActiva;

                    // Configuramos la carta con el destino elegido
                    caballero.setOpciones(terreno.getId(), null); // null = víctima al azar según tu lógica

                    // Ejecutamos efecto
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

                // 3. Restaurar estado
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
            ladronVisual.toFront(); // Lo traemos al frente por si acaso quedó tapado
        }
    }



    private VBox crearPanelDerecho() {
        VBox panel = new VBox();
        panel.setPadding(new Insets(20));
        panel.setPrefWidth(300);
        panel.setAlignment(Pos.TOP_CENTER);

        // 1. ZONA JUGADORES (Dinámica)
        this.contenedorInfoJugadores = new VBox();
        this.contenedorInfoJugadores.setSpacing(10); // Espacio entre fichas de jugadores
        actualizarPanelJugadores(); // Llenar por primera vez

        // 2. ESPACIADOR
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // 3. ZONA DADOS Y CONTROLES (Estática)
        this.contenedorDadosVisuales = new HBox(15);
        this.contenedorDadosVisuales.setAlignment(Pos.CENTER);

        Dados dados = new Dados();
        ControladorLanzarDados controladorLanzar = new ControladorLanzarDados(dados, this);
        this.btnLanzar = new BotonLanzarDados(controladorLanzar);

        ControladorTerminarTurno controladorTerminarTurno = new ControladorTerminarTurno(btnLanzar, this);
        this.btnTerminar = new BotonTerminarTurno(controladorTerminarTurno);

        // Configuración inicial de botones
        btnTerminar.setDisable(true);
        controladorLanzar.setBoton(btnLanzar);
        controladorLanzar.setBotonTerminar(btnTerminar);
        controladorTerminarTurno.setBotonTerminar(btnTerminar);

        HBox contenedorBotones = new HBox(15);
        contenedorBotones.setAlignment(Pos.CENTER);
        contenedorBotones.getChildren().addAll(btnLanzar, btnTerminar);

        VBox zonaControl = new VBox(20);
        zonaControl.setAlignment(Pos.CENTER);
        zonaControl.getChildren().addAll(this.contenedorDadosVisuales, contenedorBotones);

        // 4. ARMAR EL PANEL FINAL
        panel.getChildren().addAll(this.contenedorInfoJugadores, spacer, zonaControl);

        // Dibujo inicial de dados
        soloDibujarDados(1, 1);

        return panel;
    }

    private void actualizarPanelJugadores() {
        if (this.contenedorInfoJugadores == null) return;

        this.contenedorInfoJugadores.getChildren().clear(); // Limpiamos solo la lista

        List<Jugador> jugadores = Catan.getInstance().getJugadores();

        // Instanciamos el controlador del panel (para los logros visuales)
        ControladorPanelJugadores controladorPanel = new ControladorPanelJugadores(this);
        this.controladorJugadores = controladorPanel;

        for (Jugador j : jugadores) {
            HBox infoJugador = agregarJugador(j); // Crea la ficha con puntos actualizados
            controladorPanel.agregarPanelyJugador(infoJugador, j);
            this.contenedorInfoJugadores.getChildren().add(infoJugador);
        }

        // Actualizamos los íconos de logros si corresponde
        controladorPanel.actualizarGranCaballeria();
        controladorPanel.actualizarRutaComercial();
    }

    private HBox agregarJugador(Jugador jugador) {
        HBox jugadorBox = new HBox();
        jugadorBox.setPadding(new Insets(10));
        Color colorFondoJavaFX = Color.web(jugador.getColor().getColor());
        jugadorBox.setBackground(new Background(new BackgroundFill(colorFondoJavaFX, new CornerRadii(8), null)));
        jugadorBox.setStyle("-fx-border-color: rgba(255,255,255,0.3); -fx-border-radius: 8; -fx-border-width: 1;");
        Color colorTexto = obtenerColorTextoContraste(colorFondoJavaFX);

        Label nombreJugador = new Label(jugador.getNombre());
        nombreJugador.setPrefWidth(110);
        nombreJugador.setWrapText(true);
        nombreJugador.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        nombreJugador.setTextFill(colorTexto);

        VBox puntosVictoria = new VBox(2);
        puntosVictoria.setAlignment(Pos.CENTER);

        Label pvLabel = new Label("Puntos de\nVictoria");
        pvLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 10));
        pvLabel.setTextAlignment(TextAlignment.CENTER);
        pvLabel.setTextFill(colorTexto);

        String puntosReales = String.valueOf(jugador.totalPuntos());
        Label pvValue = new Label(puntosReales);
        pvValue.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        pvValue.setAlignment(Pos.CENTER);
        pvValue.setTextFill(colorTexto);

        VBox logros = crearPanelLogros();


        puntosVictoria.getChildren().addAll(pvLabel, pvValue);
        jugadorBox.getChildren().addAll(nombreJugador, puntosVictoria,logros);

        return jugadorBox;
    }

    private VBox crearPanelLogros() {
        VBox logrosBox = new VBox(5);
        logrosBox.setAlignment(Pos.CENTER);

        java.net.URL url = getClass().getResource("/imagenes/caballero.jpg" );

        Image img = new Image(url.toExternalForm());
        // Gran Caballería
        ImageView iconoCaballeria = new ImageView(
                img
        );
        iconoCaballeria.setFitWidth(20);
        iconoCaballeria.setFitHeight(20);
        iconoCaballeria.setId("caballeria");

        java.net.URL url1 = getClass().getResource("/imagenes/carreteras.jpg" );

        Image img1 = new Image(url1.toExternalForm());

        // Gran Camino
        ImageView iconoCamino = new ImageView(
                img1
        );
        iconoCamino.setFitWidth(20);
        iconoCamino.setFitHeight(20);
        iconoCamino.setId("camino");

        // Contenedor para iconos
        HBox iconosBox = new HBox(5, iconoCaballeria, iconoCamino);
        iconosBox.setAlignment(Pos.CENTER);
        iconoCaballeria.setOpacity(0.3);
        iconoCamino.setOpacity( 0.3);
        // Label
        Label labelLogros = new Label("Logros");
        labelLogros.setFont(Font.font("Verdana", 10));
        labelLogros.setTextFill(Color.WHITE);

        logrosBox.getChildren().addAll(labelLogros, iconosBox);



        return logrosBox;
    }


    private HBox crearPanelInferior() {
        HBox panel = new HBox(20);
        panel.setPadding(new Insets(15, 20, 15, 20));
        panel.setAlignment(Pos.BOTTOM_CENTER);
        panel.setPrefHeight(200);

        // INVENTARIO
        VBox inventario = new VBox(5);
        inventario.setPrefWidth(600);
        inventario.setPadding(new Insets(10, 15, 10, 15));
        inventario.setAlignment(Pos.TOP_CENTER);
        inventario.setStyle("-fx-background-color: #222; -fx-background-radius: 20; -fx-border-color: black; -fx-border-width: 4; -fx-border-radius: 20;");

        this.lblNombreJugadorActual = new Label("Cargando...");
        this.lblNombreJugadorActual.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label lblTitulo = new Label("INVENTARIO");
        lblTitulo.setStyle("-fx-text-fill: gray; -fx-font-size: 10px; -fx-font-weight: bold; -fx-letter-spacing: 2;");

        HBox filaDeCartas = new HBox(15);
        filaDeCartas.setAlignment(Pos.CENTER);
        filaDeCartas.setPrefWidth(580);

        this.contenedorRecursos = new HBox(10);
        this.contenedorRecursos.setAlignment(Pos.CENTER);

        this.contenedorCartasDesarrollo = new HBox(10);
        this.contenedorCartasDesarrollo.setAlignment(Pos.CENTER);
        this.contenedorCartasDesarrollo.setStyle("-fx-padding: 0 0 0 10; -fx-border-color: gray; -fx-border-width: 0 0 0 2;");

        filaDeCartas.getChildren().addAll(this.contenedorRecursos, this.contenedorCartasDesarrollo);
        inventario.getChildren().addAll(this.lblNombreJugadorActual, lblTitulo, filaDeCartas);

        actualizarInventario();

        // GRID DE ACCIONES
        GridPane acciones = new GridPane();
        acciones.setHgap(15);
        acciones.setVgap(15);
        acciones.setAlignment(Pos.CENTER_LEFT);

        //this.btnConstruirPoblado = crearBotonAccion("Construir\nPoblado", new ControladorConstruirPoblado(Catan.getInstance()));
        //this.btnConstruirCamino = crearBotonAccion("Construir\nCamino", new ControladorConstruirCamino(Catan.getInstance()));

        this.btnConstruirPoblado = crearBotonAccion("Construir\nPoblado", e -> {
            mostrarAlerta("Modo Construcción", "Selecciona un punto gris para construir.");
            mostrarLugaresPoblado(); // <--- Llama a la visualización
        });

        this.btnConstruirCamino = crearBotonAccion("Construir\nCamino", e -> {
            mostrarAlerta("Modo Construcción", "Selecciona una línea gris para el camino.");
            mostrarLugaresCamino(); // <--- Llama a la visualización
        });

        this.btnConstruirCiudad = crearBotonAccion("Construir\nCiudad", e -> {
            mostrarAlerta("Modo Ciudad", "Selecciona uno de tus poblados para mejorar.");
            mostrarLugaresCiudad(); // <--- Llama a la visualización
        });

        this.btnBanca = crearBotonAccion("Banca", new ControladorBanca(Catan.getInstance(), this));
        this.btnIntercambioJugadores = crearBotonAccion("Intercambio", new ControladorIntercambioEntreJugadores(Catan.getInstance(), this));
        this.btnJugarCarta = crearBotonAccion("JUGAR\nCARTA", new ControladorJugarCarta(Catan.getInstance(), this));
        this.btnMoverLadron = crearBotonAccion("MOVER\nLADRÓN", e -> {
            this.esperandoSeleccionHexagono = true;
            mostrarAlerta("Mover Ladrón", "Haz clic en un hexágono para colocar al ladrón.");
            this.getScene().setCursor(javafx.scene.Cursor.HAND);
        });
        this.btnMoverLadron.setDisable(true);

        // --- BOTON COMPRAR CARTA  ---
        this.btnComprarCarta = crearBotonAccion("Comprar\nCarta D.",new ControladorComprarCarta(this));

        // this.btnMoverLadron = crearBotonAccion("MOVER\nLADRÓN", new ControladorMoverLadron()); // Opcional

        acciones.add(this.btnConstruirPoblado, 0, 0);
        acciones.add(this.btnConstruirCamino, 1, 0);
        acciones.add(this.btnConstruirCiudad, 2, 0);
        acciones.add(this.btnBanca, 0, 1);
        acciones.add(this.btnIntercambioJugadores, 1, 1);
        acciones.add(this.btnJugarCarta, 2, 1);

        acciones.add(this.btnComprarCarta, 0, 2);
        acciones.add(this.btnMoverLadron, 2, 2);

        panel.setSpacing(30);
        panel.setAlignment(Pos.BOTTOM_CENTER);
        panel.getChildren().addAll(inventario, acciones);

        return panel;
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
            // Manejo silencioso si el juego no ha iniciado bien
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

        //  Si la luminancia es mayor a 0.5, el color es "claro" -> Texto NEGRO
        // Si es menor o igual, el color es "oscuro" -> Texto BLANCO
        // Usamos 0.55 punto medio
        return (luminancia > 0.55) ? Color.BLACK : Color.WHITE;
    }

    private StackPane crearDadoVisual(int valor) {
        //EL FONDO DEL DADO
        StackPane dado = new StackPane();
        dado.setPrefSize(60, 60); // Tamaño del dado

        javafx.scene.shape.Rectangle fondo = new javafx.scene.shape.Rectangle(60, 60);
        fondo.setArcWidth(15); // Redondez de esquinas
        fondo.setArcHeight(15);
        fondo.setFill(Color.web("#d9534f")); // Color ROJO (Igual a tus botones)
        fondo.setStroke(Color.WHITE); // Borde blanco
        fondo.setStrokeWidth(2);

        dado.getChildren().add(fondo);

        GridPane puntosGrid = new GridPane();
        puntosGrid.setAlignment(Pos.CENTER);
        puntosGrid.setHgap(5); // Espacio horizontal entre puntos
        puntosGrid.setVgap(5); // Espacio vertical entre puntos

        //posiciones lógicas de los puntos en una matriz 3x3
        // (columna, fila)
        // 0,0  1,0  2,0
        // 0,1  1,1  2,1
        // 0,2  1,2  2,2

        // PUNTOS SEGÚN EL NÚMERO
        // Centro (para 1, 3, 5)
        if (valor % 2 != 0) {
            puntosGrid.add(crearPunto(), 1, 1);
        }

        // Esquinas Superior-Izquierda e Inferior-Derecha (para 2, 3, 4, 5, 6)
        if (valor > 1) {
            puntosGrid.add(crearPunto(), 0, 0);
            puntosGrid.add(crearPunto(), 2, 2);
        }

        // Esquinas Superior-Derecha e Inferior-Izquierda (para 4, 5, 6)
        if (valor > 3) {
            puntosGrid.add(crearPunto(), 2, 0);
            puntosGrid.add(crearPunto(), 0, 2);
        }

        // Medio-Izquierda y Medio-Derecha (solo para 6)
        if (valor == 6) {
            puntosGrid.add(crearPunto(), 0, 1);
            puntosGrid.add(crearPunto(), 2, 1);
        }

        dado.getChildren().add(puntosGrid);
        return dado;
    }

    private javafx.scene.shape.Circle crearPunto() {
        javafx.scene.shape.Circle punto = new javafx.scene.shape.Circle(5); // Radio 5
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
        this.cartaSeleccionada = null; // Quitar selección
        actualizarInventario(); // Esto repintará todo bloqueado
    }

    // Auxiliar para no repetir código de habilitar botones
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
        //btnMoverLadron.setDisable(true);
        //if(btnTerminar != null) btnTerminar.setDisable(false);
    }



    // Método auxiliar para cargar imágenes de recursos y cartas
    private VBox crearFichaConImagen(String nombre, int cantidad, String nombreImagen, String colorFondoHex) {
        VBox ficha = new VBox(5);
        ficha.setPrefSize(80, 110);
        ficha.setAlignment(Pos.CENTER);
        ficha.setStyle("-fx-background-color: " + colorFondoHex + "; -fx-background-radius: 10; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 0);");

        StackPane contenedorImagen = new StackPane();
        contenedorImagen.setPrefSize(60, 60);

        try {
            java.net.URL url = getClass().getResource("/imagenes/" + nombreImagen);
            if (url != null) {
                Image img = new Image(url.toExternalForm());
                javafx.scene.image.ImageView imgView = new javafx.scene.image.ImageView(img);
                imgView.setFitWidth(50);
                imgView.setFitHeight(50);
                imgView.setPreserveRatio(true);
                contenedorImagen.getChildren().add(imgView);
            } else {
                Label lblNoImg = new Label(nombre.substring(0,1));
                lblNoImg.setTextFill(Color.WHITE);
                contenedorImagen.getChildren().add(lblNoImg);
            }
        } catch (Exception e) { }

        Label lblNombre = new Label(nombre);
        lblNombre.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px;");
        lblNombre.setEffect(new DropShadow(2, Color.BLACK));

        Label lblCantidad = new Label(String.valueOf(cantidad));
        lblCantidad.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18px;");
        lblCantidad.setEffect(new DropShadow(2, Color.BLACK));

        ficha.getChildren().addAll(contenedorImagen, lblNombre, lblCantidad);
        return ficha;
    }

    public void actualizarInventario() {
        if (this.contenedorRecursos == null || Catan.getInstance() == null) return;

        this.contenedorRecursos.getChildren().clear();
        this.contenedorCartasDesarrollo.getChildren().clear();

        Jugador jugadorActual;
        try {
            jugadorActual = this.enFaseInicial ?
                    Catan.getInstance().getManagerTurno().getJugadorActualInicial() :
                    Catan.getInstance().getManagerTurno().getJugadorActual();
        } catch(Exception e) { return; }


        if (this.lblNombreJugadorActual != null) {
            this.lblNombreJugadorActual.setText(jugadorActual.getNombre());
            try {
                this.lblNombreJugadorActual.setTextFill(Color.web(jugadorActual.getColor().getColor()));
            } catch(Exception e) {
                this.lblNombreJugadorActual.setTextFill(Color.WHITE);
            }
        }

        actualizarPanelJugadores();

        // ---LLENAR RECURSOS ---
        this.contenedorRecursos.getChildren().addAll(
                crearFichaConImagen("Madera",   jugadorActual.cantidadMadera(),   "madera.jpg",   "#228B22"),
                crearFichaConImagen("Ladrillo", jugadorActual.cantidadLadrillo(), "ladrilo.jpg", "#B22222"),
                crearFichaConImagen("Lana",     jugadorActual.cantidadLana(),     "lana.jpg",     "#7CB342"),
                crearFichaConImagen("Grano",    jugadorActual.cantidadGrano(),    "grano.jpg",    "#FFD700"),
                crearFichaConImagen("Mineral",   jugadorActual.cantidadMineral(),  "piedra.jpg",   "#708090")
        );


        boolean turnoHabilitado = !this.cartaDesarrolloJugadaEnTurno;

        this.contenedorCartasDesarrollo.getChildren().addAll(
                crearCartaSmart("Caballero", jugadorActual.cantidadCartasCaballero(), "caballero.jpg", "#A9A9A9",
                        turnoHabilitado && jugadorActual.tieneCartaHabilitada("Caballero")),

                crearCartaSmart("Monopolio", jugadorActual.cantidadCartasMonopolio(), "monopolio.jpg", "#90EE90",
                        turnoHabilitado && jugadorActual.tieneCartaHabilitada("Monopolio")),

                crearCartaSmart("Descubrimiento", jugadorActual.cantidadCartasDescubrimiento(), "descubrimiento.jpg", "#FFD700",
                        turnoHabilitado && jugadorActual.tieneCartaHabilitada("Descubrimiento")),

                crearCartaSmart("Carreteras", jugadorActual.cantidadCartasCarreteras(), "carreteras.jpg", "#FFD700",
                        turnoHabilitado && jugadorActual.tieneCartaHabilitada("Carreteras")),

                // Puntos de Victoria son pasivos, nunca se habilitan para click
                crearCartaSmart("Punto Vic.", jugadorActual.cantidadCartasPuntoVictoria(), "PV.jpg", "#FFD700", false)
        );

        verificarGanador();
    }

    private VBox crearCartaSmart(String nombre, int cantidad, String img, String color, boolean habilitada) {
        VBox carta = crearFichaConImagen(nombre, cantidad, img, color); // Tu método base

        // CASO 1: NO TIENE LA CARTA
        if (cantidad <= 0) {
            carta.setDisable(true);
            carta.setOpacity(0.3); // Muy transparente
            return carta;
        }

        // CASO 2: TIENE PERO NO PUEDE USAR (Nueva o ya jugó otra)
        if (!habilitada) {
            carta.setDisable(true); // No clickable
            carta.setOpacity(0.6); // Semi-transparente
            carta.setStyle(carta.getStyle() + "-fx-border-color: gray;"); // Borde gris
            return carta;
        }

        // CASO 3: DISPONIBLE
        carta.setCursor(Cursor.HAND);
        carta.setOnMouseClicked(e -> {
            // Lógica de Selección (Borde Amarillo)
            if (nombre.equals(this.cartaSeleccionada)) {
                this.cartaSeleccionada = null; // Deseleccionar
                carta.setStyle(carta.getStyle().replace("-fx-border-color: yellow;", "-fx-border-color: white;"));
            } else {
                this.cartaSeleccionada = nombre;
                // Limpiar hermanos
                ((javafx.scene.layout.HBox) carta.getParent()).getChildren().forEach(n ->
                        n.setStyle(n.getStyle().replace("-fx-border-color: yellow;", "-fx-border-color: white;"))
                );
                // Marcar esta
                carta.setStyle(carta.getStyle().replace("-fx-border-color: white;", "-fx-border-color: yellow;"));
            }
        });

        return carta;
    }
    private VBox crearCartaInteractiva(String nombre, int cantidad, String nombreImagen, String colorFondoHex, boolean turnoHabilitado) {

        // Reutilizamos tu método base de crear ficha
        VBox carta = crearFichaConImagen(nombre, cantidad, nombreImagen, colorFondoHex);

        if (cantidad <= 0) {
            carta.setDisable(true);
            carta.setOpacity(0.4); // Se ve transparente
            return carta;
        }

        //YA JUGUÉ CARTA ESTE TURNO (O es carta de PV)
        if (!turnoHabilitado) {
            carta.setDisable(true);
            carta.setOpacity(0.7); // Un poco más oscuro para indicar bloqueo temporal
            // Opcional: Tooltip explicando por qué
            return carta;
        }

        //DISPONIBLE PARA SELECCIONAR
        carta.setCursor(Cursor.HAND);
        carta.setOnMouseClicked(e -> {
            // Lógica de Selección Visual
            if (this.cartaSeleccionada != null && this.cartaSeleccionada.equals(nombre)) {
                // Deseleccionar si toco la misma
                this.cartaSeleccionada = null;
                carta.setStyle(carta.getStyle().replace("-fx-border-color: yellow;", "-fx-border-color: white;"));
            } else {
                // Seleccionar nueva
                this.cartaSeleccionada = nombre;

                // Limpiar borde de hermanos
                HBox padre = (HBox) carta.getParent();
                padre.getChildren().forEach(n -> {
                    n.setStyle(n.getStyle().replace("-fx-border-color: yellow;", "-fx-border-color: white;"));
                });
                // Poner borde amarillo
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

        // Set para evitar duplicados
        java.util.Set<Vertice> visitados = new java.util.HashSet<>();

        // Distancia extra desde el vértice hacia afuera
        double distanciaExtra = 25.0;

        for (Map.Entry<Coordenada, Vertice> entry : tablero.getMapaVertices().entrySet()) {
            Vertice v = entry.getValue();
            Coordenada coord = entry.getKey();

            if (v != null && v.esPuerto() && !visitados.contains(v)) {
                visitados.add(v);

                Terreno t = tablero.getTerrenos().get(coord.numHex());
                if (t == null) continue;

                Axial pos = t.getPosicion();
                double xCentro = hexRadius * Math.sqrt(3) * (pos.q + pos.r / 2.0);
                double yCentro = hexRadius * 1.5 * pos.r;

                int i = coord.indice();
                double angle = (Math.PI / 2) + i * (Math.PI / 3) + Math.PI;

                // Posición del Vértice (Donde iría la el poblado)
                double xVertice = xCentro + hexRadius * Math.cos(angle);
                double yVertice = yCentro + hexRadius * Math.sin(angle);

                //Posición del Puerto (Más afuera)
                double radioPuerto = hexRadius + distanciaExtra;
                double xPuerto = xCentro + radioPuerto * Math.cos(angle);
                double yPuerto = yCentro + radioPuerto * Math.sin(angle);

                // --- DIBUJAR ---

                // A. Línea conectora (Muelle)
                Line lineaConectora = new Line(xVertice, yVertice, xPuerto, yPuerto);
                lineaConectora.setStroke(Color.SADDLEBROWN);
                lineaConectora.setStrokeWidth(4);
                lineaConectora.setMouseTransparent(true);

                // B. Círculo del puerto (La plataforma)
                Circle plataforma = new Circle(xPuerto, yPuerto, 14);
                plataforma.setFill(Color.SADDLEBROWN);
                plataforma.setStroke(Color.WHITE);
                plataforma.setStrokeWidth(2);

                //  Lógica de Texto y Color según Tasa
                String texto = "?";
                Color colorTexto = Color.WHITE;

                try {
                    String desc = v.obtenerPoliticaDeIntercambio().toString().toLowerCase();

                    if (v.obtenerPoliticaDeIntercambio().tasa() == 3) {
                        texto = "3:1";
                    } else {
                        // Es 2:1, intentamos adivinar el recurso por el string o lo ponemos genérico
                        if (desc.contains("madera")) { texto = "Mad"; colorTexto = Color.LIGHTGREEN; }
                        else if (desc.contains("ladrillo")) { texto = "Lad"; colorTexto = Color.TOMATO; }
                        else if (desc.contains("lana")) { texto = "Lan"; colorTexto = Color.LIGHTGREEN; }
                        else if (desc.contains("grano")) { texto = "Gra"; colorTexto = Color.GOLD; }
                        else if (desc.contains("mineral")) { texto = "Min"; colorTexto = Color.LIGHTGRAY; }
                        else { texto = "2:1"; }
                    }
                } catch (Exception e) { texto = "P"; }

                Label lbl = new Label(texto);
                lbl.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                lbl.setTextFill(colorTexto);
                lbl.setTranslateX(xPuerto - 9);
                lbl.setTranslateY(yPuerto - 7);
                lbl.setMouseTransparent(true);

                grupoPuertos.getChildren().addAll(lineaConectora, plataforma, lbl);
            }
        }
    }

    private void mostrarLugaresPoblado() {
        grupoSugestiones.getChildren().clear();
        Tablero tablero = Catan.getInstance().getTablero();
        double hexRadius = 50;

        Map<Coordenada, Vertice> mapaVertices = tablero.getMapaVertices();

        for (Map.Entry<Coordenada, Vertice> entry : mapaVertices.entrySet()) {
            Coordenada coord = entry.getKey();
            Vertice v = entry.getValue();

            // Si el vértice es válido y no tiene nada construido
            if (v != null && !v.tieneConstruccion()) {


                Point2D posVisual = calcularPosicionVisual(coord, hexRadius);
                if (posVisual == null) continue; // Si no pudimos calcular (borde raro)

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
        double hexRadius = 50;

        Map<Coordenada, edu.fiuba.algo3.modelo.Tablero.Factory.Lado> mapaLados = tablero.getMapaLados();

        // Recuperar último poblado para la fase inicial
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
                    Point2D posVisual = calcularPosicionVisual(coord, 50); // 50 es tu hexRadius
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
        double hexRadius = 50;

        // Sets para evitar dibujar el mismo vértice/lado múltiples veces
        java.util.Set<Vertice> verticesVisitados = new java.util.HashSet<>();
        java.util.Set<edu.fiuba.algo3.modelo.Tablero.Factory.Lado> ladosVisitados = new java.util.HashSet<>();

        for (Terreno t : tablero.getTerrenos().values()) {
            Axial pos = t.getPosicion();
            double x = hexRadius * Math.sqrt(3) * (pos.q + pos.r / 2.0);
            double y = hexRadius * 1.5 * pos.r;

            // --- A. DIBUJAR POBLADOS Y CIUDADES ---
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

            // --- B. DIBUJAR CAMINOS ---
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
}
