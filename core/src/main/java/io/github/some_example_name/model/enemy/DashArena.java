package io.github.some_example_name.model.enemy;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class DashArena {

    private Rectangle bounds;
    private float shrinkSpeed = 20f;
    private boolean active = true;

    public DashArena(float screenWidth, float screenHeight) {
        bounds = new Rectangle(0, 0, screenWidth, screenHeight);
    }

    public void update(float delta) {
        if (!active) return;

        bounds.x += shrinkSpeed * delta;
        bounds.y += shrinkSpeed * delta;
        bounds.width -= 2 * shrinkSpeed * delta;
        bounds.height -= 2 * shrinkSpeed * delta;

        if (bounds.width < 100 || bounds.height < 100) {
            bounds.setSize(100, 100);
        }
    }

    public boolean collidesWith(Vector2 position) {
        return active && !bounds.contains(position);
    }

    public void render(ShapeRenderer renderer) {
        if (!active) return;
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void deactivate() {
        active = false;
    }
}

