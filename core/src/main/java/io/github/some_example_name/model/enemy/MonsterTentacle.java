package io.github.some_example_name.model.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MonsterTentacle extends Enemy {
    private static final float SPEED = 100f;
    private static final float FRAME_DURATION = 0.2f;

    private Animation<TextureRegion> spawnAnimation;
    private Animation<TextureRegion> idleAnimation;
    private float stateTime = 0f;
    private boolean spawning = true;

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
        updateCollisionRect();
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

        if (spawning) {
            // If spawn animation finished, switch to idle
            if (spawnAnimation.isAnimationFinished(stateTime)) {
                spawning = false;
                stateTime = 0f;
            }
        } else {
            // Move toward player when idle
            Vector2 direction = playerPosition.cpy().sub(position).nor();
            position.add(direction.scl(SPEED * delta));
        }
        updateCollisionRect();
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = spawning ?
            spawnAnimation.getKeyFrame(stateTime) :
            idleAnimation.getKeyFrame(stateTime);

        batch.draw(currentFrame, position.x, position.y);
    }
}
