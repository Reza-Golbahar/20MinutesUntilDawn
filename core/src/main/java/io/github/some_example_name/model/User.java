package io.github.some_example_name.model;

import com.badlogic.gdx.math.MathUtils;

public class User {
    private String username;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private String avatarPath; //if an image is saved, Texture or Image
    private int score = 0;
    boolean isGuest = false;



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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
