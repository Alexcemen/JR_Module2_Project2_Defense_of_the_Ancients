package org.example.entities.runes;

import org.example.config.Config;
import org.example.entities.animals.AbstractAnimal;
import org.example.models.Coordinates;

import java.util.List;

public class CloningRune extends AbstractRune {
    private List<AbstractAnimal> animals;

    public CloningRune(Coordinates coordinates,
                       AbstractAnimal animal,
                       Config config,
                       List<AbstractAnimal> animals) {
        super(coordinates, config.cloningRuneImage, animal, config);
        this.animals = animals;
    }

    @Override
    public void execute() {
        animals.add(getAnimal().copy());
    }
}
