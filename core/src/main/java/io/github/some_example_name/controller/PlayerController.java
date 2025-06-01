package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.ControlsMapping;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.Player;
import io.github.some_example_name.model.enums.ActionType;

public class PlayerController {
    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void update(float delta) {
        if (player.isInvincible()) {
            player.getPlayerSprite().setAlpha(0.5f);
        } else {
            player.getPlayerSprite().setAlpha(1f);
        }
        player.getPlayerSprite().setPosition(player.getPosX(), player.getPosY());

        handlePlayerInput();

        if (player.isTookDamage()){
            damageAnimation(delta);
        } else if (player.isPlayerRunning()) {
            runAnimation();
        } else if (!player.isPlayerIdle()) {
            walkAnimation();
        } else {
            idleAnimation();
        }

        if (player.getInvincibleTimer() > 0) {
            player.decreaseInvincibleTime(delta);
        }
        player.getPlayerSprite().draw(Main.getBatch());
    }

    public void handlePlayerInput() {
        boolean moving = false;
        float actualSpeed = player.getSpeed() * player.getSpeedMultiplier();

        if (Gdx.input.isKeyPressed(ControlsMapping.getInstance().getKey(ActionType.MoveUp))) {
            player.setPosY(player.getPosY() + actualSpeed);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(ControlsMapping.getInstance().getKey(ActionType.MoveDown))) {
            player.setPosY(player.getPosY() - actualSpeed);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(ControlsMapping.getInstance().getKey(ActionType.MoveLeft))) {
            player.setPosX(player.getPosX() - actualSpeed);
            player.getPlayerSprite().flip(true, false);  // flip horizontally
            moving = true;
        }
        if (Gdx.input.isKeyPressed(ControlsMapping.getInstance().getKey(ActionType.MoveRight))) {
            player.setPosX(player.getPosX() + actualSpeed);
            moving = true;
        }

        player.getRect().move(player.getPosX(), player.getPosY());
        player.setPlayerIdle(!moving);
        player.setPlayerRunning(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && moving);
    }


    public void idleAnimation() {
        Animation<Texture> idle = GameAssetManager.getGameAssetManager().getIdleAnimation(player.getHeroType());
        player.getPlayerSprite().setRegion(idle.getKeyFrame(player.getTime(), true));
        player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
    }

    public void walkAnimation() {
        Animation<Texture> walk = GameAssetManager.getGameAssetManager().getWalkAnimation(player.getHeroType());
        player.getPlayerSprite().setRegion(walk.getKeyFrame(player.getTime(), true));
        player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
    }

    public void runAnimation() {
        Animation<Texture> run = GameAssetManager.getGameAssetManager().getRunAnimation(player.getHeroType());
        player.getPlayerSprite().setRegion(run.getKeyFrame(player.getTime(), true));
        player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
    }

    public void damageAnimation(float delta) {
        Animation<Texture> damage = GameAssetManager.getGameAssetManager().createAnimationFromPaths(
            GameAssetManager.getGameAssetManager().getDamageAnimationFrames(), 0.15f
        );
        player.getPlayerSprite().setRegion(damage.getKeyFrame(player.getDamageStateTime(), true));
        player.setDamageStateTime(player.getDamageStateTime() + delta);

        // Stop damage animation after 0.4s (~2 frames)
        if (player.getDamageStateTime() > 0.4f) {
            player.setTookDamage(false);
            player.setDamageStateTime(0f);
        }
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
