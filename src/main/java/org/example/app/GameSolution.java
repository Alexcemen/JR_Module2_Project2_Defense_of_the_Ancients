package org.example.app;

import com.javarush.engine.cell.Game;
import org.example.entities.runes.AbstractRune;
import org.example.executor.AnimalsExecutor;
import org.example.executor.DrawExecutor;
import org.example.executor.RunesExecutor;
import org.example.executor.VictoryChecker;
import org.example.fabrics.AnimalsFabric;
import org.example.config.Config;
import org.example.entities.animals.*;
import org.example.fabrics.ExecutorsFabric;
import org.example.fabrics.RuneFabric;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class GameSolution extends Game {
    private VictoryChecker victoryChecker;
    private AnimalsExecutor animalsExecutor;
    private RunesExecutor runesExecutor;
    private DrawExecutor drawExecutor;
    private ScheduledFuture animalLogicExecutor;
    private ScheduledFuture runesLogicExecutor;
    private ScheduledFuture drawLogicExecutor;

    @Override
    public void initialize() {
        Config config = Config.load();
        //Field
        setScreenSize(config.sideField, config.sideField);
        //Animals
        AnimalsFabric animalsFabric = new AnimalsFabric(config);
        List<AbstractAnimal> animals = animalsFabric.getAnimalsList();
        //Runes
        RuneFabric runeFabric = new RuneFabric(config, animals);
        List<AbstractRune> runes = runeFabric.getRunes();
        //Executors
        ExecutorsFabric executorsFabric = new ExecutorsFabric();
        animalsExecutor = new AnimalsExecutor(
                executorsFabric,
                animals,
                runes,
                config
        );
        runesExecutor = new RunesExecutor(
                executorsFabric,
                runeFabric,
                config
        );
        drawExecutor = new DrawExecutor(
                executorsFabric,
                this,
                animals,
                runes,
                config
        );
        victoryChecker = new VictoryChecker(
                animals,
                executorsFabric,
                this,
                runesLogicExecutor,
                animalLogicExecutor,
                drawLogicExecutor,
                config
        );
        startGame();
    }

    private void startGame() {
        runesLogicExecutor = runesExecutor.scheduleRunesProcessor();
        animalLogicExecutor = animalsExecutor.scheduleAnimalProcessor();
        drawLogicExecutor = drawExecutor.scheduleDrawProcessor();
        victoryChecker.startVictoryChecker();
    }
}
