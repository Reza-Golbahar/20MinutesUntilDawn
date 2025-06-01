package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.controller.SignupMenuController;
import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enums.Language;
import io.github.some_example_name.view.GameView;
import io.github.some_example_name.view.SignupMenu;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;

    private static User currentUser;
    private static Pregame pregame;

    private static MusicPlayer musicPlayer;
    private static boolean paused = false;

    private static GameView gameScreen;

    private static Language language = Language.ENGLISH;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        main.setScreen(new SignupMenu(new SignupMenuController(), GameAssetManager.getGameAssetManager().getSkin()));

        musicPlayer = new MusicPlayer();
        musicPlayer.play(GameAssetManager.getGameAssetManager().getBackGroundMusicPaths()[0]);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }

    public static void setCurrentUser(User currentUser) {
        Main.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    public static boolean isPaused() {
        return paused;
    }

    public static void setPaused(boolean paused) {
        Main.paused = paused;
    }

    public static void setPregame(Pregame pregame) {
        Main.pregame = pregame;
    }

    public static Pregame getPregame() {
        return pregame;
    }

    public static GameView getGameScreen() {
        return gameScreen;
    }

    public static void setGameScreen(GameView gameScreen) {
        Main.gameScreen = gameScreen;
    }

    public static Language getLanguage() {
        return language;
    }

    public static void setLanguage(Language language) {
        Main.language = language;
    }
}
