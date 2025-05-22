package io.github.some_example_name.model.enums;

import io.github.some_example_name.model.GameAssetManager;

public enum HeroType {
    SHANA(4, 4, GameAssetManager.getGameAssetManager().getShanaIdleFrames()[0]),
    DIAMOND(7, 1, GameAssetManager.getGameAssetManager().getDiamondIdleFrames()[0]),
    SCARLET(3, 5, GameAssetManager.getGameAssetManager().getScarlettIdleFrames()[0]),
    LILITH(5, 3, GameAssetManager.getGameAssetManager().getLilithIdleFrames()[0]),
    DASHER(2, 10, GameAssetManager.getGameAssetManager().getDasherIdleFrames()[0]);

    private final int hp;
    private final int speed;
    private final String texturePath;

    HeroType(int hp, int speed, String texturePath) {
        this.hp = hp;
        this.speed = speed;
        this.texturePath = texturePath;
    }

    public int getHp() { return hp; }
    public int getSpeed() { return speed; }
    public String getTexturePath() { return texturePath; }
}

//TODO: Description
//    Abby("Right click to quickly shoot all of your remaining ammo in random directions"),
//    Dasher(""),
//    Diamond(""),
//    Hastur(""),
//    Hina("Right click to summon a Shadow Clone that attacks nearby enemis. Shadow Clone deals damage equal to your Bullet Damage."),
//    Lilith(""),
//    Luna(""),
//    Raven(""),
//    Scarlett(""),
//    Shana("Can reroll upgrades once per level."),
//    Spark(""),
//    Yuki("");

//    HeroType(String hint) {
//        this.hint = hint;
//    }
//
//    private String hint;

