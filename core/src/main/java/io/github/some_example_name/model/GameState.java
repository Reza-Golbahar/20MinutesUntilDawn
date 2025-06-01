package io.github.some_example_name.model;

import io.github.some_example_name.model.enemy.Enemy;

import java.util.ArrayList;

public class GameState {
    private Player player;
    private ArrayList<Enemy> enemies;
    private GameTimer gameTimer;
    private Pregame pregame;

    //For Saving
    public GameState() {
    }

    public GameState(Player player, ArrayList<Enemy> enemies, GameTimer gameTimer) {
        this.player = player;
        this.enemies = enemies;
        this.gameTimer = gameTimer;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    public void setGameTimer(GameTimer gameTimer) {
        this.gameTimer = gameTimer;
    }

    public Pregame getPregame() {
        return pregame;
    }

    public void setPregame(Pregame pregame) {
        this.pregame = pregame;
    }
}
