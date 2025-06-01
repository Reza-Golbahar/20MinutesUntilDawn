package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.view.MainMenu;

public class TalentMenuController {
    public void goToMainMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void loadSavedGame() {
        (new MainMenuController()).loadLastSavedGame();
    }
}
