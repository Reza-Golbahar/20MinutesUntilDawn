package io.github.some_example_name.model;

import com.badlogic.gdx.Input;
import io.github.some_example_name.model.enums.ActionType;

import java.util.HashMap;
import java.util.Map;

public class ControlsMapping {
    private static ControlsMapping instance;

    private final Map<ActionType, Integer> keyMap = new HashMap<>();

    public ControlsMapping() {
        setDefaults();
    }

    public static ControlsMapping getInstance() {
        if (instance == null) {
            instance = new ControlsMapping();
        }
        return instance;
    }

    public void setDefaults() {
        keyMap.put(ActionType.MoveUp, Input.Keys.W);
        keyMap.put(ActionType.MoveDown, Input.Keys.S);
        keyMap.put(ActionType.MoveLeft, Input.Keys.A);
        keyMap.put(ActionType.MoveRight, Input.Keys.D);
        keyMap.put(ActionType.Shoot, Input.Keys.SPACE);
        keyMap.put(ActionType.Dash, Input.Keys.SHIFT_LEFT);
        keyMap.put(ActionType.Interact, Input.Keys.E);
        keyMap.put(ActionType.Reload, Input.Keys.R);
    }

    public void setKey(ActionType actionType, int keycode) {
        keyMap.put(actionType, keycode);
    }

    public int getKey(ActionType actionType) {
        return keyMap.getOrDefault(actionType, Input.Keys.UNKNOWN);
    }

    public Map<ActionType, Integer> getAllMappings() {
        return keyMap;
    }
}
