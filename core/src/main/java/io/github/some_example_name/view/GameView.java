package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.GameController;
import io.github.some_example_name.model.ControlsMapping;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.GameState;
import io.github.some_example_name.model.Weapon;
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

    public GameView(GameState gameState, Skin skin) {
        Main.setPregame(gameState.getPregame());
        this.controller = new GameController();
        controller.create();
        GameAssetManager.getGameAssetManager().loadSounds();
        controller.setView(this);
        this.controller.getPlayerController().setPlayer(gameState.getPlayer());
        this.enemyManager.setEnemies(gameState.getEnemies());
        this.controller.setGameTimer(gameState.getGameTimer());

        this.controller.setHud(new HUD(gameState.getPlayer(),gameState.getPlayer().getWeapon()
            ,gameState.getGameTimer(), gameState.getPregame().getDuration()));
        hud = this.controller.getHud();

        Weapon weapon = new Weapon(gameState.getPlayer(), Main.getPregame().getWeaponType());
        weapon.initTransientFields(gameState.getPlayer());
        this.controller.getPlayerController().getPlayer().setWeapon(weapon);
        this.controller.getWeaponController().setWeapon(weapon);
    }

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

        Pixmap pixmap = new Pixmap(Gdx.files.internal("Images/T_Cursor.png"));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(customCursor);
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        // === Set up camera and batch ===
        Main.getBatch().setProjectionMatrix(controller.getCamera().combined);

        // === Start rendering everything in one batch ===
        Main.getBatch().begin();

        // Apply shaders or overlays (renderLightOverlay must be rewritten)
        applyShaders(); // Just sets the shader, no drawing

        controller.updateGame(delta); // Game world render logic

        Main.getBatch().end();

        // === Render ElderBrain Arena ===
        renderElderBrainArena(delta);

        // === Render HUD ===
        hud.render();

        // === Stage UI ===
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        stage.setDebugAll(true);
    }

    private void renderElderBrainArena(float delta) {
        shapeRenderer.setProjectionMatrix(controller.getCamera().combined);
        ElderBrain elderBrain = enemyManager.getElderBrain();
        if (elderBrain != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            elderBrain.getArena().render(shapeRenderer);
            elderBrain.getArena().update(delta);
            shapeRenderer.end();
        }
    }

    private void applyShaders() {
        // === Choose shader based on settings ===
        if (Main.getCurrentUser().isBlackAndWhiteWorld()) {
            Main.getBatch().setShader(GameAssetManager.getGrayscaleShader());
        } else {
            Main.getBatch().setShader(null);
        }
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

    public GameController getController() {
        return controller;
    }
}
