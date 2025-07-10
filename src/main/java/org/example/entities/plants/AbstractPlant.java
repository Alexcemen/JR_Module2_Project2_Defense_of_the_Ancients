package org.example.entities.plants;

import org.example.models.Coordinates;

public class AbstractPlant {
    private Coordinates coordinates;
    private String image;

    public AbstractPlant(String image) {
        this.image = image;
    }
}
