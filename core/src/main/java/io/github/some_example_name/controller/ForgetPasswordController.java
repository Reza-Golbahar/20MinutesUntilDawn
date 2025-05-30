package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.User;
import io.github.some_example_name.model.UserRepository;
import io.github.some_example_name.view.ForgetPassword;
import io.github.some_example_name.view.LoginMenu;

import java.util.Random;

public class ForgetPasswordController {
    private ForgetPassword view;
    private User user;

    public void setView(ForgetPassword forgetPassword) {
        this.view = forgetPassword;
    }

    public void handleSubmitUsername(String username) {
        if (view != null) {
            user = UserRepository.findUserByUsername(username);
            if (user == null) {
                view.getMessageLabel().setText("No User with username %s found.".formatted(username));
                return;
            }
            view.getSecurityQuestionLabel().setText(user.getSecurityQuestion());
        }
    }

    public void handleSubmitSecurityAnswer() {
        if (user == null) {
            view.getMessageLabel().setText("Submit your username First.");
            return;
        }
        String userResponse = view.getSecurityAnswerField().getText();
        if (!userResponse.equals(user.getSecurityAnswer())) {
            view.getMessageLabel().setText("Wrong Security Answer");
            return;
        }
        // Generate a strong password
        String newPassword = generateStrongPassword();
        user.setPassword(newPassword);
        view.getMessageLabel().setText("Your password has been reset.");
        view.getPasswordLabel().setText(newPassword); // You could also remove this to be more secure
        UserRepository.saveUsers();
    }

    private String generateStrongPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialCharacters = "!@#$%&*";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        Random random = new Random();
        sb.append(numbers.charAt(random.nextInt(numbers.length())));
        sb.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));
        return sb.toString();
    }


    public void handleGoBackToLoginMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }
}
