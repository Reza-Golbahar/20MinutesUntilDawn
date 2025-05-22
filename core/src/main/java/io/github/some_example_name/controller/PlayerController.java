package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.Player;

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

        if (player.isPlayerRunning()) {
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

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setPosY(player.getPosY() + player.getSpeed());
            moving = true;
            player.getRect().move(0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setPosY(player.getPosY() - player.getSpeed());
            moving = true;
            player.getRect().move(0, -1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setPosX(player.getPosX() - player.getSpeed());
            player.getPlayerSprite().setFlip(true, false);
            moving = true;
            player.getRect().move(-1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setPosX(player.getPosX() + player.getSpeed());
            player.getPlayerSprite().setFlip(false, false);
            moving = true;
            player.getRect().move(1, 0);
        }

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


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
