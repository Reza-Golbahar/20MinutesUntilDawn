package io.github.some_example_name.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.enums.WeaponType;

public class Weapon {
    private WeaponType type;
    private transient Sprite sprite;
    private int ammo;
    private int maxAmmo;
    private transient Player owner;
    private float damageMultiplier = 1.0f;
    private int projectileCount;
    private boolean reloaded = false;
    private double reloadTimer = 0d;

    //for Saving
    public Weapon() {

    }

    public Weapon(Player owner, WeaponType type) {
        this.owner = owner;
        this.type = type;
        this.ammo = type.getMaxAmmo();
        this.maxAmmo = type.getMaxAmmo();
        this.projectileCount = type.getProjectileCount();

        if (type.equals(WeaponType.DUAL_SMGS))
            this.sprite = new Sprite(new Texture(GameAssetManager.getGameAssetManager().getSMGDual()));
        else if (type.equals(WeaponType.REVOLVER))
            this.sprite = new Sprite(new Texture(GameAssetManager.getGameAssetManager().getRevolver()));
        else
            this.sprite = new Sprite(new Texture(GameAssetManager.getGameAssetManager().getShotgun()));

        this.sprite.setSize(50, 50);
        updateWeaponPosition();
    }

    public void updateWeaponPosition() {
        sprite.setPosition(owner.getPosX() + 20, owner.getPosY() + 10);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public WeaponType getType() {
        return type;
    }

    public float getDamageMultiplier() {
        return damageMultiplier;
    }

    public void setDamageMultiplier(float damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    public int getCurrentDamage() {
        return Math.round(type.getDamage() * damageMultiplier);
    }

    public void increaseMaxAmmo(int count) {
        this.maxAmmo += count;
    }

    public void reload() {
        this.ammo = this.maxAmmo;
        if (Main.getCurrentUser().isSfxEnabled())
            GameAssetManager.getGameAssetManager().reloadSound.play();
        this.reloaded = true;
    }

    public void updateReloadTimer(float delta) {
        if (this.reloaded) {
            reloadTimer += delta;
            if (reloadTimer> type.getReloadTime()) {
                reloadTimer = 0;
                reloaded = false;
            }
        }
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public int getProjectileCount() {
        return projectileCount;
    }

    public void increaseProjectileCount(int count) {
        this.projectileCount += count;
    }

    public boolean isReloaded() {
        return reloaded;
    }

    public boolean canShoot() {
        return reloadTimer <= 0 && ammo >= 1;
    }

    public void initTransientFields(Player player) {
        this.owner = player;

        // Recreate sprite depending on weapon type
        if (type.equals(WeaponType.DUAL_SMGS))
            this.sprite = new Sprite(new Texture(GameAssetManager.getGameAssetManager().getSMGDual()));
        else if (type.equals(WeaponType.REVOLVER))
            this.sprite = new Sprite(new Texture(GameAssetManager.getGameAssetManager().getRevolver()));
        else
            this.sprite = new Sprite(new Texture(GameAssetManager.getGameAssetManager().getShotgun()));

        this.sprite.setSize(50, 50);

        // Important: reposition the weapon relative to player
        updateWeaponPosition();
    }

}
