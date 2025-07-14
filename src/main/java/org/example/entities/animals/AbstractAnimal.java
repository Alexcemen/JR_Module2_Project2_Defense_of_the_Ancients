package org.example.entities.animals;

import org.example.models.Coordinates;
import org.example.models.Faction;


public abstract class AbstractAnimal {
    private Coordinates coordinates;
    private int health;
    private int powerAttack;
    private final String image;
    private final Faction faction;

    public AbstractAnimal(int health, int powerAttack, String image, Faction faction) {
        this.health = health;
        this.powerAttack = powerAttack;
        this.image = image;
        this.faction = faction;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Faction getFaction() {
        return faction;
    }

    public int getPowerAttack() {
        return powerAttack;
    }

    public void setPowerAttack(int powerAttack) {
        this.powerAttack = powerAttack;
    }

    public String getImage() {
        return image;
    }

    public abstract AbstractAnimal copy();
}
