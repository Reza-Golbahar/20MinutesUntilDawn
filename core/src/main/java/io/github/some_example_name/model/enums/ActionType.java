package io.github.some_example_name.model.enums;

public enum ActionType {
    MoveUp(false, "", "Move Up"),
    MoveDown(false, "", "Move Down"),
    MoveLeft(false, "", "Move Left"),
    MoveRight(false, "", "Move Right"),
    Shoot(false, "", "Shoot"),
    Dash(false, "", "Dash"),
    Interact(false, "", "Interact"),
    Reload(false, "", "Reload"),
    Pause(false, "", "Pause"),
    ToggleAutoAim(false, "", "Toggle Auto Aim"),

    //Cheat codes
    FastForward1MinuteCheatCode(true, "Fast forwards 1 minute of the game", "Fast Forward 1 Minute Cheat Code"),
    LevelUpCheatCode(true, "Increments the level of the player.", "Leve Up Cheat Code"),
    IncreaseHPCheatCode(true, "Increments player's HP by 1", "Increase HP Cheat Code"),
    BossFightCheatCode(true, "Summons the Boss", "Boss Fight Cheat Code"),
    TripleProjectionCountCheatCode(true, "Triples the projections count. Lasts for 10 seconds.", "Triple Projections Cheat Code");

    private boolean isCheatCode;
    private String cheatCodeDescription;
    private String displayName;

    ActionType(boolean isCheatCode, String cheatCodeDescription, String displayName) {
        this.isCheatCode = isCheatCode;
        this.cheatCodeDescription = cheatCodeDescription;
        this.displayName = displayName;
    }

    public boolean isCheatCode() {
        return isCheatCode;
    }

    public String getCheatCodeDescription() {
        return cheatCodeDescription;
    }

    public String getDisplayName() {
        return displayName;
    }
}
