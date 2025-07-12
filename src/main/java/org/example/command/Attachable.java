package org.example.command;

import org.example.entities.animals.AbstractAnimal;
import org.example.models.Coordinates;

import java.util.ArrayList;
import java.util.List;

public interface Attachable {
    default void attack(AbstractAnimal animal, List<AbstractAnimal> animals) {
        int powerAttack = animal.getPowerAttack();
        List<AbstractAnimal> targetsForAttack = findTargetsForAttack(animal, animals);
        if (!targetsForAttack.isEmpty()) {
            for (AbstractAnimal target : targetsForAttack) {
                takeDamage(powerAttack, target);
            }
        }
    }

    default List<AbstractAnimal> findTargetsForAttack(AbstractAnimal animal, List<AbstractAnimal> animals) {
        List<AbstractAnimal> targets = new ArrayList<>();
        int currentX = animal.getCoordinates().x();
        int currentY = animal.getCoordinates().y();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j < 1; j++) {
                if (i == 0 && j == 0) continue;
                Coordinates coordinatesTarget = new Coordinates(currentX + i, currentY + j);
                AbstractAnimal animalTarget = findAnimal(coordinatesTarget, animals);
                if (animalTarget != null && !animalTarget.getImage().equals(animal.getImage())) {
                    targets.add(animalTarget);
                }
            }
        }
        return targets;
    }

    private AbstractAnimal findAnimal(Coordinates neighboringCellCoordinate, List<AbstractAnimal> animals) {
        return animals.stream()
                .filter(animal -> animal.getCoordinates().equals(neighboringCellCoordinate))
                .findFirst()
                .orElse(null);
    }

    private void takeDamage(int powerAttack, AbstractAnimal target) {
        target.setHealth(target.getHealth() - powerAttack);
    }
}
