package io.github.some_example_name.model.enemy;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class DashArena {
    private Rectangle safeZone;
    private float shrinkRate = 10f;

    public DashArena(float width, float height) {
        this.safeZone = new Rectangle(0, 0, width, height);
    }

    public void update(float delta) {
        float shrinkAmount = shrinkRate * delta;
        safeZone.x += shrinkAmount / 2f;
        safeZone.y += shrinkAmount / 2f;
        safeZone.width -= shrinkAmount;
        safeZone.height -= shrinkAmount;
    }

    public boolean collidesWith(Vector2 position) {
        return !safeZone.contains(position);
    }

    public void deactivate() {
        safeZone.setSize(0, 0);
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(safeZone.x, safeZone.y, safeZone.width, safeZone.height);
    }
}
