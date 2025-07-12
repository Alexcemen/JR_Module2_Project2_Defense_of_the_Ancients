package org.example.command;

import org.example.config.Config;
import org.example.data.ValueCells;
import org.example.entities.animals.AbstractAnimal;
import org.example.models.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class AnimalInteraction {

    private Config config;
    private AbstractAnimal animal;
    private ValueCells valueCells;
    private List<AbstractAnimal> animals;

    public AnimalInteraction(Config config,
                             ValueCells valueCells,
                             AbstractAnimal animal,
                             List<AbstractAnimal> animals) {
        this.config = config;
        this.valueCells = valueCells;
        this.animal = animal;
        this.animals = animals;
    }

    public List<AbstractAnimal> findTargetsForAttack() {
        List<AbstractAnimal> targets = new ArrayList<>();
        int currentX = animal.getCoordinates().x();
        int currentY = animal.getCoordinates().y();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j < 1; j++) {
                if (i == 0 && j == 0) continue;
                Coordinates coordinatesTarget = new Coordinates(currentX + i, currentY + j);
                AbstractAnimal animalTarget = findAnimal(coordinatesTarget);
                if (animalTarget != null && !animalTarget.getImage().equals(animal.getImage())) {
                    targets.add(animalTarget);
                }
            }
        }
        return targets;
    }

    private AbstractAnimal findAnimal(Coordinates neighboringCellCoordinate) {
        return animals.stream()
                .filter(animal -> animal.getCoordinates().equals(neighboringCellCoordinate))
                .findFirst()
                .orElse(null);
    }
}
