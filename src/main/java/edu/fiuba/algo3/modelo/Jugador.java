package edu.fiuba.algo3.modelo;

import java.util.List;

public class Jugador {
    int MADERA = 0;
    int LADRILLO = 0;
    int LANA= 0;
    int CEREAL= 0;
    int MINERAL= 0;

    // maderaReq, ladrilloReq, lanaReq, cerealReq, mineralReq
    public boolean tiene(int madera, int ladrillo, int lana, int cereal, int mineral) {
        return  MADERA   >= madera   &&
                LADRILLO >= ladrillo &&
                LANA     >= lana     &&
                CEREAL   >= cereal   &&
                MINERAL  >= mineral;
    }

    public void sumarRecursos(List<Recurso> recursos) {
        for (Recurso recurso : recursos) {
            switch (recurso) {
                case MADERA:
                    MADERA++;
                    break;
                case LADRILLO:
                    LADRILLO++;
                    break;
                case LANA:
                    LANA++;
                    break;
                case CEREAL:
                    CEREAL++;
                    break;
                case MINERAL:
                    MINERAL++;
                    break;
            }
        }
    }

}
