package org.example.entities.runes;

import org.example.config.Config;
import org.example.entities.animals.AbstractAnimal;
import org.example.models.Coordinates;

public class HealingRune extends AbstractRune {
    public HealingRune(Coordinates coordinates, AbstractAnimal animal, Config config) {
        super(coordinates, config.healingRuneImage, animal, config);
    }

    @Override
    public void execute() {
        getAnimal().setHealth(
                getAnimal().getHealth() + getConfig().healingRuneExecute
        );
    }
}
