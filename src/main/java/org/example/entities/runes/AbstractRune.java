package org.example.entities.runes;

import org.example.config.Config;
import org.example.entities.animals.AbstractAnimal;
import org.example.models.Coordinates;

public abstract class AbstractRune {
    private Coordinates coordinates;
    private final String image;
    private AbstractAnimal animal;
    private Config config;

    public AbstractRune(Coordinates coordinates,
                        String image,
                        AbstractAnimal animal,
                        Config config) {
        this.coordinates = coordinates;
        this.image = image;
        this.animal = animal;
        this.config = config;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getImage() {
        return image;
    }

    public AbstractAnimal getAnimal() {
        return animal;
    }

    public void setAnimal(AbstractAnimal animal) {
        this.animal = animal;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    abstract public void execute();
}
