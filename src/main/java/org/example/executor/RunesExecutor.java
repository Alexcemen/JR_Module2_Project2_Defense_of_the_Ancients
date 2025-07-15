package org.example.executor;

import org.example.config.Config;
import org.example.fabrics.RuneFabric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RunesExecutor implements MyExecutor {
    private final static Logger log = LoggerFactory.getLogger(RunesExecutor.class);
    private final RuneFabric runeFabric;
    private final Config config;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> future;

    public RunesExecutor(RuneFabric runeFabric,
                         Config config) {
        this.runeFabric = runeFabric;
        this.config = config;
    }

    @Override
    public void start() {
        log.info("⚙️ Запущен цикл обновления рун на поле");
        this.future = executor.scheduleAtFixedRate(
                () -> {
                    log.info("✨ Обновление списка рун");
                    runeFabric.updateListRunes().run();
                },
                0,
                config.runesTick,
                TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        if (future != null && !future.isCancelled()) {
            future.cancel(true);
            log.info("❌ Остановка обновления рун");
        }
        executor.shutdownNow();
    }
}
