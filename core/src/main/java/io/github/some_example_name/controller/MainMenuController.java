package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.GameState;
import io.github.some_example_name.model.UserRepository;
import io.github.some_example_name.model.enemy.Enemy;
import io.github.some_example_name.model.enemy.EyeBat;
import io.github.some_example_name.model.enemy.Projectile;
import io.github.some_example_name.view.*;

public class MainMenuController {
    private MainMenu view;

    public void setView(MainMenu mainMenu) {
        this.view = mainMenu;
    }

    public void goToSettings() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SettingsMenu(new SettingsMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void goToProfile() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ProfileMenu(new ProfileMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void goToPreGameMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreGameMenu(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void goToScoreboard() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ScoreBoardMenu(new ScoreBoardMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void goToTalentMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new TalentMenu(new TalentMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void loadLastSavedGame() {
        GameState savedGame = UserRepository.findUserByUsername(Main.getCurrentUser().getUsername()).getSavedGame();

        savedGame.getPlayer().initTransientFields();
        savedGame.getPlayer().getWeapon().initTransientFields(savedGame.getPlayer());
        savedGame.getPlayer().getWeapon().updateWeaponPosition();  // position weapon relative to player

        for (Enemy enemy : savedGame.getEnemies()) {
            enemy.initTransientField();
            if (enemy instanceof EyeBat) {
                for (Projectile projectile : ((EyeBat)enemy).getProjectileManager().getProjectiles()) {
                    projectile.initTransientField();
                }
            }
        }

        Main.getMain().getScreen().dispose();

        GameView gameView = new GameView(savedGame, GameAssetManager.getGameAssetManager().getSkin());

        Main.getMain().setScreen(gameView);
    }



    public void logout() {
        Main.setCurrentUser(null);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }
}
