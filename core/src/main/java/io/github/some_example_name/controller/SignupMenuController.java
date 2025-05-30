package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.User;
import io.github.some_example_name.model.UserRepository;
import io.github.some_example_name.view.LoginMenu;
import io.github.some_example_name.view.MainMenu;
import io.github.some_example_name.view.SignupMenu;

public class SignupMenuController {
    private SignupMenu view;

    public void handleRegisterButton() {
        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();
        if (view != null) {
            if (UserRepository.isUsernameTaken(username)) {
                view.getErrorLabel().setText("Username already taken.");
                return;
            } else if (!isStrongPassword(password)) {
                view.getErrorLabel().setText("Weak password. Must be at least 8 characters, \ninclude a number, a capital letter, \nand a special character.");
                return;
            } else if (view.getSecurityQuestionField().getText().isBlank()) {
                view.getErrorLabel().setText("You can not leave Security Question Field Blank.");
                return;
            } else if (view.getSecurityAnswerField().getText().isBlank()) {
                view.getErrorLabel().setText("You can not leave Security Answer Field Blank.");
                return;
            }

            User user = new User(username, password);
            user.setSecurityQuestion(view.getSecurityQuestionField().getText());
            user.setSecurityAnswer(view.getSecurityAnswerField().getText());

            UserRepository.addUser(user);
            view.getErrorLabel().setText("User registered Successfully");
            view.getUsernameField().setText("");
            view.getPasswordField().setText("");
            view.getSecurityQuestionField().setText("");
            view.getSecurityAnswerField().setText("");
            UserRepository.saveUsers();
        }
    }

    public void handlePlayAsGuestButton() {
        if (view != null) {
            User guestUser = new User("Guest", "");
            guestUser.setGuest(true);

            Main.setCurrentUser(guestUser);
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
        }
    }

    public void handleGoToLoginMenuButton() {
        if (view != null) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
        }
    }

    public static  boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
            password.matches(".*[A-Z].*") &&
            password.matches(".*[0-9].*") &&
            password.matches(".*[!@_()*&%$#].*");
    }

    public void setView(SignupMenu signUpMenuView) {
        this.view = signUpMenuView;
    }
}
