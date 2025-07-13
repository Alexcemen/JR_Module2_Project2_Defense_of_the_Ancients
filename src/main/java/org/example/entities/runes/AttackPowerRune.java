package org.example.entities.runes;

import org.example.config.Config;
import org.example.entities.animals.AbstractAnimal;
import org.example.models.Coordinates;

public class AttackPowerRune extends AbstractRune {
    public AttackPowerRune(Coordinates coordinates, AbstractAnimal animal, Config config) {
        super(coordinates, config.powerAttackRuneImage, animal, config);
    }

    @Override
    public void execute() {
        getAnimal().setPowerAttack(
                getAnimal().getPowerAttack() + getConfig().powerAttackRuneExecute
        );
    }
}
