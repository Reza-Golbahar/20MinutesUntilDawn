package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.view.MainMenu;
import io.github.some_example_name.view.PauseMenu;

public class PauseMenuController {
    private PauseMenu view;

    public void returnToGame() {
        Main.setPaused(false);
        Main.getMain().setScreen(Main.getGameScreen());
    }

    public void saveAndQuit() {
        //TODO: save the game
        //Main.getGame().save();
        Gdx.app.exit();
    }

    public void giveUp() {
        Main.setPaused(false);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenu(new MainMenuController() , GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void toggleBlackWhite(boolean enabled) {
        Main.getCurrentUser().setBlackAndWhiteWorld(enabled);
    }

    public boolean isBlackWhite() {
        return Main.getCurrentUser().isBlackAndWhiteWorld();
    }

    public void setView(PauseMenu pauseMenu) {
        this.view = pauseMenu;
    }
}
