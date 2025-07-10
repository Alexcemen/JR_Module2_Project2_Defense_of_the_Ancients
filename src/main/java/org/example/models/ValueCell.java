package org.example.models;

import com.javarush.engine.cell.Color;

public class ValueCell {
    private Coordinates coordinates;
    private Color color;
    private String value;

    public ValueCell(int x, int y, Color color, String value) {
        this.coordinates = new Coordinates(x, y);
        this.color = color;
        this.value = value;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
