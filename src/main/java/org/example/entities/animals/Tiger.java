package org.example.entities.animals;

import org.example.models.Coordinates;

public class Tiger extends AbstractAnimal {
    public Tiger(int health, int powerAttack, String image) {
        super(health, powerAttack, image);
    }

    @Override
    public AbstractAnimal copy() {
        Tiger clone = new Tiger(this.getHealth(), this.getPowerAttack(), this.getImage());
        clone.setCoordinates(new Coordinates(this.getCoordinates().x(), this.getCoordinates().y()));
        return clone;
    }
}
