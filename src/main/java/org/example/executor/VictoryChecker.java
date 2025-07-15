package org.example.executor;

import com.javarush.engine.cell.Color;
import org.example.app.GameSolution;
import org.example.config.Config;
import org.example.entities.animals.AbstractAnimal;
import org.example.models.Faction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class VictoryChecker implements MyExecutor {
    private final static Logger log = LoggerFactory.getLogger(VictoryChecker.class);
    private final List<AbstractAnimal> animals;
    private final GameSolution gameSolution;
    private final Config config;
    private final List<MyExecutor> executors;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> future;

    public VictoryChecker(List<AbstractAnimal> animals,
                          GameSolution gameSolution,
                          Config config,
                          List<MyExecutor> executors) {
        this.animals = animals;
        this.gameSolution = gameSolution;
        this.config = config;
        this.executors = executors;
    }

    @Override
    public void start() {
        log.info("⚙️ Запущен цикл проверки победителей");
        this.future = executor.scheduleAtFixedRate(
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
                        stop();
                    } else if (direAnimalsCount == 0) {
                        log.info("🟩 Победа Radiant — все Dire уничтожены");
                        gameSolution.showMessageDialog(Color.BLACK, "Radiant Victory!", Color.WHITE, 90);
                        stop();
                    }
                },
                0,
                config.checkVictoryTick,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public void stop() {
        log.info("⛔ Завершаю игру и все процессы");
        executors.forEach(MyExecutor::stop);
        if (future != null && !future.isCancelled()) {
            future.cancel(true);
            log.info("❌ Остановка VictoryChecker");
        }
        executor.shutdownNow();
    }
}
