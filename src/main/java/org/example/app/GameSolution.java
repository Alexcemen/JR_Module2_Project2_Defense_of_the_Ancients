package org.example.app;


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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GameSolution extends Game {
    private final static Logger log = LoggerFactory.getLogger(GameSolution.class);
    private int SIDE;
    private Config config;
    private AnimalsFabric animalsFabric;
    private List<AbstractAnimal> animals;
    private AnimalMover animalMover;
    private AnimalAttacker animalAttacker;
    private ValueCells valueCells;
    private RuneFabric runeFabric;
    private List<AbstractRune> runes;
    private ExecutorsFabric executorsFabric;

    private ScheduledFuture runesLogicExecutor;
    private ScheduledFuture animalLogicExecutor;
    private ScheduledFuture drawLogicExecutor;

    @Override
    public void initialize() {
        config = Config.load();
        SIDE = config.sideField;
        animalsFabric = new AnimalsFabric(config);
        animals = animalsFabric.getAnimalsList();
        animalMover = new AnimalMover(config, animals);
        animalAttacker = new AnimalAttacker(animals);
        valueCells = new ValueCells(config);
        runeFabric = new RuneFabric(config, animals);
        executorsFabric = new ExecutorsFabric();
        runes = runeFabric.getRunes();
        setScreenSize(SIDE, SIDE);
        startGame();
    }

    public void startGame() {
        runesLogicExecutor = scheduleRunesProcessor();
        animalLogicExecutor = scheduleAnimalProcessor();
        drawLogicExecutor = scheduleDrawProcessor();
        startVictoryChecker();
    }

    public void startVictoryChecker() {
        log.info("⚙️ Запущен цикл проверки победителей");
        executorsFabric.getSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> {
                    int radiantAnimalsCount = 0;
                    int direAnimalsCount = 0;
                    for (AbstractAnimal abstractAnimal : animals) {
                        if (abstractAnimal.getFaction() == Faction.RADIANT) {
                            radiantAnimalsCount++;
                        } else {
                            direAnimalsCount++;
                        }
                    }
                    log.info("🐺 Radiant: {} | 🐉 Dire: {}", radiantAnimalsCount, direAnimalsCount);
                    if (radiantAnimalsCount == 0) {
                        log.info("🏴 Победа Dire — все Radiant уничтожены");
                        showMessageDialog(Color.BLACK, "Dire Victory!", Color.WHITE, 90);
                        shutdownGameExecutors();
                    } else if (direAnimalsCount == 0) {
                        log.info("🟩 Победа Radiant — все Dire уничтожены");
                        showMessageDialog(Color.BLACK, "Radiant Victory!", Color.WHITE, 90);
                        shutdownGameExecutors();
                    }
                },
                0,
                50,
                TimeUnit.MILLISECONDS
        );
    }

    public void shutdownGameExecutors() {
        runesLogicExecutor.cancel(true);
        animalLogicExecutor.cancel(true);
        drawLogicExecutor.cancel(true);
    }





    public ScheduledFuture scheduleDrawProcessor() {
        log.info("🗺️ Запущена отрисовка карты и элементов поля");
        return executorsFabric.getSingleThreadScheduledExecutor().scheduleAtFixedRate(
                this::drawScene,
                0,
                100,
                TimeUnit.MILLISECONDS
        );
    }

    public ScheduledFuture scheduleRunesProcessor() {
        log.info("⚙️ Запущен цикл обновления рун на поле");
        return executorsFabric.getSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> {
                    log.info("✨ Обновление списка рун");
                    runeFabric.updateListRunes().run();
                },
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
        animals.forEach(animal -> {
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
