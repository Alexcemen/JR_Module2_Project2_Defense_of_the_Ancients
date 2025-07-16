package org.example.executor;

import org.example.command.AnimalAttacker;
import org.example.command.AnimalMover;
import org.example.config.Config;
import org.example.entities.animals.AbstractAnimal;
import org.example.entities.runes.AbstractRune;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class AnimalsExecutor implements MyExecutor {
    private final static Logger log = LoggerFactory.getLogger(AnimalsExecutor.class);
    private final List<AbstractAnimal> animals;
    private final List<AbstractRune> runes;
    private final Config config;
    private final AnimalMover animalMover;
    private final AnimalAttacker animalAttacker;
    private final ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
    private final ExecutorService executor = Executors.newWorkStealingPool();
    private ScheduledFuture<?> future;


    public AnimalsExecutor(List<AbstractAnimal> animals,
                           List<AbstractRune> runes,
                           Config config) {
        this.animals = animals;
        this.runes = runes;
        this.config = config;
        this.animalMover = new AnimalMover(config, animals);
        this.animalAttacker = new AnimalAttacker(animals);
    }

    @Override
    public void start() {
        log.info("⚙️ Запущен цикл движения животных и взаимодействия с рунами");
        this.future = scheduled.scheduleAtFixedRate(
                () -> {
                    for (AbstractAnimal animal : animals) {
                        executor.submit(() -> {
                            animalMover.move(animal);
                            animalAttacker.attack(animal);
                            checkRunes(animal);
                        });
                    }
                },
                0,
                config.animalMoveTick,
                TimeUnit.MILLISECONDS
        );
    }

    private void checkRunes(AbstractAnimal animal) {
        Iterator<AbstractRune> iterator = runes.iterator();
        while (iterator.hasNext()) {
            AbstractRune rune = iterator.next();
            if (animal.getCoordinates().equals(rune.getCoordinates())) {
                log.info("⚡ Обнаружена руна {} на координатах {} — активируется",
                        rune.getClass().getSimpleName(),
                        rune.getCoordinates());
                rune.setAnimal(animal);
                rune.execute();
                iterator.remove();
                log.info("🔥 Руна {} удалена после активации", rune.getClass().getSimpleName());
            }
        }
    }

    @Override
    public void stop() {
        if (future != null && !future.isCancelled()) {
            future.cancel(true);
            log.info("❌ Остановлен цикл движения животных");
        }
        scheduled.shutdownNow();
        executor.shutdownNow();
    }
}
