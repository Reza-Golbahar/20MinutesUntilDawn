package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.ControlsMapping;
import io.github.some_example_name.model.GameTimer;
import io.github.some_example_name.model.LevelUpManager;
import io.github.some_example_name.model.Player;
import io.github.some_example_name.model.enemy.DashArena;
import io.github.some_example_name.model.enemy.ElderBrain;
import io.github.some_example_name.model.enemy.EnemyManager;
import io.github.some_example_name.model.enums.ActionType;

public class CheatCodeHandler {
    private final Player player;
    private final EnemyManager enemyManager;
    private final GameTimer gameTimer;
    private final Texture backgroundTexture;

    private boolean isTripleProjectionActive = false;
    private float tripleProjectionCountTimer = 0f;
    private final float tripleProjectionTimeLimit = 10f;

    public CheatCodeHandler(Player player, EnemyManager enemyManager, GameTimer gameTimer, Texture backgroundTexture) {
        this.player = player;
        this.enemyManager = enemyManager;
        this.gameTimer = gameTimer;
        this.backgroundTexture = backgroundTexture;
    }

    public void checkPlayerInput() {
        if (Gdx.input.isKeyJustPressed(ControlsMapping.getInstance().getKey(ActionType.BossFightCheatCode))) {
            goToBossFight();
        } else if (Gdx.input.isKeyJustPressed(ControlsMapping.getInstance().getKey(ActionType.FastForward1MinuteCheatCode))) {
            fastForward1Minute();
        } else if (Gdx.input.isKeyJustPressed(ControlsMapping.getInstance().getKey(ActionType.IncreaseHPCheatCode))) {
            increaseHP();
        } else if (Gdx.input.isKeyJustPressed(ControlsMapping.getInstance().getKey(ActionType.LevelUpCheatCode))) {
            levelUp();
        } else if (Gdx.input.isKeyJustPressed(ControlsMapping.getInstance().getKey(ActionType.TripleProjectionCountCheatCode))) {
            tripleProjectionCount();
        }
    }

    private void tripleProjectionCount() {
        isTripleProjectionActive = true;
    }

    private void levelUp() {
        player.setLevel(player.getLevel() + 1);
        Main.getMain().setScreen(new LevelUpManager(player));
    }

    private void increaseHP() {
        if (player.getPlayerHealth() < player.getMaxHP())
            player.setPlayerHealth(player.getPlayerHealth() + 1);
    }

    private void fastForward1Minute() {
        gameTimer.setElapsedTime(gameTimer.getElapsedTime() + 60);
    }

    private void goToBossFight() {
        if (!enemyManager.isElderBrainSpawned()) {
            enemyManager.setElderBrainSpawned(true);
            ElderBrain elderBrain = new ElderBrain(new Vector2(300, 300), new DashArena(backgroundTexture.getWidth(), backgroundTexture.getHeight()));;
            enemyManager.setElderBrain(elderBrain);
            enemyManager.getEnemies().add(elderBrain);
        }
    }

    public void update(float delta) {
        if (isTripleProjectionActive) {
            tripleProjectionCountTimer += delta;
            if (tripleProjectionCountTimer >= tripleProjectionTimeLimit) {
                tripleProjectionCountTimer = 0f;
                isTripleProjectionActive = false;
            }
        }
    }

    public boolean isTripleProjectionActive() {
        return isTripleProjectionActive;
    }
}
