package org.example;


import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import org.example.config.Config;
import org.example.data.ValueCells;
import org.example.entities.animals.*;
import org.example.models.ValueCell;

import java.util.List;

public class GameSolution extends Game {
    private int SIDE;
    private Config config;
    private EntitiesFabric entitiesFabric;
    private List<AbstractAnimal> animalsList;
    private AnimalMover animalMover;
    private ValueCells valueCells;

    @Override
    public void initialize() {
        config = Config.load();
        SIDE = config.sideField;
        entitiesFabric = new EntitiesFabric(config);
        animalsList = entitiesFabric.getAnimalsList();
        animalMover = new AnimalMover(config, animalsList);
        valueCells = new ValueCells(config);


        setScreenSize(SIDE, SIDE);
        drawScene(valueCells);

        setTurnTimer(config.turnTimer);


    }

    @Override
    public void onTurn(int step) {
        animalMover.move();
        clear();
        updateAnimalsOnField(animalsList);
    }

    private void clear() {
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                setCellValueEx(i, j, Color.WHITE, "");
            }
        }
    }


    private void updateValueCellsArray(ValueCells valueCells) {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                Color cellColor = getCellColor(x, y);
                String cellValue = getCellValue(x, y);
                valueCells.updateValueCell(x, y, cellColor, cellValue);
            }
        }
    }

    private void updateAnimalsOnField(List<AbstractAnimal> animals) {
        animals.forEach(animal -> {
            setCellValueEx(
                    animal.getCoordinates().x(),
                    animal.getCoordinates().y(),
                    Color.ALICEBLUE,
                    animal.getImage()
            );
        });
    }

    private void drawScene(ValueCells valueCells) {
        updateAnimalsOnField(animalsList);
        updateValueCellsArray(valueCells);
    }


    private void soutCells(ValueCells valueCells) {
        for (ValueCell[] valueCellsArray : valueCells.getValueCells()) {
            for (ValueCell valueCell : valueCellsArray) {
                System.out.println(
                        "x=" + valueCell.getCoordinates().x() +
                                " y=" + valueCell.getCoordinates().y() +
                                " color=" + valueCell.getColor() +
                                " value=" + valueCell.getValue()
                );
            }
        }
    }

}
