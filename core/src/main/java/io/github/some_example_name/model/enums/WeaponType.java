package io.github.some_example_name.model.enums;

public enum WeaponType {
    REVOLVER(6, 1f, 1, 20),
    SHOTGUN(2, 1f, 4, 10),
    DUAL_SMGS(24, 2f, 1, 8);

    private final int maxAmmo;
    private final float reloadTime;
    private final int projectileCount;
    private final int damage;

    WeaponType(int maxAmmo, float reloadTime, int projectileCount, int damage) {
        this.maxAmmo = maxAmmo;
        this.reloadTime = reloadTime;
        this.projectileCount = projectileCount;
        this.damage = damage;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public float getReloadTime() {
        return reloadTime;
    }

    public int getProjectileCount() {
        return projectileCount;
    }

    public int getDamage() {
        return damage;
    }
}

