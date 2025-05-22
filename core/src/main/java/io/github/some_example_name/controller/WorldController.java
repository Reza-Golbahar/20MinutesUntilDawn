package io.github.some_example_name.controller;

import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.Main;

public class WorldController {
    private PlayerController playerController;
    private Texture backgroundTexture;
    private float backgroundX = 0;
    private float backgroundY = 0;

    public WorldController(PlayerController playerController) {
        this.backgroundTexture = new Texture("background.png");
        this.playerController = playerController;
    }

    public void update() {
        Main.getBatch().draw(backgroundTexture, 0, 0);
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }
}
