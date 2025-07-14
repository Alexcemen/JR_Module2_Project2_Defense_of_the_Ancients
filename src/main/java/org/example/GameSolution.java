package org.example;


import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import org.example.command.AnimalAttacker;
import org.example.command.AnimalMover;
import org.example.entities.runes.AbstractRune;
import org.example.fabrics.AnimalsFabric;
import org.example.config.Config;
import org.example.data.ValueCells;
import org.example.entities.animals.*;
import org.example.fabrics.ExecutorsFabric;
import org.example.fabrics.RuneFabric;
import org.example.models.Faction;
import org.example.util.Field;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GameSolution extends Game {
    private int SIDE;
    private Config config;
    private AnimalsFabric animalsFabric;
    private List<AbstractAnimal> animalsList;
    private AnimalMover animalMover;
    private AnimalAttacker animalAttacker;
    private ValueCells valueCells;
    private RuneFabric runeFabric;
    private List<AbstractRune> runes;
    private ExecutorsFabric executorsFabric;

    private ScheduledFuture startRunesExecute;
    private ScheduledFuture startAnimalsExecute;
    private ScheduledFuture startDrawExecute;

    @Override
    public void initialize() {
        config = Config.load();
        SIDE = config.sideField;
        animalsFabric = new AnimalsFabric(config);
        animalsList = animalsFabric.getAnimalsList();
        animalMover = new AnimalMover(config, animalsList);
        animalAttacker = new AnimalAttacker(animalsList);
        valueCells = new ValueCells(config);
        runeFabric = new RuneFabric(config, animalsList);
        executorsFabric = new ExecutorsFabric();
        runes = runeFabric.getRunes();
        setScreenSize(SIDE, SIDE);
        startGame();
    }

    public void startGame() {
        startRunesExecute = startRunesExecute();
        startAnimalsExecute = startAnimals();
        startDrawExecute = startDraw();
        checker();
    }

    public void checker() {
        executorsFabric.getSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> {
                    int radiantAnimalsCount = 0;
                    int direAnimalsCount = 0;
                    for (AbstractAnimal abstractAnimal : animalsList) {
                        if (abstractAnimal.getFaction() == Faction.RADIANT) {
                            radiantAnimalsCount++;
                        } else {
                            direAnimalsCount++;
                        }
                    }
                    if (radiantAnimalsCount == 0) {
                        showMessageDialog(Color.BLACK, "Radiant Victory!", Color.WHITE, 90);
                        complete();
                    } else if (direAnimalsCount == 0) {
                        showMessageDialog(Color.BLACK, "Dire Victory!", Color.WHITE, 90);
                        complete();
                    }
                },
                0,
                50,
                TimeUnit.MILLISECONDS
        );
    }

    public void complete() {
        startRunesExecute.cancel(true);
        startRunesExecute.cancel(true);
        startDrawExecute.cancel(true);
    }

    public ScheduledFuture startAnimals() {
        return executorsFabric.getSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> {
                    for (AbstractAnimal animal : animalsList) {
                        animalMover.move(animal);
                        animalAttacker.attack(animal);

                        Iterator<AbstractRune> iterator = runes.iterator();
                        while (iterator.hasNext()) {
                            AbstractRune rune = iterator.next();
                            if (animal.getCoordinates().equals(rune.getCoordinates())) {
                                rune.setAnimal(animal);
                                rune.execute();
                                iterator.remove();
                            }
                        }
                    }
                },
                0,
                100,
                TimeUnit.MILLISECONDS
        );
    }

    public ScheduledFuture startDraw() {
        return executorsFabric.getSingleThreadScheduledExecutor().scheduleAtFixedRate(
                this::drawScene,
                0,
                1000,
                TimeUnit.MILLISECONDS
        );
    }

    public ScheduledFuture startRunesExecute() {
        return executorsFabric.getSingleThreadScheduledExecutor().scheduleAtFixedRate(
                runeFabric.updateListRunes(),
                0,
                10,
                TimeUnit.SECONDS);
    }

    private void drawScene() {
        drawMap();
        updateRunesOnField();
        updateAnimalsOnField();
        updateValueCellsArray();
    }

    private void drawMap() {
        Color[][] map = Field.getMap();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                String value = "";
                if (map[y][x] == Color.DARKOLIVEGREEN) {
                    value = "\uD83C\uDF33";
                } else if (map[y][x] == Color.DARKSLATEGRAY) {
                    value = "\uD83C\uDF32";
                }
                setCellValueEx(x, y, map[y][x], value);
            }
        }
        setCellValue(0, 19, "\uD83C\uDFF0");//база radiant
        setCellValue(19, 0, "\uD83C\uDFEF");//база dark
        setCellValue(4, 8, "\uD83C\uDFD5");//палатка
        setCellValue(16, 12, "\uD83C\uDFD5");

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                setCellTextSize(x, y, 90);
            }
        }
    }


    private void updateAnimalsOnField() {
        animalsList.forEach(animal -> {
            setCellValueEx(
                    animal.getCoordinates().x(),
                    animal.getCoordinates().y(),
                    getHealthColor(animal),
                    animal.getImage(),
                    Color.AQUA,
                    90
            );
        });
    }

    private void updateRunesOnField() {
        runes.forEach(rune -> {
            setCellValueEx(
                    rune.getCoordinates().x(),
                    rune.getCoordinates().y(),
                    Color.DARKCYAN,
                    rune.getImage(),
                    Color.AQUA,
                    90
            );
        });
    }

    private void updateValueCellsArray() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                Color cellColor = getCellColor(x, y);
                String cellValue = getCellValue(x, y);
                valueCells.updateValueCell(x, y, cellColor, cellValue);
            }
        }
    }

    private Color getHealthColor(AbstractAnimal animal) {
        int health = animal.getHealth();
        int group = health / 10;
        if (animal.getFaction() == Faction.RADIANT) {
            return switch (group) {
                case 9 -> Color.GREENYELLOW;
                case 8 -> Color.YELLOWGREEN;
                case 7 -> Color.YELLOW;
                case 6 -> Color.GOLD;
                case 5 -> Color.ORANGE;
                case 4 -> Color.DARKORANGE;
                case 3 -> Color.ORANGERED;
                case 2 -> Color.CRIMSON;
                default -> Color.LIME;
            };
        } else {
            return switch (group) {
                case 9 -> Color.DARKSEAGREEN;
                case 8 -> Color.OLIVEDRAB;
                case 7 -> Color.GOLDENROD;
                case 6 -> Color.DARKGOLDENROD;
                case 5 -> Color.PERU;
                case 4 -> Color.FIREBRICK;
                case 3 -> Color.DARKRED;
                case 2 -> Color.MAROON;
                default -> Color.DARKOLIVEGREEN;
            };
        }
    }
}
