package org.example.models;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT;


    public static Direction getDirection(int number) {
        return Direction.values()[number];
    }
}




