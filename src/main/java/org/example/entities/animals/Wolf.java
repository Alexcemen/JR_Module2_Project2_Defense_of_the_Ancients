package org.example.entities.animals;

import org.example.models.Coordinates;

public class Wolf extends AbstractAnimal {
    public Wolf(int health, int powerAttack, String image) {
        super(health, powerAttack, image);
    }

    @Override
    public AbstractAnimal copy() {
        Wolf clone = new Wolf(this.getHealth(), this.getPowerAttack(), this.getImage());
        clone.setCoordinates(new Coordinates(this.getCoordinates().x(), this.getCoordinates().y()));
        return clone;
    }
}
