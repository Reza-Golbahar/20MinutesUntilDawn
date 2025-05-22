package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.model.enums.HeroType;

import java.util.ArrayList;

public class Player {
    private Texture playerTexture;
    private Sprite playerSprite;

    private float posX = 0;
    private float posY = 0;
    private int xp = 0;
    private float invincibleTimer = 0;

    private HeroType heroType;
    private float playerHealth;
    private float speed;

    private CollisionRect rect ;
    private float time = 0;
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    float spriteWidth;
    float spriteHeight;


    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;

    public Player(HeroType heroType) {
        this.heroType = heroType;

        // Load texture based on selected hero
        this.playerTexture = new Texture(Gdx.files.internal(heroType.getTexturePath()));
        this.playerSprite = new Sprite(playerTexture);
        this.speed = heroType.getSpeed();
        this.playerHealth = heroType.getHp();

        // Set size and position
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);

        spriteWidth = playerSprite.getWidth();
        spriteHeight = playerSprite.getHeight();

        playerSprite.setPosition(
            screenWidth / 2f - spriteWidth / 2f,
            screenHeight / 2f - spriteHeight / 2f
        );

        this.rect = new CollisionRect(
            screenWidth / 2f - spriteWidth / 2f,
            screenHeight / 2f - spriteHeight / 2f,
            spriteWidth,
            spriteHeight
        );
    }

    public float getSpeed() {
        return speed;
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public void setPlayerTexture(Texture playerTexture) {
        this.playerTexture = playerTexture;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void setPlayerSprite(Sprite playerSprite) {
        this.playerSprite = playerSprite;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(float playerHealth) {
        this.playerHealth = playerHealth;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public void updateRect() {
        rect.move(posX, posY);
    }

    public void setRect(CollisionRect rect) {
        this.rect = rect;
    }


    public boolean isPlayerIdle() {
        return isPlayerIdle;
    }

    public void setPlayerIdle(boolean playerIdle) {
        isPlayerIdle = playerIdle;
    }

    public boolean isPlayerRunning() {
        return isPlayerRunning;
    }

    public void setPlayerRunning(boolean playerRunning) {
        isPlayerRunning = playerRunning;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public Vector2 getPosition() {
        return new Vector2(posX, posY);
    }

    public HeroType getHeroType() {
        return heroType;
    }

    public void addXp(int amount) {
        xp += amount;
    }

    public int getXp() {
        return xp;
    }

    public float getInvincibleTimer() {
        return invincibleTimer;
    }

    public void decreaseInvincibleTime(float amount) {
        this.invincibleTimer -= amount;
    }

    public boolean isInvincible() {
        return invincibleTimer > 0;
    }

    public void takeDamage(float amount) {
        if (invincibleTimer <= 0) {
            playerHealth -= amount;
            invincibleTimer = 1f;
            return;
        }
    }
}
