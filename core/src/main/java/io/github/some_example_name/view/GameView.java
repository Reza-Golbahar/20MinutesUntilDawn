package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.GameController;
import io.github.some_example_name.model.ControlsMapping;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.enemy.ElderBrain;
import io.github.some_example_name.model.enemy.EnemyManager;
import io.github.some_example_name.model.enums.ActionType;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController controller;

    private HUD hud;
    //for ElderBrain
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private EnemyManager enemyManager;

    public GameView(GameController controller, Skin skin) {
        this.controller = controller;
        controller.create(); // Set up the camera
        GameAssetManager.getGameAssetManager().loadSounds();
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        // Conditional shader logic
        if (Main.getCurrentUser().isBlackAndWhiteWorld()) {
            Main.getBatch().setShader(GameAssetManager.getGrayscaleShader());
        } else {
            Main.getBatch().setShader(null); // use default
        }

        Main.getBatch().setProjectionMatrix(controller.getCamera().combined);

        Main.getBatch().begin();
        controller.updateGame(delta);
        Main.getBatch().end();

        hud.render();
        ElderBrain elderBrain = enemyManager.getElderBrain();
        if (elderBrain != null) {
            // Begin shape rendering first
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // Use Line or Filled
            elderBrain.getArena().render(shapeRenderer);
            shapeRenderer.end();
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        stage.setDebugAll(true);
    }

    @Override
    public void resize(int width, int height) {

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
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Handle mouse shooting only if mapped to a mouse button
        int mapped = ControlsMapping.getInstance().getKey(ActionType.Shoot);
        if (mapped == Input.Buttons.LEFT && button == Input.Buttons.LEFT) {
            controller.getWeaponController().shootAtScreen(screenX, screenY);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void setHUD(HUD hud) {
        this.hud = hud;
    }

    public void setEnemyManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }
}
