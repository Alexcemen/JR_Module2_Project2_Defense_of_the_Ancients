package org.example;

import org.example.config.Config;
import org.example.entities.animals.*;
import org.example.models.Animals;
import org.example.models.Coordinates;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class EntitiesFabric {

    private List<AbstractAnimal> animals;
    private final Config config;

    public EntitiesFabric(Config config) {
        this.config = config;
        animals = createListAnimals();
    }

    public List<AbstractAnimal> createListAnimals() {
        animals = new CopyOnWriteArrayList<>();
        Set<Coordinates> randomCoordinatesSet = getRandomCoordinatesSet();

        for (int i = 0; i < config.startCountAnimals; i++) {
            AbstractAnimal randomAnimal = getRandomAnimal();
            randomAnimal.setCoordinates(pollOne(randomCoordinatesSet));
            animals.add(randomAnimal);
        }
        return animals;
    }

    public List<AbstractAnimal> getAnimalsList() {
        return animals;
    }

    private AbstractAnimal getRandomAnimal() {
        return switch (getRandomAnimalEnum()) {
            case BEAR -> new Bear(config.animalHealth, config.animalPowerAttack, config.bearImage);
            case FOX -> new Fox(config.animalHealth, config.animalPowerAttack, config.foxImage);
            case TIGER -> new Tiger(config.animalHealth, config.animalPowerAttack, config.tigerImage);
            case WOLF -> new Wolf(config.animalHealth, config.animalPowerAttack, config.wolfImage);
        };
    }

    private Animals getRandomAnimalEnum() {
        int countAnimalSpecies = Animals.values().length;
        int numberRandomAnimal = ThreadLocalRandom.current().nextInt(countAnimalSpecies);
        return Animals.values()[numberRandomAnimal];
    }

    private Set<Coordinates> getRandomCoordinatesSet() {
        Set<Coordinates> coordinatesSet = new HashSet<>();
        //добавляем в сет столько координатов, сколько будет животных.
        //сет для того, чтобы стартовые координаты не повторялись
        while (coordinatesSet.size() < config.startCountAnimals) {
            coordinatesSet.add(new Coordinates(
                            ThreadLocalRandom.current().nextInt(config.sideField),
                            ThreadLocalRandom.current().nextInt(config.sideField)
                    )
            );
        }
        return coordinatesSet;
    }

    private Coordinates pollOne(Set<Coordinates> coordinatesSet) {
        Iterator<Coordinates> iterator = coordinatesSet.iterator();
        if (iterator.hasNext()) {
            Coordinates coordinates = iterator.next();
            iterator.remove();
            return coordinates;
        }
        throw new NoSuchElementException("Set is empty — nothing to poll!");
    }
}
