package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.Bullet;
import io.github.some_example_name.model.enemy.Enemy;
import io.github.some_example_name.model.Weapon;
import io.github.some_example_name.model.enemy.EnemyManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeaponController {
    private Weapon weapon;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private EnemyManager enemySpawner; // Inject from GameController
    private OrthographicCamera camera;

    public WeaponController(Weapon weapon, OrthographicCamera camera) {
        this.weapon = weapon;
        this.camera = camera;
    }

    public void setEnemySpawner(EnemyManager spawner) {
        this.enemySpawner = spawner;
    }

    public void update() {
        weapon.getSmgSprite().draw(Main.getBatch());
        weapon.updateWeaponPosition();
        updateBullets();
    }

    public void handleWeaponShoot(int mouseX, int mouseY) {
        Vector2 worldMouse = screenToWorld(mouseX, mouseY);
        float startX = weapon.getOwner().getPosX();
        float startY = weapon.getOwner().getPosY();
        bullets.add(new Bullet(startX, startY, worldMouse.x, worldMouse.y));
        weapon.setAmmo(weapon.getAmmo() - 1);
    }


    private Vector2 screenToWorld(int screenX, int screenY) {
        Vector3 screenCoords = new Vector3(screenX, screenY, 0);
        Vector3 worldCoords = camera.unproject(screenCoords);
        return new Vector2(worldCoords.x, worldCoords.y);
    }


    public void updateBullets() {
        float delta = Gdx.graphics.getDeltaTime();

        for (int i = 0; i < bullets.size(); ) {
            Bullet b = bullets.get(i);

            b.update(delta);  // ✅ Move in direction

            Sprite sprite = b.getSprite();
            if (sprite != null) {
                sprite.draw(Main.getBatch());
            }

            // ✅ Despawn if far away
            float px = weapon.getOwner().getPosX();
            float py = weapon.getOwner().getPosY();
            if (Vector2.dst(px, py, b.getX(), b.getY()) > 2000) {
                bullets.remove(i);
                continue;
            }

            // ✅ Check collision with enemies
            boolean collided = false;
            List<Enemy> enemiesSnapshot = new ArrayList<>(enemySpawner.getEnemies());
            for (Enemy e : enemiesSnapshot) {
                Sprite eSprite = e.getSprite();
                if (sprite != null && eSprite != null &&
                    sprite.getBoundingRectangle().overlaps(eSprite.getBoundingRectangle())) {
                    e.takeDamage(b.getDamage());
                    bullets.remove(i);
                    collided = true;
                    break;
                }
            }

            if (!collided) {
                i++; // only if not removed
            }
        }

        enemySpawner.getEnemies().removeIf(Enemy::isDead);
    }


    public void handleWeaponRotation(int mouseX, int mouseY) {
        Vector2 worldMouse = screenToWorld(mouseX, mouseY);
        float px = weapon.getOwner().getPosX();
        float py = weapon.getOwner().getPosY();
        float angle = MathUtils.atan2(worldMouse.y - py, worldMouse.x - px);
        weapon.getSmgSprite().setRotation(angle * MathUtils.radiansToDegrees);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}

