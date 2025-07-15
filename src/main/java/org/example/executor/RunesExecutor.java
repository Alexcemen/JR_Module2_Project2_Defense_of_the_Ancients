package org.example.executor;

import org.example.fabrics.ExecutorsFabric;
import org.example.fabrics.RuneFabric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RunesExecutor {
    private final static Logger log = LoggerFactory.getLogger(RunesExecutor.class);
    private final ExecutorsFabric executorsFabric;
    private final RuneFabric runeFabric;

    public RunesExecutor(ExecutorsFabric executorsFabric, RuneFabric runeFabric) {
        this.executorsFabric = executorsFabric;
        this.runeFabric = runeFabric;
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
}
