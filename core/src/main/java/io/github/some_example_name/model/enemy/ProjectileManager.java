package io.github.some_example_name.model.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ProjectileManager {
    private final List<Projectile> projectiles = new ArrayList<>();

    //for saving
    public ProjectileManager() {

    }

    public void add(Projectile p) {
        projectiles.add(p);
    }

    public void update(float delta) {
        for (Projectile p : projectiles) {
            p.update(delta);
        }
        projectiles.removeIf(Projectile::isExpired);
    }

    public void render(SpriteBatch batch) {
        for (Projectile p : projectiles) {
            p.render(batch);
        }
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }
}
