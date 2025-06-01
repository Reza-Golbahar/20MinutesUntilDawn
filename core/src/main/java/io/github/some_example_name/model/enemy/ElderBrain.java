package io.github.some_example_name.model.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.some_example_name.model.CollisionRect;
import io.github.some_example_name.model.GameAssetManager;

public class ElderBrain extends Enemy {

    private static final float DASH_INTERVAL = 5f;
    private static final float DASH_SPEED = 600f;
    private static final float NORMAL_SPEED = 50f;
    private static final int MAX_HEALTH = 400;

    private float dashTimer = 0f;
    private float dashDurationTimer = 0f;
    private float stateTime = 0f;
    private boolean isDashing = false;
    private Vector2 dashDirection = new Vector2();
    private static final float FRAME_DURATION = 0.15f;
    private Texture backgroundTexture;

    private transient Animation<TextureRegion> animation;
    private DashArena arena;

    //for saving
    public ElderBrain() {

    }

    public ElderBrain(Vector2 spawnPosition, DashArena arena) {
        super(spawnPosition, MAX_HEALTH);
        this.arena = arena;
        this.animation = createAnimationFromPaths(
            new String[]{GameAssetManager.getGameAssetManager().getElderBrain()}, 0.2f);
        texture = new Texture(GameAssetManager.getGameAssetManager().getElderBrain());
        collisionRect = new CollisionRect(position.x, position.y, texture.getWidth(), texture.getHeight());
        deathAnimation = createAnimationFromPaths(
            GameAssetManager.getGameAssetManager().getDamageAnimationFrames(), FRAME_DURATION);
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
        timeSinceLastDamage += delta;

        if (!isDashing && dashTimer >= DASH_INTERVAL) {
            dashDirection = playerPosition.cpy().sub(position).nor();
            isDashing = true;
            dashTimer = 0f;
            dashDurationTimer = 0f;
        }

        if (isDashing) {
            dashDurationTimer += delta;
            if (dashDurationTimer >= 1f) {
                isDashing = false;
            }
        }

        if (isDying())
            return;
        if (timeSinceLastDamage > 0.4) {
            Vector2 movement = isDashing
                ? dashDirection.cpy().scl(DASH_SPEED * delta)
                : playerPosition.cpy().sub(position).nor().scl(NORMAL_SPEED * delta);

            position.add(movement);
        } else {
            Vector2 knockbackDir = new Vector2(position.x - lastBulletHit.getX(), position.y - lastBulletHit.getY()).nor();
            position.add(knockbackDir.scl(NORMAL_SPEED * delta));
        }

        if (isDashing && dashTimer >= 1f) {
            isDashing = false;
        }

        if (isDead()) {
            arena.deactivate();
        }
        updateCollisionRect();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isDying()) {
            deathStateTime += Gdx.graphics.getDeltaTime();
            currentFrame = deathAnimation.getKeyFrame(deathStateTime);

            // Reset after short duration
            if (deathStateTime > 0.4f) { // ~2 frames if frameDuration = 0.2f
                dying = false;
                deathStateTime = 0f;
            }
        } else {
            currentFrame = animation.getKeyFrame(stateTime);
        }

        batch.draw(currentFrame, position.x, position.y);
    }

    @Override
    public void initTransientField() {
        this.animation = createAnimationFromPaths(
            new String[]{GameAssetManager.getGameAssetManager().getElderBrain()}, 0.2f);
        this.texture = new Texture(GameAssetManager.getGameAssetManager().getElderBrain());
        this.deathAnimation = createAnimationFromPaths(
            GameAssetManager.getGameAssetManager().getDamageAnimationFrames(), FRAME_DURATION);

        this.collisionRect = new CollisionRect(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public DashArena getArena() {
        return arena;
    }
}
