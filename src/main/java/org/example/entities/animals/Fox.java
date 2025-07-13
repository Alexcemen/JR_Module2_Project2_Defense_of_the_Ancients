package org.example.entities.animals;

import org.example.models.Coordinates;

public class Fox extends AbstractAnimal {
    public Fox(int health, int powerAttack, String image) {
        super(health, powerAttack, image);
    }

    @Override
    public AbstractAnimal copy() {
        Fox clone = new Fox(this.getHealth(), this.getPowerAttack(), this.getImage());
        clone.setCoordinates(new Coordinates(this.getCoordinates().x(), this.getCoordinates().y()));
        return clone;
    }
}
