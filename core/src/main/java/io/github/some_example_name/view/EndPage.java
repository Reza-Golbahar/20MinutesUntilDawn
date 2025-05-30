package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.EndPageController;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.GameTimer;
import io.github.some_example_name.model.Player;
import io.github.some_example_name.model.UserRepository;

public class EndPage implements Screen {
    private final EndPageController controller;
    private Stage stage;
    private final Label titleLabel;
    private final Label usernameLabel;
    private final Label surviveTimeLabel;
    private final Label killCountLabel;
    private final Label scoreLabel;
    private final Label winOrLoseLabel;
    private final Table table;

    public EndPage(EndPageController controller, Skin skin, Player player, GameTimer gameTimer, boolean won) {
        this.controller = controller;
        this.titleLabel = new Label("End Page", skin);
        this.usernameLabel =  new Label("Username: %s".formatted(Main.getCurrentUser().getUsername()), skin);

        float score = gameTimer.getElapsedTime() * player.getKillCount();
        Main.getCurrentUser().setScore(Main.getCurrentUser().getScore() + score);

        this.surviveTimeLabel = new Label("Time survived: %.1f".formatted(gameTimer.getElapsedTime()), skin);
        Main.getCurrentUser().setSurvivalTime(Main.getCurrentUser().getSurvivalTime() + gameTimer.getElapsedTime());

        this.killCountLabel = new Label("Kill Count: %s".formatted(player.getKillCount()), skin);
        this.scoreLabel = new Label("Score: %.1f".formatted(score), skin);

        if (won) {
            this.winOrLoseLabel = new Label("Win", skin);
            if (Main.getCurrentUser().isSfxEnabled())
                GameAssetManager.getGameAssetManager().youWinSound.play();
        }
        else {
            this.winOrLoseLabel = new Label("Dead", skin);
            if (Main.getCurrentUser().isSfxEnabled())
                GameAssetManager.getGameAssetManager().youLostSound.play();
        }

        this.table = new Table();
        controller.setView(this);
        UserRepository.saveUsers();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();  // Center table contents
        table.padTop(100);  // Optional: space from top of screen

        // Add labels to the table
        table.add(titleLabel).pad(10).row();
        table.add(usernameLabel).pad(10).row();
        table.add(surviveTimeLabel).pad(10).row();
        table.add(killCountLabel).pad(10).row();
        table.add(scoreLabel).pad(10).row();
        table.add(winOrLoseLabel).pad(20).row();

        stage.addActor(table);
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
