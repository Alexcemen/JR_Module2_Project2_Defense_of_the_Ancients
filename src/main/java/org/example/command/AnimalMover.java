package org.example.command;

import org.example.config.Config;
import org.example.entities.animals.AbstractAnimal;
import org.example.models.Coordinates;
import org.example.models.Direction;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalMover {

    private Config config;
    private List<AbstractAnimal> animals;

    public AnimalMover(Config config, List<AbstractAnimal> animals) {
        this.config = config;
        this.animals = animals;
    }

    public void move() {
        for (AbstractAnimal animal : animals) {
            Coordinates newCoordinates;
            do {
                newCoordinates = getNewCoordinates(animal.getCoordinates());
            } while (!isCorrectCoordinates(newCoordinates) || !isCellEmpty(newCoordinates));
            animal.setCoordinates(newCoordinates);
        }
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
        if (coordinates.x() < 0
                || coordinates.x() >= config.sideField
                || coordinates.y() < 0
                || coordinates.y() >= config.sideField
        ) {
            return false;
        } else {
            return true;
        }
    }

    private Boolean isCellEmpty(Coordinates coordinates) {
        return animals.stream()
                .noneMatch(animal -> animal.getCoordinates().equals(coordinates));
    }

    private Boolean isSameCoordinates(Coordinates currentCoordinates, Coordinates newCoordinates) {
        if (currentCoordinates == newCoordinates) {
            return true;
        } else {
            return false;
        }
    }
}
