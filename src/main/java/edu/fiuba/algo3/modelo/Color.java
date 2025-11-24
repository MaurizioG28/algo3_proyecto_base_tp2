package edu.fiuba.algo3.modelo;

import java.util.Objects;

public class Color {
    private final String color;

    public Color(String color) {
        this.color = color.trim().toLowerCase();
    }

    public String getColor() {
        return this.color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Color)) return false;
        Color otro = (Color) o;
        return Objects.equals(color, otro.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    @Override
    public String toString() {
        return color;
    }

    public boolean esMismoColor(String color) {
        String colorFormateado = color.trim().toLowerCase();
        return this.color.equals(colorFormateado);
    }
    public boolean esMismoColor(Color colorFormateado) {
        return (this.color.equals(colorFormateado.getColor()));
    }
}
