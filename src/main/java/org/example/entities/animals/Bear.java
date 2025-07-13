package org.example.entities.animals;

import org.example.models.Coordinates;

public class Bear extends AbstractAnimal {
    public Bear(int health, int powerAttack, String image) {
        super(health, powerAttack, image);
    }

    @Override
    public AbstractAnimal copy() {
        Bear clone = new Bear(this.getHealth(), this.getPowerAttack(), this.getImage());
        clone.setCoordinates(new Coordinates(this.getCoordinates().x(), this.getCoordinates().y()));
        return clone;
    }
}
