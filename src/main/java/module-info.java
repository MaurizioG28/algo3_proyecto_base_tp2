module edu.fiuba.algo3 {
    requires javafx.controls;
    requires json.simple;
    requires org.apache.commons.lang3;
    exports edu.fiuba.algo3;
    exports edu.fiuba.algo3.vistas;
    exports edu.fiuba.algo3.modelo.Tablero;
    exports edu.fiuba.algo3.modelo;
    exports edu.fiuba.algo3.modelo.Mocks;
    exports edu.fiuba.algo3.modelo.Contruccion;
    exports edu.fiuba.algo3.modelo.Tablero.Factory;
}