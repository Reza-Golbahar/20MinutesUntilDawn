package io.github.some_example_name.model;

import io.github.some_example_name.Main;

public class GameTimer {
    private float elapsedTime = 0;

    public void update(float delta) {
        elapsedTime += delta;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public boolean checkIsTimeOver() {
        return elapsedTime >= Main.getPregame().getDuration();
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}

