package org.example.executor;

import org.example.command.AnimalAttacker;
import org.example.command.AnimalMover;
import org.example.config.Config;
import org.example.entities.animals.AbstractAnimal;
import org.example.entities.runes.AbstractRune;
import org.example.fabrics.ExecutorsFabric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class AnimalsExecutor {
    private final static Logger log = LoggerFactory.getLogger(AnimalsExecutor.class);
    private final ExecutorsFabric executorsFabric;
    private final List<AbstractAnimal> animals;
    private final List<AbstractRune> runes;
    private final AnimalMover animalMover;
    private final AnimalAttacker animalAttacker;
    private final Config config;

    public AnimalsExecutor(ExecutorsFabric executorsFabric,
                           List<AbstractAnimal> animals,
                           List<AbstractRune> runes,
                           AnimalMover animalMover,
                           AnimalAttacker animalAttacker,
                           Config config) {
        this.executorsFabric = executorsFabric;
        this.animals = animals;
        this.runes = runes;
        this.animalMover = animalMover;
        this.animalAttacker = animalAttacker;
        this.config = config;
    }

    public ScheduledFuture scheduleAnimalProcessor() {
        log.info("⚙️ Запущен цикл движения животных и взаимодействия с рунами");
        return executorsFabric.getSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> {
                    for (AbstractAnimal animal : animals) {
                        animalMover.move(animal);
                        animalAttacker.attack(animal);
                        checkRunes(animal);
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
}
