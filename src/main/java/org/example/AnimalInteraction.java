package org.example;

import org.example.config.Config;
import org.example.data.ValueCells;
import org.example.entities.animals.AbstractAnimal;
import org.example.models.Coordinates;
import org.example.models.ValueCell;

import java.util.List;

public class AnimalInteraction {

    private Config config;
    private List<AbstractAnimal> animals;
    private ValueCells valueCells;

    public AnimalInteraction(Config config, ValueCells valueCells, List<AbstractAnimal> animals) {
        this.config = config;
        this.valueCells = valueCells;
        this.animals = animals;
    }

    public Boolean isThereEntity(Coordinates neighboringCellCoordinate) {
        return !valueCells.getValueCell(neighboringCellCoordinate).getValue().isEmpty();
    }
}
