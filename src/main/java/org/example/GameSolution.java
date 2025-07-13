package org.example;


import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import org.example.command.AnimalAttacker;
import org.example.command.AnimalMover;
import org.example.command.AnimalMultiply;
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
    private AnimalAttacker animalAttacker;
    private AnimalMultiply animalMultiply;
    private ValueCells valueCells;

    @Override
    public void initialize() {
        config = Config.load();
        SIDE = config.sideField;
        entitiesFabric = new EntitiesFabric(config);
        animalsList = entitiesFabric.getAnimalsList();
        animalMover = new AnimalMover(config, animalsList);
        animalAttacker = new AnimalAttacker(animalsList);
        animalMultiply = new AnimalMultiply(animalsList);
        valueCells = new ValueCells(config);
        setScreenSize(SIDE, SIDE);
        setTurnTimer(config.turnTimer);
        draw();
    }

    @Override
    public void onTurn(int step) {
        //заменить цикл на executor
        for (AbstractAnimal animal : animalsList) {
            animalMover.move(animal);
            animalAttacker.attack(animal);
            animalMultiply.multiply(animal);
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
        //Татьяна, когда я писал вам сообщение, я хотел, чтобы этот метод отрисовывал поле
        //и каждые 100 millis менял цвет фона
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
                    getHealthColor(animal.getHealth()), //эту строчку нужно заменить на color
//                    color,
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

    private Color getHealthColor(int health) {
        int group = health / 10;
        return switch (group) {
            case 10 -> Color.LIME;
            case 9 -> Color.GREENYELLOW;
            case 8 -> Color.YELLOWGREEN;
            case 7 -> Color.YELLOW;
            case 6 -> Color.GOLD;
            case 5 -> Color.ORANGE;
            case 4 -> Color.DARKORANGE;
            case 3, 2 -> Color.ORANGERED;
            default -> Color.RED;
        };
    }
}
