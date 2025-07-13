package org.example.entities.runes;

import org.example.config.Config;
import org.example.entities.animals.AbstractAnimal;
import org.example.models.Coordinates;

public class PoisonRune extends AbstractRune {
    public PoisonRune(Coordinates coordinates, AbstractAnimal animal, Config config) {
        super(coordinates, config.poisonRuneImage, animal, config);
    }

    @Override
    public void execute() {
        getAnimal().setHealth(
                getAnimal().getHealth() - getConfig().poisonRuneExecute
        );
    }
}
