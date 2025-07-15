package org.example.executor;

import com.javarush.engine.cell.Color;
import org.example.app.GameSolution;
import org.example.config.Config;
import org.example.entities.animals.AbstractAnimal;
import org.example.entities.runes.AbstractRune;
import org.example.models.Faction;
import org.example.util.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DrawExecutor implements MyExecutor {
    private final static Logger log = LoggerFactory.getLogger(DrawExecutor.class);
    private final GameSolution gameSolution;
    private final List<AbstractAnimal> animals;
    private final List<AbstractRune> runes;
    private final Config config;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> future;

    public DrawExecutor(GameSolution gameSolution,
                        List<AbstractAnimal> animals,
                        List<AbstractRune> runes,
                        Config config) {
        this.gameSolution = gameSolution;
        this.animals = animals;
        this.runes = runes;
        this.config = config;
    }

    @Override
    public void start() {
        log.info("🗺️ Запущена отрисовка карты и элементов поля");
        this.future = executor.scheduleAtFixedRate(
                this::drawScene,
                0,
                config.drawTick,
                TimeUnit.MILLISECONDS
        );
    }

    private void drawScene() {
        drawMap();
        updateRunesOnField();
        updateAnimalsOnField();
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
                gameSolution.setCellValueEx(x, y, map[y][x], value);
            }
        }
        gameSolution.setCellValue(0, 19, "\uD83C\uDFF0");//база radiant
        gameSolution.setCellValue(19, 0, "\uD83C\uDFEF");//база dark
        gameSolution.setCellValue(4, 8, "\uD83C\uDFD5");//палатка
        gameSolution.setCellValue(16, 12, "\uD83C\uDFD5");

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                gameSolution.setCellTextSize(x, y, 90);
            }
        }
    }

    private void updateAnimalsOnField() {
        animals.forEach(animal -> {
            gameSolution.setCellValueEx(
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
            gameSolution.setCellValueEx(
                    rune.getCoordinates().x(),
                    rune.getCoordinates().y(),
                    Color.DARKCYAN,
                    rune.getImage(),
                    Color.AQUA,
                    90
            );
        });
    }

    private Color getHealthColor(AbstractAnimal animal) {
        int health = animal.getHealth();
        int group = health / 10;
        if (animal.getFaction() == Faction.RADIANT) {
            return switch (group) {
                case 9 -> Color.LAWNGREEN;
                case 8 -> Color.GREENYELLOW;
                case 7 -> Color.YELLOWGREEN;
                case 6 -> Color.YELLOW;
                case 5 -> Color.GOLD;
                case 4 -> Color.ORANGE;
                case 3 -> Color.DARKORANGE;
                case 2 -> Color.ORANGERED;
                case 1 -> Color.CRIMSON;
                default -> Color.LIME;
            };
        } else {
            return switch (group) {
                case 9 -> Color.FORESTGREEN;
                case 8 -> Color.DARKSEAGREEN;
                case 7 -> Color.OLIVEDRAB;
                case 6 -> Color.GOLDENROD;
                case 5 -> Color.DARKGOLDENROD;
                case 4 -> Color.PERU;
                case 3 -> Color.FIREBRICK;
                case 2 -> Color.DARKRED;
                case 1 -> Color.MAROON;
                default -> Color.DARKOLIVEGREEN;
            };
        }
    }

    @Override
    public void stop() {
        if (future != null && !future.isCancelled()) {
            future.cancel(true);
            log.info("❌ Остановка отрисовки поля");
        }
        executor.shutdownNow();
    }
}
