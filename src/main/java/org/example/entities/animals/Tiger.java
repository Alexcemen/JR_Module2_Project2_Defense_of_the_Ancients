package org.example.entities.animals;

import org.example.models.Coordinates;
import org.example.models.Faction;

public class Tiger extends AbstractAnimal {
    public Tiger(int health, int powerAttack, String image, Faction faction) {
        super(health, powerAttack, image, faction);
    }

    @Override
    public AbstractAnimal copy() {
        Tiger clone = new Tiger(this.getHealth(), this.getPowerAttack(), this.getImage(), this.getFaction());
        clone.setCoordinates(new Coordinates(this.getCoordinates().x(), this.getCoordinates().y()));
        return clone;
    }
}
