package io.github.some_example_name.model;

public class GameTimer {
    private float elapsedTime = 0;

    public void update(float delta) {
        elapsedTime += delta;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }
}

