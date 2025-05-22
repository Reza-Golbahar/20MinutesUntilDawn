package io.github.some_example_name.model.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.some_example_name.model.GameAssetManager;

public class ElderBrain extends Enemy {

    private static final float DASH_INTERVAL = 5f;
    private static final float DASH_SPEED = 600f;
    private static final float NORMAL_SPEED = 50f;
    private static final int MAX_HEALTH = 400;

    private float dashTimer = 0f;
    private float stateTime = 0f;
    private boolean isDashing = false;
    private Vector2 dashDirection = new Vector2();

    private Animation<TextureRegion> animation;
    private DashArena arena;

    public ElderBrain(Vector2 spawnPosition, DashArena arena) {
        super(spawnPosition, MAX_HEALTH);
        this.arena = arena;
        this.animation = createAnimationFromPaths(
            new String[]{GameAssetManager.getGameAssetManager().getElderBrain()}, 0.2f);
        updateCollisionRect();
    }

    private Animation<TextureRegion> createAnimationFromPaths(String[] paths, float duration) {
        Array<TextureRegion> frames = new Array<>();
        for (String path : paths) {
            Texture texture = new Texture(path);
            frames.add(new TextureRegion(texture));
        }
        return new Animation<>(duration, frames, Animation.PlayMode.LOOP);
    }

    @Override
    public void update(float delta, Vector2 playerPosition) {
        stateTime += delta;
        dashTimer += delta;

        arena.update(delta);

        if (dashTimer >= DASH_INTERVAL) {
            dashDirection = playerPosition.cpy().sub(position).nor();
            isDashing = true;
            dashTimer = 0f;
        }

        Vector2 movement = isDashing
            ? dashDirection.cpy().scl(DASH_SPEED * delta)
            : playerPosition.cpy().sub(position).nor().scl(NORMAL_SPEED * delta);

        position.add(movement);

        if (isDashing && dashTimer >= 1f) {
            isDashing = false;
        }

        if (arena.collidesWith(position)) {
            takeDamage(10);
        }

        if (isDead()) {
            arena.deactivate();
        }
        updateCollisionRect();
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion current = animation.getKeyFrame(stateTime);
        batch.draw(current, position.x, position.y);
    }
}
