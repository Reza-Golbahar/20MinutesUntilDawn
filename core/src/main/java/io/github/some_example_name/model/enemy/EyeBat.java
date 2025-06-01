package io.github.some_example_name.model.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.some_example_name.model.CollisionRect;
import io.github.some_example_name.model.GameAssetManager;

public class EyeBat extends Enemy {
    private static final float SPEED = 150f;
    private static final float SHOOT_INTERVAL = 3f;
    private static final float FRAME_DURATION = 0.15f;
    private static final int PROJECTILE_DAMAGE = 10;

    private transient Animation<TextureRegion> flyAnimation;
    private float shootTimer = 0f;
    private float stateTime = 0f;
    private ProjectileManager projectileManager;

    //for saving
    public EyeBat() {

    }

    public EyeBat(Vector2 spawnPosition, ProjectileManager projectileManager) {
        super(spawnPosition, 50);
        this.projectileManager = projectileManager;
        flyAnimation = createAnimationFromPaths(GameAssetManager.getGameAssetManager().getEyeBatFrames(), FRAME_DURATION);
        texture = new Texture(GameAssetManager.getGameAssetManager().getEyeBatFrames()[0]);
        collisionRect = new CollisionRect(position.x, position.y, texture.getWidth(), texture.getHeight());
        deathAnimation = createAnimationFromPaths(
            GameAssetManager.getGameAssetManager().getDamageAnimationFrames(), FRAME_DURATION);
    }

    private Animation<TextureRegion> createAnimationFromPaths(String[] paths, float frameDuration) {
        Array<TextureRegion> frames = new Array<>();
        for (String path : paths) {
            Texture texture = new Texture(path);
            frames.add(new TextureRegion(texture));
        }
        return new Animation<>(frameDuration, frames, Animation.PlayMode.LOOP);
    }


    @Override
    public void update(float delta, Vector2 playerPosition) {
        stateTime += delta;
        shootTimer += delta;
        timeSinceLastDamage += delta;

        if (isDying())
            return;
        if (timeSinceLastDamage > 0.4) {
            Vector2 direction = playerPosition.cpy().sub(position).nor();
            position.add(direction.scl(SPEED * delta));
        } else {
            Vector2 knockbackDir = new Vector2(position.x - lastBulletHit.getX(), position.y - lastBulletHit.getY()).nor();
            position.add(knockbackDir.scl(SPEED * delta));
        }

        if (shootTimer >= SHOOT_INTERVAL) {
            shootAt(playerPosition);
            shootTimer = 0f;
        }
        updateCollisionRect();
    }

    private void shootAt(Vector2 target) {
        Vector2 direction = target.cpy().sub(position).nor();
        float bulletSpeed = 300f;
        Vector2 velocity = direction.scl(bulletSpeed);

        Projectile projectile = new Projectile(
            position.cpy(),
            velocity,
            PROJECTILE_DAMAGE
        );
        projectileManager.add(projectile);
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
            currentFrame = flyAnimation.getKeyFrame(stateTime);
        }

        batch.draw(currentFrame, position.x, position.y);
    }

    @Override
    public void initTransientField() {
        flyAnimation = createAnimationFromPaths(GameAssetManager.getGameAssetManager().getEyeBatFrames(), FRAME_DURATION);
        texture = new Texture(GameAssetManager.getGameAssetManager().getEyeBatFrames()[0]);
        collisionRect = new CollisionRect(position.x, position.y, texture.getWidth(), texture.getHeight());
        deathAnimation = createAnimationFromPaths(GameAssetManager.getGameAssetManager().getDamageAnimationFrames(), FRAME_DURATION);
    }

    public ProjectileManager getProjectileManager() {
        return projectileManager;
    }
}
