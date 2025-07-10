package org.example.entities.plants;

public class PoisonousBerry extends AbstractPlant {
    private final int poisonBerryDamage;

    public PoisonousBerry(String image, int poisonBerryDamage) {
        super(image);
        this.poisonBerryDamage = poisonBerryDamage;
    }

    public int getPoisonBerryDamage() {
        return poisonBerryDamage;
    }
}
