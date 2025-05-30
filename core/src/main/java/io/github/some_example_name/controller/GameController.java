package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enemy.Enemy;
import io.github.some_example_name.model.enemy.EnemyManager;
import io.github.some_example_name.model.enemy.Projectile;
import io.github.some_example_name.model.enemy.ProjectileManager;
import io.github.some_example_name.model.enums.ActionType;
import io.github.some_example_name.view.GameView;
import io.github.some_example_name.view.HUD;
import io.github.some_example_name.view.EndPage;
import io.github.some_example_name.view.PauseMenu;

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
    private LevelUpManager levelUpManager;
    private CheatCodeHandler cheatCodeHandler;
    private HUD hud;

    public void setView(GameView view) {
        this.view = view;
        playerController = new PlayerController(new Player(Main.getPregame().getHeroType()));
        Weapon weapon = new Weapon(playerController.getPlayer(), Main.getPregame().getWeaponType());
        playerController.getPlayer().setWeapon(weapon);
        worldController = new WorldController(playerController);

        playerController.getPlayer().setPosX(worldController.getBackgroundTexture().getWidth() / 2f);
        playerController.getPlayer().setPosY(worldController.getBackgroundTexture().getHeight() / 2f);
        camera.position.set(playerController.getPlayer().getPosX(), playerController.getPlayer().getPosY(), 0);

        weaponController = new WeaponController(weapon, camera);
        //this.weaponController.getWeapon().setOwner(playerController.getPlayer());

        this.projectileManager = new ProjectileManager();
        enemyManager = new EnemyManager(gameTimer, worldController.getBackgroundTexture(), projectileManager);
        enemyManager.spawnInitialTrees(100);
        view.setEnemyManager(enemyManager);

        weaponController.setEnemySpawner(this.enemyManager); // ✅ Inject the spawner
        this.levelUpManager = new LevelUpManager(playerController.getPlayer());
        this.cheatCodeHandler = new CheatCodeHandler(playerController.getPlayer(), enemyManager, gameTimer, worldController.getBackgroundTexture());
        weaponController.setCheatCodeHandler(cheatCodeHandler);

        hud = new HUD(playerController.getPlayer(), weapon, gameTimer, Main.getPregame().getDuration());
        view.setHUD(hud);
    }

    public void updateGame(float delta) {
        if (Main.isPaused()) return;
        if (playerController.getPlayer().getPlayerHealth() <= 0) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new EndPage(new EndPageController(), GameAssetManager.getGameAssetManager().getSkin(),
                playerController.getPlayer(), gameTimer, false));
        } else if (gameTimer.checkIsTimeOver()) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new EndPage(new EndPageController(), GameAssetManager.getGameAssetManager().getSkin(),
                playerController.getPlayer(), gameTimer, true));
        }

        if (view != null) {
            Texture lightMask = new Texture(GameAssetManager.getGameAssetManager().getWhiteCircle());
            Main.getBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            Main.getBatch().draw(lightMask, playerController.getPlayer().getPosX() - 128, playerController.getPlayer().getPosY() - 128);
            Main.getBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            handlePauseAndCheatCodes();
            updateCamera(playerController.getPlayer()); // 👈 Update the camera position
            worldController.update();
            playerController.update(delta);
            weaponController.update(delta);
            cheatCodeHandler.update(delta);

            Vector2 playerPos = playerController.getPlayer().getPosition();
            enemyManager.update(delta, playerPos);
            enemyManager.render(Main.getBatch());
            projectileManager.update(delta);
            projectileManager.render(Main.getBatch());

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
            levelUpManager.checkLevelUp();

            gameTimer.update(delta);
        }
    }

    private void handlePauseAndCheatCodes() {
        if (Gdx.input.isKeyPressed(ControlsMapping.getInstance().getKey(ActionType.Pause))) {
            Main.setPaused(true);
            Main.getMain().setScreen(new PauseMenu(new PauseMenuController(),
                GameAssetManager.getGameAssetManager().getSkin(), playerController.getPlayer()));
        }
        cheatCodeHandler.checkPlayerInput();
    }

    private void checkPlayerCollisions(float delta) {
        Player player = playerController.getPlayer();
        if (player == null || player.getRect() == null) return;

        // Check collisions with enemies
        for (Enemy enemy : enemyManager.getEnemies()) {
            CollisionRect enemyRect = enemy.getRect();
            if (player.getRect().collidesWith(enemyRect)) {
                player.takeDamage();
            }
        }

        // Check collisions with projectiles
        for (Projectile projectile : new ArrayList<>(projectileManager.getProjectiles())) {
            CollisionRect projRect = projectile.getRect();
            if (player.getRect().collidesWith(projRect)) {
                player.takeDamage();
            }
        }
    }



    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public void create() {
        GameAssetManager.loadShaders();
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
