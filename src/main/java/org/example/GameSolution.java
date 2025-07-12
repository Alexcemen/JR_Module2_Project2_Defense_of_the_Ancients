package org.example;


import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import org.example.command.AnimalMover;
import org.example.command.EntitiesFabric;
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
        setTurnTimer(config.turnTimer);
        draw();
    }

    @Override
    public void onTurn(int step) {
        animalMover.move();

        for (AbstractAnimal animal : animalsList) {
            animal.attack(animal, animalsList);
            System.out.println(animal.getHealth());
        }

    }

    public void draw() {
        new Thread(() -> updateScene()).start();
    }

    private void updateScene() {
        while (true) {
            clear();
            drawScene(valueCells, 1);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clear();
            drawScene(valueCells, 2);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void clear() {
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                setCellValueEx(i, j, Color.WHITE, "");
            }
        }
    }

    private void drawScene(ValueCells valueCells, int n) {
        updateAnimalsOnField(animalsList, n);
        updateValueCellsArray(valueCells);
    }

    private void updateAnimalsOnField(List<AbstractAnimal> animals, int colorNumber) {
        Color color;
        if (colorNumber == 1) {
            color = Color.PINK;
        } else {
            color = Color.AQUA;
        }
        animals.forEach(animal -> {
            setCellValueEx(
                    animal.getCoordinates().x(),
                    animal.getCoordinates().y(),
                    color,
                    animal.getImage()
            );
        });
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
