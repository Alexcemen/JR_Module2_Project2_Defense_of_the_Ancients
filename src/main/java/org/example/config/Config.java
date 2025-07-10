package org.example.config;


import java.util.Properties;

public class Config {

    public final String bearImage;
    public final String foxImage;
    public final String tigerImage;
    public final String wolfImage;
    public final String poisonBerryImage;
    public final String healingBerryImage;
    public final int animalHealth;
    public final int animalPowerAttack;
    public final int poisonBerryDamage;
    public final int powerHealing;
    public final int startCountAnimals;
    public final int sideField;
    public final int turnTimer;

    private Config(Properties props) {
        this.bearImage = props.getProperty("bear_image");
        this.foxImage = props.getProperty("fox_image");
        this.tigerImage = props.getProperty("tiger_image");
        this.wolfImage = props.getProperty("wolf_image");
        this.poisonBerryImage = props.getProperty("poison_berry_image");
        this.healingBerryImage = props.getProperty("healing_berry_image");
        this.animalHealth = Integer.parseInt(props.getProperty("animal_health"));
        this.animalPowerAttack = Integer.parseInt(props.getProperty("animal_power_attack"));
        this.poisonBerryDamage = Integer.parseInt(props.getProperty("poison_berry_damage"));
        this.powerHealing = Integer.parseInt(props.getProperty("power_healing"));
        this.startCountAnimals = Integer.parseInt(props.getProperty("start_count_animals"));
        this.sideField = Integer.parseInt(props.getProperty("side_field"));
        this.turnTimer = Integer.parseInt(props.getProperty("turn_timer"));
    }

    public static Config load() {
        Properties props = new Properties();
        try (var in = Config.class.getResourceAsStream("/zoo.properties")) {
            if (in != null) props.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Cannot read bot.bot.properties", e);
        }
        return new Config(props);
    }
}
