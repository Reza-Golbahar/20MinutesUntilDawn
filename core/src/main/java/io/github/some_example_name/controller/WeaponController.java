package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.Bullet;
import io.github.some_example_name.model.ControlsMapping;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.enemy.Enemy;
import io.github.some_example_name.model.Weapon;
import io.github.some_example_name.model.enemy.EnemyManager;
import io.github.some_example_name.model.enums.ActionType;

import java.util.ArrayList;
import java.util.List;

public class WeaponController {
    private Weapon weapon;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private EnemyManager enemySpawner;
    private OrthographicCamera camera;
    private CheatCodeHandler cheatCodeHandler;

    public WeaponController(Weapon weapon, OrthographicCamera camera) {
        this.weapon = weapon;
        this.camera = camera;
    }

    public void setEnemySpawner(EnemyManager spawner) {
        this.enemySpawner = spawner;
    }

    public void update(float delta) {
        handleInput();

        weapon.updateReloadTimer(delta);
        weapon.updateWeaponPosition();
        weapon.getSprite().draw(Main.getBatch());

        if (Main.getCurrentUser().isAutoReload() && weapon.getAmmo() < weapon.getProjectileCount()) {
            weapon.reload();
        }

        if (Main.getCurrentUser().isAutoAim() && weapon.canShoot()) {
            float px = weapon.getOwner().getPosX();
            float py = weapon.getOwner().getPosY();
            Enemy target = findClosestEnemy(px, py);

            if (target != null) {
                float tx = target.getPosition().x;
                float ty = target.getPosition().y;

                shootAtTarget(tx, ty);
                float angle = MathUtils.atan2(ty - py, tx - px) * MathUtils.radiansToDegrees;
                weapon.getSprite().setRotation(angle);
            }
        }

        updateBullets();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(ControlsMapping.getInstance().getKey(ActionType.Reload))) {
            weapon.reload();
        }

        if (ControlsMapping.getInstance().getKey(ActionType.Shoot) != Input.Buttons.LEFT) {
            if (Gdx.input.isKeyJustPressed(ControlsMapping.getInstance().getKey(ActionType.Shoot))) {
                int mouseX = Gdx.input.getX();
                int mouseY = Gdx.input.getY();
                shootAtScreen(mouseX, mouseY);
            }
        }

        if (Gdx.input.isKeyJustPressed(ControlsMapping.getInstance().getKey(ActionType.ToggleAutoAim))) {
            Main.getCurrentUser().setAutoAim(!Main.getCurrentUser().isAutoAim());
        }
    }

    public void shootAtScreen(int mouseX, int mouseY) {
        if (!weapon.canShoot()) return;

        Vector2 worldMouse = screenToWorld(mouseX, mouseY);
        float px = weapon.getOwner().getPosX();
        float py = weapon.getOwner().getPosY();
        shootWithSpread(px, py, worldMouse.x, worldMouse.y);

        float angle = MathUtils.atan2(worldMouse.y - py, worldMouse.x - px) * MathUtils.radiansToDegrees;
        weapon.getSprite().setRotation(angle);
    }

    private void shootAtTarget(float targetX, float targetY) {
        if (!weapon.canShoot()) return;

        float px = weapon.getOwner().getPosX();
        float py = weapon.getOwner().getPosY();
        shootWithSpread(px, py, targetX, targetY);
    }

    private void shootWithSpread(float startX, float startY, float targetX, float targetY) {
        int projectileCount = weapon.getProjectileCount();
        //For Cheat code
        if (cheatCodeHandler.isTripleProjectionActive())
            projectileCount *= 3;

        if (weapon.getAmmo() == 0) return;

        float baseAngle = MathUtils.atan2(targetY - startY, targetX - startX);
        float arcSpread = 20f;
        float step = arcSpread / Math.max(1, projectileCount - 1);
        float startAngle = baseAngle - (arcSpread * 0.5f) * MathUtils.degreesToRadians;

        for (int i = 0; i < projectileCount; i++) {
            float stepInRadians = step * MathUtils.degreesToRadians;
            float angle = startAngle + i * stepInRadians;
            float dx = startX + MathUtils.cos(angle);
            float dy = startY + MathUtils.sin(angle);

            Bullet bullet = new Bullet(startX, startY, dx, dy, weapon.getCurrentDamage());
            bullets.add(bullet);
        }

        weapon.setAmmo(weapon.getAmmo() - 1);

        if (Main.getCurrentUser().isSfxEnabled()) {
            GameAssetManager.getGameAssetManager().shootSound.play();
        }
    }

    private void updateBullets() {
        float delta = Gdx.graphics.getDeltaTime();
        float px = weapon.getOwner().getPosX();
        float py = weapon.getOwner().getPosY();

        for (int i = 0; i < bullets.size(); ) {
            Bullet b = bullets.get(i);
            b.update(delta);

            Sprite sprite = b.getSprite();
            if (sprite != null) {
                sprite.draw(Main.getBatch());
            }

            if (Vector2.dst(px, py, b.getX(), b.getY()) > 2000) {
                bullets.remove(i);
                continue;
            }

            boolean collided = false;
            List<Enemy> enemiesSnapshot = new ArrayList<>(enemySpawner.getEnemies());
            for (Enemy e : enemiesSnapshot) {
                if (e.isDead()) continue;

                if (b.getRect().collidesWith(e.getRect())) {
                    e.takeDamage(b.getDamage(), b);
                    if (e.isDead()) weapon.getOwner().incrementKillCount();
                    bullets.remove(i);
                    collided = true;
                    break;
                }
            }

            if (!collided) i++;
        }
    }

    private Vector2 screenToWorld(int screenX, int screenY) {
        Vector3 screenCoords = new Vector3(screenX, screenY, 0);
        Vector3 worldCoords = camera.unproject(screenCoords);
        return new Vector2(worldCoords.x, worldCoords.y);
    }

    private Enemy findClosestEnemy(float px, float py) {
        Enemy closest = null;
        float minDist = Float.MAX_VALUE;
        for (Enemy e : enemySpawner.getEnemies()) {
            if (e.isDead()) continue;
            float dist = Vector2.dst(px, py, e.getPosition().x, e.getPosition().y);
            if (dist < minDist) {
                minDist = dist;
                closest = e;
            }
        }
        return closest;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void handleWeaponRotation(int mouseX, int mouseY) {
        Vector2 worldMouse = screenToWorld(mouseX, mouseY);
        float px = weapon.getOwner().getPosX();
        float py = weapon.getOwner().getPosY();
        float angle = MathUtils.atan2(worldMouse.y - py, worldMouse.x - px);
        weapon.getSprite().setRotation(angle * MathUtils.radiansToDegrees);
    }

    public void setCheatCodeHandler(CheatCodeHandler cheatCodeHandler) {
        this.cheatCodeHandler = cheatCodeHandler;
    }
}
