package org.example.executor;

import com.javarush.engine.cell.Color;
import org.example.app.GameSolution;
import org.example.entities.animals.AbstractAnimal;
import org.example.fabrics.ExecutorsFabric;
import org.example.models.Faction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class VictoryChecker {
    private final static Logger log = LoggerFactory.getLogger(VictoryChecker.class);
    private final List<AbstractAnimal> animals;
    private final ExecutorsFabric executorsFabric;
    private final GameSolution gameSolution;
    private final ScheduledFuture runesLogicExecutor;
    private final ScheduledFuture animalLogicExecutor;
    private final ScheduledFuture drawLogicExecutor;

    public VictoryChecker(List<AbstractAnimal> animals,
                          ExecutorsFabric executorsFabric,
                          GameSolution gameSolution,
                          ScheduledFuture runesLogicExecutor,
                          ScheduledFuture animalLogicExecutor,
                          ScheduledFuture drawLogicExecutor) {
        this.animals = animals;
        this.executorsFabric = executorsFabric;
        this.gameSolution = gameSolution;
        this.runesLogicExecutor = runesLogicExecutor;
        this.animalLogicExecutor = animalLogicExecutor;
        this.drawLogicExecutor = drawLogicExecutor;
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
                        gameSolution.showMessageDialog(Color.BLACK, "Dire Victory!", Color.WHITE, 90);
                        shutdownGameExecutors();
                    } else if (direAnimalsCount == 0) {
                        log.info("🟩 Победа Radiant — все Dire уничтожены");
                        gameSolution.showMessageDialog(Color.BLACK, "Radiant Victory!", Color.WHITE, 90);
                        shutdownGameExecutors();
                    }
                },
                0,
                50,
                TimeUnit.MILLISECONDS
        );
    }

    private void shutdownGameExecutors() {
        runesLogicExecutor.cancel(true);
        animalLogicExecutor.cancel(true);
        drawLogicExecutor.cancel(true);
    }
}
