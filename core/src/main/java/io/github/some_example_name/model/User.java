package io.github.some_example_name.model;

import com.badlogic.gdx.math.MathUtils;

public class User {
    private String username;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private String avatarPath; //if an image is saved, Texture or Image

    private double score = 0;
    private int killCount = 0;
    private double survivalTime = 0;

    boolean isGuest = false;
    private boolean sfxEnabled = true;
    private boolean blackAndWhiteWorld = false;
    private boolean autoReload = false;
    private boolean autoAim = false;

    public User() {
        //Added for json loading
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        //Setting random Avatar for Player
        String[] avatars = GameAssetManager.getGameAssetManager().getAvatarPaths();
        int index = MathUtils.random(avatars.length - 1);
        this.setAvatarPath(avatars[index]);
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setGuest(boolean guest) {
        if (guest) {
            this.securityQuestion = "Age";
            this.securityAnswer = "18";
        }
        isGuest = guest;
    }

    public void setSecurityQuestion(String text) {
        this.securityQuestion = text;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSfxEnabled() {
        return sfxEnabled;
    }

    public void setSfxEnabled(boolean sfxEnabled) {
        this.sfxEnabled = sfxEnabled;
    }

    public boolean isBlackAndWhiteWorld() {
        return blackAndWhiteWorld;
    }

    public void setBlackAndWhiteWorld(boolean blackAndWhiteWorld) {
        this.blackAndWhiteWorld = blackAndWhiteWorld;
    }

    public boolean isAutoReload() {
        return autoReload;
    }

    public void setAutoReload(boolean autoReload) {
        this.autoReload = autoReload;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public double getSurvivalTime() {
        return survivalTime;
    }

    public void setSurvivalTime(double survivalTime) {
        this.survivalTime = survivalTime;
    }

    public boolean isAutoAim() {
        return autoAim;
    }

    public void setAutoAim(boolean autoAim) {
        this.autoAim = autoAim;
    }
}

