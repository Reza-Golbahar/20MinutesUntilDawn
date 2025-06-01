package io.github.some_example_name.model.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.model.Bullet;
import io.github.some_example_name.model.CollisionRect;

public abstract class Enemy {
    protected Vector2 position;
    protected float hp;
    protected transient Sprite sprite;
    protected transient Texture texture;
    protected transient TextureRegion currentFrame; // New field
    protected CollisionRect collisionRect;

    protected float timeSinceLastDamage = 0.4f;
    protected Bullet lastBulletHit;

    protected boolean dying = false;
    protected float deathStateTime = 0f;
    protected transient Animation<TextureRegion> deathAnimation;

    //for saving
    public Enemy() {

    }

    public Enemy(Vector2 spawnPosition, int hp) {
        this.position = spawnPosition;
        this.hp = hp;
        // collisionRect will be initialized in subclass after setting sprite or texture
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void takeDamage(float damage, Bullet bullet) {
        this.lastBulletHit = bullet;
        timeSinceLastDamage = 0f;
        hp -= damage;
    }

    public abstract void update(float delta, Vector2 playerPosition);

    public abstract void render(SpriteBatch batch);

    public Sprite getSprite() {
        return sprite;
    }

    public CollisionRect getRect() {
        return collisionRect;
    }

    protected void updateCollisionRect() {
        collisionRect.move(position.x, position.y);
    }

    public boolean isDying() {
        return dying;
    }

    public void startDying() {
        dying = true;
        deathStateTime = 0f;
    }

    public float getDeathStateTime() {
        return deathStateTime;
    }

    public void setDeathStateTime(float deathStateTime) {
        this.deathStateTime = deathStateTime;
    }

    public abstract void initTransientField();
}
