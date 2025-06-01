package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enemy.Enemy;
import io.github.some_example_name.view.EndPage;
import io.github.some_example_name.view.PauseMenu;

import java.util.ArrayList;

public class PauseMenuController {
    private PauseMenu view;
    private final Player player;
    private final GameTimer gameTimer;
    private final ArrayList<Enemy> enemies;

    public PauseMenuController(Player player, GameTimer gameTimer, ArrayList<Enemy> enemies) {
        this.player = player;
        this.gameTimer = gameTimer;
        this.enemies = enemies;
    }

    public void returnToGame() {
        Main.setPaused(false);
        Main.getMain().setScreen(Main.getGameScreen());
    }

    public void saveAndQuit() {
        GameState gameState = Main.getCurrentUser().getSavedGame();
        gameState.setGameTimer(gameTimer);
        gameState.setPlayer(player);
        gameState.setEnemies(enemies);

        UserRepository.saveUsers();
        Gdx.app.exit();
    }

    public void giveUp() {
        Main.setPaused(false);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new EndPage(new EndPageController(), GameAssetManager.getGameAssetManager().getSkin(),
            player, gameTimer, false));
    }

    public void toggleBlackWhite(boolean enabled) {
        Main.getCurrentUser().setBlackAndWhiteWorld(enabled);
    }

    public void setView(PauseMenu pauseMenu) {
        this.view = pauseMenu;
    }
}
