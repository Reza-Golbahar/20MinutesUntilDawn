package io.github.some_example_name.model.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.model.CollisionRect;
import io.github.some_example_name.model.GameAssetManager;

public class Tree extends Enemy {

    //for saving
    public Tree() {

    }

    public Tree(Vector2 position) {
        super(position, 100); // Static, no damage handling needed
        this.texture = new Texture(GameAssetManager.getGameAssetManager().getTreeMonsterFrame());
        collisionRect = new CollisionRect(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void update(float delta, Vector2 playerPosition) {
        // Static tree, no update needed
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    @Override
    public void initTransientField() {
        this.texture = new Texture(GameAssetManager.getGameAssetManager().getTreeMonsterFrame());
    }
}

