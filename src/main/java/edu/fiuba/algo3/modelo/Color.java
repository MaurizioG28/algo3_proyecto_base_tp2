package edu.fiuba.algo3.modelo;

public class Color {
    private final String color;

    public Color(String color) {
        this.color = color.trim().toLowerCase();
    }

    public String getColor() {
        return this.color;
    }

    public boolean esIgual(Color otroColor) {
        return this.equals(otroColor);
    }

}
