package org.example.command;

import org.example.entities.animals.AbstractAnimal;
import org.example.models.Coordinates;
import org.example.models.Gender;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/*
Этот класс отвечает за размножение
Должны быть выполнены условия:
1. 2 животных одного вида и разного пола на соседних клетках
2. У одной самки может быть максимум 3 детей
3. Вероятность рождения 20%
 */

public class AnimalMultiply {
    private List<AbstractAnimal> animals;

    public AnimalMultiply(List<AbstractAnimal> animals) {
        this.animals = animals;
    }

    public void multiply(AbstractAnimal animal) {
        List<AbstractAnimal> targets = findTargets(animal, animals);
        if (!targets.isEmpty() && animal.getGender() == Gender.FEMALE && animal.getCountPossibleChildren() > 0) {
            int probability = ThreadLocalRandom.current().nextInt(5);
            if (probability == 1) {
                animals.add(createCopy(animal));
            }
        }
    }

    private List<AbstractAnimal> findTargets(AbstractAnimal animal, List<AbstractAnimal> animals) {
        List<AbstractAnimal> targets = new ArrayList<>();
        int currentX = animal.getCoordinates().x();
        int currentY = animal.getCoordinates().y();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j < 1; j++) {
                if (i == 0 && j == 0) continue;
                Coordinates coordinatesTarget = new Coordinates(currentX + i, currentY + j);
                AbstractAnimal animalTarget = findAnimal(coordinatesTarget, animals);
                if (animalTarget != null && animalTarget.getImage().equals(animal.getImage())) {
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

    private AbstractAnimal createCopy(AbstractAnimal animal) {
        AbstractAnimal copy = animal.copy();
        //оставить координаты такими же, как у матери
        //когда будут делать ход -> животные разойдутся
        animal.setCountPossibleChildren(animal.getCountPossibleChildren()-1);
        return copy;
    }
}
