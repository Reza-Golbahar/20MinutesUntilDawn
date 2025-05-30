package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.view.MainMenu;

import java.util.Map;

public class SettingsMenuController {
    private static final Map<String, String> songMap = Map.of(
        "Hello-Adele", "BackgroundMusic/Hello.mp3",
        "Chandelier-Sia", "BackgroundMusic/Chandelier.mp3",
        "Venom-Eminem", "BackgroundMusic/Venom.mp3"
    );

    public void setMusicVolume(float value) {
        Main.getMusicPlayer().setVolume(value);
    }

    public void changeMusic(String selected) {
        String path = songMap.get(selected);
        if (path != null)
            Main.getMusicPlayer().play(path);
    }

    public void toggleSFX(boolean enabled) {
        Main.getCurrentUser().setSfxEnabled(enabled);
    }

    public void setAutoReload(boolean checked) {
        Main.getCurrentUser().setAutoReload(checked);
    }

    public void setBlackWhiteDisplay(boolean checked) {
        Main.getCurrentUser().setBlackAndWhiteWorld(checked);
    }

    public void goToMainMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }
}
