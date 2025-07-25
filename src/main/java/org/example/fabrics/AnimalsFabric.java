package org.example.fabrics;

import org.example.config.Config;
import org.example.entities.animals.*;
import org.example.models.Animals;
import org.example.models.Coordinates;
import org.example.models.Faction;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalsFabric {

    private List<AbstractAnimal> animals;
    private final Config config;

    public AnimalsFabric(Config config) {
        this.config = config;
        animals = createListAnimals();
    }

    public List<AbstractAnimal> createListAnimals() {
        animals = new CopyOnWriteArrayList<>();
        Set<Coordinates> randomCoordinatesSet = getRandomCoordinatesSet();
        animals.addAll(getRadiantAnimals());
        animals.addAll(getDireAnimals());
        int index = 0;
        for (Coordinates coordinates : randomCoordinatesSet) {
            animals.get(index).setCoordinates(coordinates);
            index++;
        }
        return animals;
    }

    public List<AbstractAnimal> getAnimalsList() {
        return animals;
    }

    private List<AbstractAnimal> getRadiantAnimals() {
        List<AbstractAnimal> animals = new CopyOnWriteArrayList<>();
        for (int i = 0; i < config.startCountAnimals / 2; i++) {
            AbstractAnimal randomAnimal = getRandomAnimal(Faction.RADIANT);
            animals.add(randomAnimal);
        }
        return animals;
    }

    private List<AbstractAnimal> getDireAnimals() {
        List<AbstractAnimal> animals = new CopyOnWriteArrayList<>();
        for (int i = 0; i < config.startCountAnimals / 2; i++) {
            AbstractAnimal randomAnimal = getRandomAnimal(Faction.DIRE);
            animals.add(randomAnimal);
        }
        return animals;
    }

    private AbstractAnimal getRandomAnimal(Faction faction) {
        return switch (getRandomAnimalEnum()) {
            case BEAR -> new Bear(config.animalHealth, config.animalPowerAttack, config.bearImage, faction);
            case FOX -> new Fox(config.animalHealth, config.animalPowerAttack, config.foxImage, faction);
            case TIGER -> new Tiger(config.animalHealth, config.animalPowerAttack, config.tigerImage, faction);
            case WOLF -> new Wolf(config.animalHealth, config.animalPowerAttack, config.wolfImage, faction);
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
}
