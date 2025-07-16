package org.example.command;

import org.example.config.Config;
import org.example.entities.animals.AbstractAnimal;
import org.example.models.Coordinates;
import org.example.models.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalMover {

    private static final Logger log = LoggerFactory.getLogger(AnimalMover.class);
    private final Config config;
    private final List<AbstractAnimal> animals;

    public AnimalMover(Config config, List<AbstractAnimal> animals) {
        this.config = config;
        this.animals = animals;
    }

    public void move(AbstractAnimal animal) {
        Coordinates newCoordinates;
        do {
            newCoordinates = getNewCoordinates(animal.getCoordinates());
        } while (!isCorrectCoordinates(newCoordinates) || !isCellEmpty(newCoordinates));
        animal.setCoordinates(newCoordinates);
//        log.info("Animal " + animal + " пошел. Поток: " + Thread.currentThread().getName());
    }

    private Coordinates getNewCoordinates(Coordinates currentCoordinates) {
        Direction direction = getDirection();
        int currentX = currentCoordinates.x();
        int currentY = currentCoordinates.y();
        return switch (direction) {
            case DOWN -> new Coordinates(currentX, currentY + 1);
            case UP -> new Coordinates(currentX, currentY - 1);
            case LEFT -> new Coordinates(currentX - 1, currentY);
            case RIGHT -> new Coordinates(currentX + 1, currentY);
            case TOP_LEFT -> new Coordinates(currentX - 1, currentY - 1);
            case TOP_RIGHT -> new Coordinates(currentX + 1, currentY - 1);
            case BOTTOM_LEFT -> new Coordinates(currentX - 1, currentY + 1);
            case BOTTOM_RIGHT -> new Coordinates(currentX + 1, currentY + 1);
        };
    }

    private Direction getDirection() {
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 8);
        return Direction.getDirection(randomNumber);
    }

    private Boolean isCorrectCoordinates(Coordinates coordinates) {
        return coordinates.x() >= 0
                && coordinates.x() < config.sideField
                && coordinates.y() >= 0
                && coordinates.y() < config.sideField;
    }

    private Boolean isCellEmpty(Coordinates coordinates) {
        return animals.stream()
                .noneMatch(animal -> animal.getCoordinates().equals(coordinates));
    }
}