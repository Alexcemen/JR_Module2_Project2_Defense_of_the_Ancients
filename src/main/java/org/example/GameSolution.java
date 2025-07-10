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
    private AnimalMover animalMover;
    private List<AbstractAnimal> animals;


    @Override
    public void initialize() {
        Config config = Config.load();
        SIDE = config.sideField;
//        valueCellsArray = new ValueCell[SIDE][SIDE];
        setScreenSize(SIDE, SIDE);
        ValueCells valueCells = new ValueCells(config);

        EntitiesFabric entitiesFabric = new EntitiesFabric(config);
        animals = entitiesFabric.createListAnimals();
        animalMover = new AnimalMover(config, animals);
        updateValueCellsArray(valueCells);

//        setTurnTimer(config.turnTimer);



//        for (ValueCell[] valueCells : valueCellsArray) {
//            for (ValueCell valueCell : valueCells) {
//                System.out.println(
//                        "x=" + valueCell.getCoordinates().x() +
//                                " y=" + valueCell.getCoordinates().y() +
//                                " color=" + valueCell.getColor() +
//                                " value=" + valueCell.getValue()
//                );
//            }
//        }

    }

//    @Override
//    public void onTurn(int step) {
//        animalMover.move();
//        clear();
//        drawAnimals(animals);
//    }

//    private void clear() {
//        for (int i = 0; i < gameField.length; i++) {
//            for (int j = 0; j < gameField.length; j++) {
//                setCellValueEx(i, j, Color.WHITE, "");
//            }
//        }
//    }


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
        updateAnimalsOnField(animals);
        updateValueCellsArray(valueCells);
    }


}
