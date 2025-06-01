package io.github.some_example_name.model.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.some_example_name.model.CollisionRect;
import io.github.some_example_name.model.GameAssetManager;

public class MonsterTentacle extends Enemy {
    private static final float SPEED = 100f;
    private static final float FRAME_DURATION = 0.15f;

    private transient Animation<TextureRegion> spawnAnimation;
    private transient Animation<TextureRegion> idleAnimation;
    private float stateTime = 0f;
    private boolean spawning = true;

    //for saving
    public MonsterTentacle() {
        super();
    }

    public MonsterTentacle(Vector2 spawnPosition) {
        super(spawnPosition, 25);

        // Load spawn frames
        String[] spawnFrames = {
            "Images/Tentacle Monster/T_TentacleSpawn_0.png",
            "Images/Tentacle Monster/T_TentacleSpawn_1.png",
            "Images/Tentacle Monster/T_TentacleSpawn_2.png"
        };
        spawnAnimation = createAnimationFromPaths(spawnFrames, FRAME_DURATION);

        // Load idle frames
        String[] idleFrames = {
            "Images/Tentacle Monster/T_TentacleEnemy_0.png",
            "Images/Tentacle Monster/T_TentacleEnemy_1.png",
            "Images/Tentacle Monster/T_TentacleEnemy_2.png",
            "Images/Tentacle Monster/T_TentacleEnemy_3.png"
        };
        idleAnimation = createAnimationFromPaths(idleFrames, FRAME_DURATION);
        deathAnimation = createAnimationFromPaths(
            GameAssetManager.getGameAssetManager().getDamageAnimationFrames(), FRAME_DURATION);
        texture = new Texture("Images/Tentacle Monster/T_TentacleEnemy_0.png");
        collisionRect = new CollisionRect(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    private Animation<TextureRegion> createAnimationFromPaths(String[] paths, float frameDuration) {
        Array<TextureRegion> frames = new Array<>();
        for (String path : paths) {
            Texture texture = new Texture(path);  // Ideally get from asset manager
            frames.add(new TextureRegion(texture));
        }
        return new Animation<>(frameDuration, frames, Animation.PlayMode.LOOP);
    }

    @Override
    public void update(float delta, Vector2 playerPosition) {
        stateTime += delta;
        timeSinceLastDamage += delta;

        if (spawning) {
            // If spawn animation finished, switch to idle
            if (spawnAnimation.isAnimationFinished(stateTime)) {
                spawning = false;
                stateTime = 0f;
            }
        } else {
            // Move toward player when idle
            if (isDying())
                return;
            Vector2 direction = playerPosition.cpy().sub(position).nor();
            if (timeSinceLastDamage > 0.4)
                position.add(direction.scl(SPEED * delta));
            else {
                Vector2 knockbackDir = new Vector2(position.x - lastBulletHit.getX(), position.y - lastBulletHit.getY()).nor();
                position.add(knockbackDir.scl(SPEED * delta));
            }

        }
        updateCollisionRect();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (spawning) {
            currentFrame = spawnAnimation.getKeyFrame(stateTime);
        } else if (isDying()) {
            deathStateTime += Gdx.graphics.getDeltaTime();
            currentFrame = deathAnimation.getKeyFrame(deathStateTime);

            // Reset after short duration
            if (deathStateTime > 0.4f) { // ~2 frames if frameDuration = 0.2f
                dying = false;
                deathStateTime = 0f;
            }
        } else {
            currentFrame = idleAnimation.getKeyFrame(stateTime);
        }

        batch.draw(currentFrame, position.x, position.y);
    }

    @Override
    public void initTransientField() {
        // Reload spawn animation
        String[] spawnFrames = {
            "Images/Tentacle Monster/T_TentacleSpawn_0.png",
            "Images/Tentacle Monster/T_TentacleSpawn_1.png",
            "Images/Tentacle Monster/T_TentacleSpawn_2.png"
        };
        spawnAnimation = createAnimationFromPaths(spawnFrames, FRAME_DURATION);

        // Reload idle animation
        String[] idleFrames = {
            "Images/Tentacle Monster/T_TentacleEnemy_0.png",
            "Images/Tentacle Monster/T_TentacleEnemy_1.png",
            "Images/Tentacle Monster/T_TentacleEnemy_2.png",
            "Images/Tentacle Monster/T_TentacleEnemy_3.png"
        };
        idleAnimation = createAnimationFromPaths(idleFrames, FRAME_DURATION);

        // Reload death animation
        deathAnimation = createAnimationFromPaths(
            GameAssetManager.getGameAssetManager().getDamageAnimationFrames(), FRAME_DURATION);

        this.texture = new Texture("Images/Tentacle Monster/T_TentacleEnemy_0.png");
        this.collisionRect = new CollisionRect(position.x, position.y, texture.getWidth(), texture.getHeight());
    }
}
