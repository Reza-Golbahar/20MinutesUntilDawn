package io.github.some_example_name.model.enums;

public enum AbilityType {
    VITALITY("Max HP Boost", "Increases max HP by 1."),
    DAMAGER("Damage Boost", "Increases weapon damage by 25% for 10 seconds."),
    PROCREASE("Extra Projectile", "Adds 1 more projectile to weapon."),
    AMOCREASE("Ammo Capacity", "Increases max ammo by 5."),
    SPEEDY("Speed Boost", "Doubles movement speed for 10 seconds.");

    private final String title;
    private final String description;

    AbilityType(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
