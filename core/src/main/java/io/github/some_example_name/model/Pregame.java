package io.github.some_example_name.model;

import io.github.some_example_name.model.enums.HeroType;
import io.github.some_example_name.model.enums.WeaponType;

public class Pregame {
    private HeroType heroType;
    private WeaponType weaponType;
    private float duration = 600;

    //for saving
    public Pregame() {

    }

    public HeroType getHeroType() {
        return heroType;
    }

    public void setHeroType(HeroType heroType) {
        this.heroType = heroType;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
}

