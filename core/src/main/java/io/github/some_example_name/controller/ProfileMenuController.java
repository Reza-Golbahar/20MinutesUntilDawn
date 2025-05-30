package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.User;
import io.github.some_example_name.model.UserRepository;
import io.github.some_example_name.view.LoginMenu;
import io.github.some_example_name.view.MainMenu;
import io.github.some_example_name.view.ProfileMenu;

import javax.swing.*;
import java.io.File;

public class ProfileMenuController {
    private ProfileMenu view;

    public void setView(ProfileMenu profileMenu) {
        this.view = profileMenu;
    }

    public void setSelectedAvatar(String avatarPath) {
        Main.getCurrentUser().setAvatarPath(avatarPath);
        UserRepository.saveUsers();
    }


    public void setUsername(String newUsername) {
        if (newUsername.equalsIgnoreCase(Main.getCurrentUser().getUsername())) {
            view.getUsernameMessageLabel().setText("You must enter a new username.");
            return;
        }
        User user = UserRepository.findUserByUsername(newUsername);
        if (user != null) {
            view.getUsernameMessageLabel().setText("Username already taken.");
            return;
        }
        Main.getCurrentUser().setUsername(newUsername);
        view.getUsernameMessageLabel().setText("Username successfully changed.");
        UserRepository.saveUsers();
    }

    public void setPassword(String newPassword) {
        if (!SignupMenuController.isStrongPassword(newPassword)) {
            view.getPasswordMessageLabel().setText("Password not strong");
            return;
        }
        Main.getCurrentUser().setPassword(newPassword);
        view.getPasswordMessageLabel().setText("Password successfully changed.");
    }

    public void deleteAccount() {
        UserRepository.deleteUser(Main.getCurrentUser());
        UserRepository.saveUsers();

        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void goToMainMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void setAvatarAsUploadedFile() {
        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                Gdx.app.postRunnable(() -> {
                    Main.getCurrentUser().setAvatarPath(file.getAbsolutePath());
                    view.getUsernameMessageLabel().setText("Custom avatar selected.");
                    UserRepository.saveUsers();
                });
            }
        });
    }
}
