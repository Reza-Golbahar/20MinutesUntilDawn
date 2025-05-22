package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enemy.Enemy;
import io.github.some_example_name.model.enemy.EnemyManager;
import io.github.some_example_name.model.enemy.Projectile;
import io.github.some_example_name.model.enemy.ProjectileManager;
import io.github.some_example_name.view.GameView;

import java.util.ArrayList;
import java.util.Iterator;

public class GameController {
    private GameView view;
    private OrthographicCamera camera;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private EnemyManager enemyManager;
    private ProjectileManager projectileManager;
    private final GameTimer gameTimer = new GameTimer();


    public void setView(GameView view) {
        this.view = view;
        playerController = new PlayerController(new Player(Main.getPregame().getHeroType()));
        worldController = new WorldController(playerController);

        playerController.getPlayer().setPosX(worldController.getBackgroundTexture().getWidth() / 2f);
        playerController.getPlayer().setPosY(worldController.getBackgroundTexture().getHeight() / 2f);
        camera.position.set(playerController.getPlayer().getPosX(), playerController.getPlayer().getPosY(), 0);

        weaponController = new WeaponController(new Weapon(playerController.getPlayer()), camera);
        //this.weaponController.getWeapon().setOwner(playerController.getPlayer());

        this.projectileManager = new ProjectileManager();
        enemyManager = new EnemyManager(gameTimer, worldController.getBackgroundTexture(), projectileManager);
        enemyManager.spawnInitialTrees(100);

        weaponController.setEnemySpawner(this.enemyManager); // ✅ Inject the spawner
    }

    public void updateGame(float delta) {
        if (view != null) {
            updateCamera(playerController.getPlayer()); // 👈 Update the camera position
            worldController.update();
            playerController.update(delta);
            weaponController.update();

            Vector2 playerPos = playerController.getPlayer().getPosition();
            enemyManager.update(delta, playerPos);
            enemyManager.render(Main.getBatch());

            if (!enemyManager.getDroppedSeeds().isEmpty()) {
                Iterator<ItemSeed> iterator = enemyManager.getDroppedSeeds().iterator();

                while (iterator.hasNext()) {
                    ItemSeed seed = iterator.next();
                    if (playerController.getPlayer().getRect().collidesWith(seed.getRect())) {
                        playerController.getPlayer().addXp(3);
                        iterator.remove(); // remove collected seed
                    }
                }
                enemyManager.renderSeeds(Main.getBatch());
            }


            checkPlayerCollisions(delta);

            gameTimer.update(delta);
        }
    }

    private void checkPlayerCollisions(float delta) {
        Player player = playerController.getPlayer();
        if (player == null || player.getRect() == null) return;

        // Check collisions with enemies
        for (Enemy enemy : enemyManager.getEnemies()) {
            CollisionRect enemyRect = enemy.getRect();
            if (enemyRect != null && player.getRect().collidesWith(enemyRect)) {
                player.takeDamage(10);
            }
        }

        // Check collisions with projectiles
        for (Projectile projectile : new ArrayList<>(projectileManager.getProjectiles())) {
            CollisionRect projRect = projectile.getRect();
            if (projRect != null && player.getRect().collidesWith(projRect)) {
                player.takeDamage(5);
            }
        }

        player.decreaseInvincibleTime(delta);
    }



    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void updateCamera(Player player) {
        camera.position.set(player.getPosX(), player.getPosY(), 0);
        camera.update();
    }
}
