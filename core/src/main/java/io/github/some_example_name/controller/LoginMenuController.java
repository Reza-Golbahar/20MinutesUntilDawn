package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.User;
import io.github.some_example_name.model.UserRepository;
import io.github.some_example_name.view.ForgetPassword;
import io.github.some_example_name.view.LoginMenu;
import io.github.some_example_name.view.MainMenu;
import io.github.some_example_name.view.SignupMenu;

public class LoginMenuController {
    private LoginMenu view;

    public void setView(LoginMenu loginMenu) {
        this.view = loginMenu;
    }

    public void handleLogin() {
        if (view != null) {
            String result = login(
                view.getUsernameField().getText(),
                view.getPasswordField().getText()
            );

            view.getErrorLabel().setText(result);
            if (result.equals("Success")) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        }
    }

    private String login(String username, String password) {
        User user = UserRepository.findUserByUsername(username);
        if (user == null) {
            return "No User with username %s has registered before".formatted(username);
        }
        else if (!password.equalsIgnoreCase(user.getPassword())) {
            return "Wrong Password";
        }
        Main.setCurrentUser(user);
        return "Success";
    }

    public void handleForgotPassword(String text) {
        if (view != null) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new ForgetPassword(new ForgetPasswordController(), GameAssetManager.getGameAssetManager().getSkin()));
        }
    }

    public void handleGoBackToSignUpMenu() {
        if (view != null) {
            Main.getMain().dispose();
            Main.getMain().setScreen(new SignupMenu(new SignupMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
        }
    }
}
