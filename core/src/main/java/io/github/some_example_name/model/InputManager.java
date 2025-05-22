package io.github.some_example_name.model;

import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.Map;

public class InputManager {
    private static final Map<String, Integer> keyBindings = new HashMap<>();

    static {
        keyBindings.put("MOVE_UP", Input.Keys.W);
        keyBindings.put("MOVE_DOWN", Input.Keys.S);
        keyBindings.put("MOVE_LEFT", Input.Keys.A);
        keyBindings.put("MOVE_RIGHT", Input.Keys.D);
        keyBindings.put("SHOOT", Input.Keys.SPACE);
        // Add more actions as needed
    }

    public static int getKey(String action) {
        return keyBindings.getOrDefault(action, Input.Keys.UNKNOWN);
    }

    public static void setKey(String action, int keycode) {
        keyBindings.put(action, keycode);
    }

    public static Map<String, Integer> getKeyBindings() {
        return keyBindings;
    }
}

