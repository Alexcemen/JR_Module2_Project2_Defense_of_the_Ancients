package org.example.entities.animals;

import org.example.models.Coordinates;
import org.example.models.Gender;

import java.util.concurrent.ThreadLocalRandom;


public abstract class AbstractAnimal {
    private Coordinates coordinates;
    private int health;
    private int powerAttack;
    private String image;
    private Gender gender;
    private int countPossibleChildren;

    public AbstractAnimal(int health, int powerAttack, String image) {
        this.health = health;
        this.powerAttack = powerAttack;
        this.image = image;
        gender = Gender.values()[ThreadLocalRandom.current().nextInt(2)];
        countPossibleChildren = 3;
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

    public int getPowerAttack() {
        return powerAttack;
    }

    public void setPowerAttack(int powerAttack) {
        this.powerAttack = powerAttack;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Gender getGender() {
        return gender;
    }

    public int getCountPossibleChildren() {
        return countPossibleChildren;
    }

    public void setCountPossibleChildren(int countPossibleChildren) {
        this.countPossibleChildren = countPossibleChildren;
    }

    public abstract AbstractAnimal copy();
}
