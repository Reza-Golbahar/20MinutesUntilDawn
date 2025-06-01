package io.github.some_example_name.model.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.model.CollisionRect;
import io.github.some_example_name.model.GameAssetManager;

public class Projectile {
    private Vector2 position;
    private Vector2 velocity;
    private float lifeTime = 3f;
    private transient Texture texture;
    private int damage;
    private CollisionRect collisionRect;

    //for saving
    public Projectile() {

    }

    public Projectile(Vector2 position, Vector2 velocity, int damage) {
        this.position = position;
        this.velocity = velocity;
        this.damage = damage;
        this.texture = new Texture(GameAssetManager.getGameAssetManager().getEyeBatProjectile());
        updateCollisionRect();
    }

    public void initTransientField() {
        texture = new Texture(GameAssetManager.getGameAssetManager().getEyeBatProjectile());
        updateCollisionRect();
    }


    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
        lifeTime -= delta;
        updateCollisionRect();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public boolean isExpired() {
        return lifeTime <= 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public int getDamage() {
        return damage;
    }

    public CollisionRect getRect() {
        return collisionRect;
    }

    private void updateCollisionRect() {
        if (collisionRect == null) {
            collisionRect = new CollisionRect(position.x, position.y, texture.getWidth(), texture.getHeight());
        } else {
            collisionRect.move(position.x, position.y);
        }
    }
}
