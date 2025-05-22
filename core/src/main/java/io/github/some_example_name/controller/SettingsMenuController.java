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
        Main.setSFXEnabled(enabled); // TODO: implement logic to mute or skip SFX playback
    }

    public void setAutoReload(boolean checked) {
        //TODO
    }

    public void setBlackWhiteDisplay(boolean checked) {
        //TODO
    }

    public void goToMainMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }
}
