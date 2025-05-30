package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.enums.AbilityType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelUpManager implements Screen {
    private final Player player;
    private final Stage stage;
    private final Table table;

    public LevelUpManager(Player player) {
        this.player = player;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.table = new Table();
    }

    @Override
    public void show() {
        if (Main.getCurrentUser().isSfxEnabled()) {
            GameAssetManager.getGameAssetManager().levelUpSound.play();
        }

        List<AbilityType> abilities = new ArrayList<>(List.of(AbilityType.values()));
        for (AbilityType abilityType : player.getAbilitiesObtained()) {
            abilities.remove(abilityType);
        }
        Collections.shuffle(abilities);
        List<AbilityType> randomThree;
        if (abilities.size() >= 3)
            randomThree = abilities.subList(0, 3);
        else
            randomThree = abilities;

        for (AbilityType ability : randomThree) {
            TextButton button = new TextButton(ability.getTitle(), GameAssetManager.getGameAssetManager().getSkin());

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    applyAbility(ability);
                    returnToGame();
                }
            });

            VerticalGroup group = new VerticalGroup();
            group.space(5);
            group.addActor(button);

            table.add(group).pad(10).row();
        }

        table.pack();
        table.setPosition(
            (stage.getViewport().getWorldWidth() - table.getWidth()) / 2,
            (stage.getViewport().getWorldHeight() - table.getHeight()) / 2
        );
        stage.addActor(table);

        Main.setPaused(true);
    }

    private void applyAbility(AbilityType ability) {
        AbilityType abilityType = null;
        switch (ability) {
            case VITALITY:
                player.increaseMaxHP(1);
                abilityType = AbilityType.VITALITY;
                break;
            case DAMAGER:
                player.activateDamageBoost(10);
                abilityType = AbilityType.DAMAGER;
                break;
            case PROCREASE:
                player.getWeapon().increaseProjectileCount(1);
                abilityType = AbilityType.PROCREASE;
                break;
            case AMOCREASE:
                player.getWeapon().increaseMaxAmmo(5);
                abilityType = AbilityType.AMOCREASE;
                break;
            case SPEEDY:
                player.activateSpeedBoost(10);
                abilityType = AbilityType.SPEEDY;
                break;
        }
        player.getAbilitiesObtained().add(abilityType);
    }

    private void returnToGame() {
        Main.setPaused(false);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(Main.getGameScreen()); // Go back to the main game screen
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);
        stage.act(Math.min(delta, 1 / 30f));
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

    public static float getNextLevelXP(int currentLevel) {
        return currentLevel * 20;
    }

    public void checkLevelUp() {
        if (player.getXp() >= 20 * player.getLevel()) {
            player.setXp(player.getXp() - 20 * player.getLevel());
            player.setLevel(player.getLevel() + 1);
            Main.getMain().setScreen(new LevelUpManager(player));
        }
    }
}
