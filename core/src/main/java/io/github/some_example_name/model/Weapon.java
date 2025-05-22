package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Weapon {
    private final Texture smgTexture = new Texture(GameAssetManager.getGameAssetManager().getSmg());
    private Sprite smgSprite = new Sprite(smgTexture);
    private int ammo = 30;
    private Player owner;

    public Weapon(Player owner) {
        this.owner = owner;
        smgSprite.setSize(50, 50);
        updateWeaponPosition();
    }

    public void updateWeaponPosition() {
        // Example: offset to the right of player
        smgSprite.setPosition(owner.getPosX() + 20, owner.getPosY() + 10);
    }

    public Sprite getSmgSprite() {
        return smgSprite;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public Texture getSmgTexture() {
        return smgTexture;
    }

    public void setSmgSprite(Sprite smgSprite) {
        this.smgSprite = smgSprite;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}

