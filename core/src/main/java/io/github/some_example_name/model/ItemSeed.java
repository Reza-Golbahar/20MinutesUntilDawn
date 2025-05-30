package io.github.some_example_name.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ItemSeed {
    private Vector2 position;
    private CollisionRect rect;
    private Texture texture;

    public ItemSeed(Vector2 position) {
        this.position = position;
        this.texture = new Texture("Images/Elder/ElderBrain_Em.png");
        this.rect = new CollisionRect(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public CollisionRect getRect() {
        return rect;
    }

    public Vector2 getPosition() {
        return position;
    }
}
