package org.example.app;


import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import org.example.command.AnimalAttacker;
import org.example.command.AnimalMover;
import org.example.entities.runes.AbstractRune;
import org.example.executor.AnimalsExecutor;
import org.example.executor.DrawExecutor;
import org.example.executor.RunesExecutor;
import org.example.executor.VictoryChecker;
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
    private AnimalsExecutor animalsExecutor;
    private RunesExecutor runesExecutor;
    private DrawExecutor drawExecutor;
    private VictoryChecker victoryChecker;

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
        animalsExecutor = new AnimalsExecutor(executorsFabric, animals, runes, animalMover, animalAttacker);
        runesExecutor = new RunesExecutor(executorsFabric, runeFabric);
        drawExecutor = new DrawExecutor(executorsFabric, this, animals, runes, SIDE, valueCells);
        victoryChecker = new VictoryChecker(animals, executorsFabric, this, runesLogicExecutor, animalLogicExecutor, drawLogicExecutor);
        startGame();
    }

    private void startGame() {
        runesLogicExecutor = runesExecutor.scheduleRunesProcessor();
        animalLogicExecutor = animalsExecutor.scheduleAnimalProcessor();
        drawLogicExecutor = drawExecutor.scheduleDrawProcessor();
        victoryChecker.startVictoryChecker();
    }
}
