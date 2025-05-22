package io.github.some_example_name.model.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.model.CollisionRect;

public abstract class Enemy {
    protected Vector2 position;
    protected int hp;
    protected Sprite sprite;
    protected Texture texture;
    protected CollisionRect collisionRect;

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

    public void takeDamage(int damage) {
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
        if (texture != null) {
            if (collisionRect == null)
                collisionRect = new CollisionRect(position.x, position.y, texture.getWidth(), texture.getHeight());
            else
                collisionRect.move(position.x, position.y);
        } else if (sprite != null) {
            if (collisionRect == null)
                collisionRect = new CollisionRect(position.x, position.y, sprite.getWidth(), sprite.getHeight());
            else
                collisionRect.move(position.x, position.y);
        }
    }
}
