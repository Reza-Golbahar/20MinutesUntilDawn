package io.github.some_example_name.model.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameTimer;
import io.github.some_example_name.model.ItemSeed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnemyManager {
    private List<ItemSeed> droppedSeeds = new ArrayList<>();
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final GameTimer gameTimer;

    private float tentacleSpawnTimer = 0f;
    private float eyebatSpawnTimer = 0f;
    private float lastTentacleSpawn = 0f;
    private float lastEyeBatSpawn = 0f;
    private boolean elderBrainSpawned = false;
    private final Texture backgroundTexture;
    private final ProjectileManager projectileManager;

    private static final float BOSS_SPAWN_TIME = Main.getPregame().getDuration() / 2f; // 2.5 minutes

    public EnemyManager(GameTimer gameTimer, Texture backgroundTexture, ProjectileManager projectileManager) {
        this.gameTimer = gameTimer;
        this.backgroundTexture = backgroundTexture;
        this.projectileManager = projectileManager;
    }

    public void update(float delta, Vector2 playerPosition) {
        float elapsedTime = gameTimer.getElapsedTime();
        tentacleSpawnTimer += delta;
        eyebatSpawnTimer += delta;

        // Spawn MonsterTentacle every 3 seconds if time > 30s
        if (elapsedTime > 30 && elapsedTime - lastTentacleSpawn >= 3f) {
            enemies.add(new MonsterTentacle(randomSpawn()));
            lastTentacleSpawn = elapsedTime;
        }

        // Spawn EyeBat on staggered intervals
        float spawnStart = 4 * (int) (elapsedTime / 10f) - elapsedTime + 30;
        if (elapsedTime > 30 && elapsedTime >= spawnStart && elapsedTime - lastEyeBatSpawn >= 10f) {
            enemies.add(new EyeBat(randomSpawn(), projectileManager));
            lastEyeBatSpawn = elapsedTime;
        }

        // Spawn ElderBrain boss once at BOSS_SPAWN_TIME
        if (!elderBrainSpawned && elapsedTime >= BOSS_SPAWN_TIME) {
            ElderBrain elder = new ElderBrain(new Vector2(300, 300), new DashArena(backgroundTexture.getWidth(), backgroundTexture.getHeight()));
            enemies.add(elder);
            elderBrainSpawned = true;
        }

        // Update enemies and remove dead ones
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy e = iterator.next();
            e.update(delta, playerPosition);
            if (e.isDead()) {
                droppedSeeds.add(new ItemSeed(e.getPosition().cpy()));
                iterator.remove();
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Enemy e : enemies) {
            e.render(batch);
        }
    }

    public void spawnInitialTrees(int count) {
        for (int j = 0; j < count; j++) {
            enemies.add(new Tree(randomSpawn()));
        }
    }

    private Vector2 randomSpawn() {
        float x = (float) (Math.random() * backgroundTexture.getWidth());
        float y = (float) (Math.random() * backgroundTexture.getHeight());
        return new Vector2(x, y);
    }

    public void renderSeeds(SpriteBatch batch) {
        for (ItemSeed seed : droppedSeeds) {
            seed.render(batch);
        }
    }


    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public List<ItemSeed> getDroppedSeeds() {
        return droppedSeeds;
    }
}
