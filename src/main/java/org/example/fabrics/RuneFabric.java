package org.example.fabrics;

import org.example.config.Config;
import org.example.entities.animals.*;
import org.example.entities.runes.*;
import org.example.models.Coordinates;
import org.example.models.RuneEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RuneFabric {

    private List<AbstractRune> runes;
    private Coordinates coordinates1;
    private Coordinates coordinates2;
    private final Config config;
    private final List<AbstractAnimal> animals;

    public RuneFabric(Config config, List<AbstractAnimal> animals) {
        this.config = config;
        this.animals = animals;
    }

    public List<AbstractRune> getRunes() {
        if (runes == null) {
            createListRunes();
        }
        return runes;
    }

    private void createListRunes() {
        runes = Collections.synchronizedList(new ArrayList<>());
        coordinates1 = new Coordinates(5, 4);
        coordinates2 = new Coordinates(13, 15);

        AbstractRune randomRune1 = getRandomRune(coordinates1);
        AbstractRune randomRune2;

        do {
            randomRune2 = getRandomRune(coordinates2);
        } while (randomRune2.getImage().equals(randomRune1.getImage()));

        runes.add(randomRune1);
        runes.add(randomRune2);
    }

    public Runnable updateListRunes() {
        return () -> {
            runes.clear();
            AbstractRune randomRune1 = getRandomRune(coordinates1);
            AbstractRune randomRune2;

            do {
                randomRune2 = getRandomRune(coordinates2);
            } while (randomRune2.getImage().equals(randomRune1.getImage()));

            runes.add(randomRune1);
            runes.add(randomRune2);
        };
    }

    private AbstractRune getRandomRune(Coordinates coordinates) {
        return switch (getRandomRuneEffect()) {
            case HEALING -> new HealingRune(coordinates, null, config);
            case POISON -> new PoisonRune(coordinates, null, config);
            case ATTACK_POWER -> new AttackPowerRune(coordinates, null, config);
            case CLONING -> new CloningRune(coordinates, null, config, animals);
        };
    }

    private RuneEffect getRandomRuneEffect() {
        int countRuneEffects = RuneEffect.values().length;
        int numberRandomRuneEffect = ThreadLocalRandom.current().nextInt(countRuneEffects);
        return RuneEffect.values()[numberRandomRuneEffect];
    }

}
