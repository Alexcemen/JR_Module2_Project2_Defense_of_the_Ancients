package org.example.app;

import com.javarush.engine.cell.Game;
import org.example.entities.runes.AbstractRune;
import org.example.executor.*;
import org.example.fabrics.AnimalsFabric;
import org.example.config.Config;
import org.example.entities.animals.*;
import org.example.fabrics.RuneFabric;
import java.util.List;

public class GameSolution extends Game {
    private VictoryChecker victoryChecker;
    private AnimalsExecutor animalsExecutor;
    private RunesExecutor runesExecutor;
    private DrawExecutor drawExecutor;

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
        animalsExecutor = new AnimalsExecutor(
                animals,
                runes,
                config
        );
        runesExecutor = new RunesExecutor(
                runeFabric,
                config
        );
        drawExecutor = new DrawExecutor(
                this,
                animals,
                runes,
                config
        );
        List<MyExecutor> executors = List.of(
                animalsExecutor,
                runesExecutor,
                drawExecutor
        );
        victoryChecker = new VictoryChecker(
                animals,
                this,
                config,
                executors
        );
        startGame();
    }

    private void startGame() {
        runesExecutor.start();
        animalsExecutor.start();
        drawExecutor.start();
        victoryChecker.start();
    }
}
