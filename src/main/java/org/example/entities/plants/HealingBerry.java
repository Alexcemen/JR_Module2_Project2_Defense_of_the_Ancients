package org.example.entities.plants;

public class HealingBerry extends AbstractPlant {
    private final int powerHealing;

    public HealingBerry(String image, int powerHealing) {
        super(image);
        this.powerHealing = powerHealing;
    }
}
