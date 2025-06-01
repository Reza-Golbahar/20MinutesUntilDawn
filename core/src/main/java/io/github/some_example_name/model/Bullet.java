package io.github.some_example_name.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private transient Sprite sprite;
    private Vector2 direction;
    private float speed = 500f;
    private float damage;
    private CollisionRect rect;

    //for saving
    public Bullet() {

    }

    public Bullet(float startX, float startY, float targetX, float targetY, float damage) {
        Texture texture = new Texture(GameAssetManager.getGameAssetManager().getBullet());
        this.sprite = new Sprite(texture);
        this.sprite.setSize(20, 20);
        this.sprite.setPosition(startX, startY);

        Vector2 dir = new Vector2(targetX - startX, targetY - startY).nor();
        this.direction = dir;
        this.damage = damage;

        this.rect = new CollisionRect(startX, startY, sprite.getWidth(), sprite.getHeight());
    }

    public void rebuildSprite() {
        Texture texture = new Texture(GameAssetManager.getGameAssetManager().getBullet());
        this.sprite = new Sprite(texture);
        this.sprite.setSize(20, 20);

        // reposition sprite based on current rect or direction
        this.sprite.setPosition(rect.x, rect.y);
    }

    public void update(float delta) {
        float vx = direction.x * speed * delta;
        float vy = direction.y * speed * delta;
        sprite.translate(vx, vy);

        rect.move(sprite.getX(), sprite.getY());
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

    public void setX(float x) {
        sprite.setX(x);
    }

    public void setY(float y) {
        sprite.setY(y);
    }

    public float getDamage() {
        return damage;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public void initTransientFields() {
        // Rebuild sprite from asset manager
        Texture texture = new Texture(GameAssetManager.getGameAssetManager().getBullet());
        this.sprite = new Sprite(texture);
        this.sprite.setSize(20, 20);

        // Reposition based on rect if available, or fallback to origin
        if (rect != null) {
            this.sprite.setPosition(rect.x, rect.y);
        } else {
            this.sprite.setPosition(0, 0);
            this.rect = new CollisionRect(0, 0, 20, 20);
        }
    }

}
