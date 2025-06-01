package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enums.UIText;

public class HUD {
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private Player player;
    private Weapon weapon;
    private GameTimer gameTimer;
    private float totalGameTime; // in seconds

    private Texture bulletTexture;
    private Texture redHeartTexture;
    private Texture blackHeartTexture;

    public HUD(Player player, Weapon weapon, GameTimer gameTimer, float totalGameTime) {
        this.player = player;
        this.weapon = weapon;
        this.gameTimer = gameTimer;
        this.totalGameTime = totalGameTime;

        //Load .ttf font using FreeTypeFontGenerator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Font/LiberationSans.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        generator.dispose();

        shapeRenderer = new ShapeRenderer();

        bulletTexture = new Texture(GameAssetManager.getGameAssetManager().getAmmo());
        redHeartTexture = new Texture(GameAssetManager.getGameAssetManager().getRedHeartFrames()[0]);
        blackHeartTexture = new Texture(GameAssetManager.getGameAssetManager().getBlackHeart());
    }

    public void render() {
        float screenHeight = Gdx.graphics.getHeight();

        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        font.draw(batch, UIText.KILLS.get() + player.getKillCount(), 20, screenHeight - 50);

        float timeRemaining = totalGameTime - gameTimer.getElapsedTime();
        int min = Math.max(0, (int) (timeRemaining / 60));
        int sec = Math.max(0, (int) (timeRemaining % 60));
        font.draw(batch, String.format("%s%02d:%02d", UIText.TIME.get(), min, sec), 20, screenHeight - 80);

        font.draw(batch, UIText.AMMO.get() + weapon.getAmmo() + "/" + weapon.getMaxAmmo(), 20, screenHeight - 110);
        font.draw(batch, UIText.LEVEL.get() + player.getLevel(), 20, screenHeight - 140);

        renderHearts(batch);
        renderAmmo(batch);
        batch.end();
        renderXPBar();
    }


    private void renderXPBar() {
        float screenHeight = Gdx.graphics.getHeight();
        float x = 30;
        float y = screenHeight - 280;
        float barWidth = 200;
        float barHeight = 20;

        float xp = player.getXp();
        float xpMax = LevelUpManager.getNextLevelXP(player.getLevel());
        float progress = xpMax == 0 ? 0 : xp / xpMax;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(x, y, barWidth, barHeight);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(x, y, progress * barWidth, barHeight);
        shapeRenderer.end();
    }

    private void renderHearts(SpriteBatch batch) {
        float maxHP = player.getMaxHP();
        float currentHP = player.getPlayerHealth();

        float x = 20;
        float y = Gdx.graphics.getHeight() - 200;
        float size = 24;

        for (int i = 0; i < maxHP; i++) {
            Texture heartTexture = (i < currentHP) ? redHeartTexture : blackHeartTexture;
            batch.draw(heartTexture, x + i * (size + 5), y, size, size);
        }
    }

    private void renderAmmo(SpriteBatch batch) {
        int ammo = weapon.getAmmo();
        float x = 20;
        float y = Gdx.graphics.getHeight() - 240;
        float size = 16;

        for (int i = 0; i < ammo; i++) {
            batch.draw(bulletTexture, x + i * (size + 5), y, size, size);
        }
    }

    public void dispose() {
        bulletTexture.dispose();
        redHeartTexture.dispose();
        blackHeartTexture.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
