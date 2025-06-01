package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.ProfileMenuController;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.enums.UIText;


public class ProfileMenu implements Screen {
    private Stage stage;
    private ProfileMenuController controller;

    private final Label currentUsernameLabel;
    private final TextField newUsernameField;
    private final Label usernameMessageLabel;
    private final TextButton usernameSubmit;

    private final Label currentPasswordLabel;
    private final TextField newPasswordField;
    private final Label passwordMessageLabel;
    private final TextButton passwordSubmit;

    private final TextButton deleteAccountButton;
    private final Table avatarGrid;
    private final ScrollPane avatarScrollPane;
    private final TextButton fileChooserButton;

    private final TextButton goToMainMenuButton;

    private final Table table;


    public ProfileMenu(ProfileMenuController controller, Skin skin) {
        this.controller = controller;

        this.currentUsernameLabel = new Label("%s: %s".formatted(
            UIText.CURRENT_USERNAME.get(), Main.getCurrentUser().getUsername()), skin);
        this.newUsernameField = new TextField("", skin);
        this.newUsernameField.setMessageText(UIText.CHANGE_USERNAME_PLACEHOLDER.get());
        this.usernameMessageLabel = new Label("", skin);
        this.usernameSubmit = new TextButton(UIText.SUBMIT_USERNAME.get(), skin);

        this.currentPasswordLabel = new Label( "%s: %s".formatted(
            UIText.CURRENT_PASSWORD.get(), Main.getCurrentUser().getPassword()), skin);
        this.newPasswordField = new TextField("", skin);
        this.newPasswordField.setMessageText(UIText.CHANGE_PASSWORD_PLACEHOLDER.get());
        this.passwordMessageLabel = new Label("", skin);
        this.passwordSubmit = new TextButton(UIText.SUBMIT_PASSWORD.get(), skin);

        this.deleteAccountButton = new TextButton(UIText.DELETE_ACCOUNT.get(), skin);

        this.avatarGrid = new Table();
        for (String path : GameAssetManager.getGameAssetManager().getAvatarPaths()) {
            final Texture texture = new Texture(Gdx.files.internal(path));
            Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
            ImageButton button = new ImageButton(drawable);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.setSelectedAvatar(path); // use path, not texture
                }
            });

            avatarGrid.add(button).size(64).pad(5);
        }


        this.avatarScrollPane = new ScrollPane(avatarGrid);

        this.fileChooserButton = new TextButton(UIText.CHOOSE_FILE.get(), skin);
        this.goToMainMenuButton = new TextButton(UIText.GO_TO_MAIN_MENU.get(), skin);

        this.table = new Table();

        controller.setView(this);

        usernameSubmit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.setUsername(newUsernameField.getText());
            }
        });

        passwordSubmit.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
               controller.setPassword(newPasswordField.getText());
           }
        });

        deleteAccountButton.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
               controller.deleteAccount();
           }
        });

        goToMainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToMainMenu();
            }
        });

        fileChooserButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.setAvatarAsUploadedFile();
            }
        });
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Outer table to fill the screen
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        // Inner table with all content
        table.top().padTop(30).padLeft(50).padRight(50);

        // Place all your UI elements into this `table` as before
        table.add(currentUsernameLabel).left().colspan(2).padBottom(5).row();
        table.add(newUsernameField).fillX().colspan(2).padBottom(5).row();
        table.add(usernameSubmit).left().colspan(2).padBottom(5).row();
        table.add(usernameMessageLabel).left().colspan(2).padBottom(20).row();

        table.add(currentPasswordLabel).left().colspan(2).padBottom(5).row();
        table.add(newPasswordField).fillX().colspan(2).padBottom(5).row();
        table.add(passwordSubmit).left().colspan(2).padBottom(5).row();
        table.add(passwordMessageLabel).left().colspan(2).padBottom(20).row();

        avatarScrollPane.setFadeScrollBars(false);
        avatarScrollPane.setScrollingDisabled(false, true); // horizontal scrolling
        table.add(avatarScrollPane).width(400).height(80).center().colspan(2).padBottom(10).row();

        table.add(fileChooserButton).left().colspan(2).padBottom(30).row();

        table.add(deleteAccountButton).center().colspan(2).padBottom(10).row();
        table.add(goToMainMenuButton).center().colspan(2).padBottom(10).row();

        // Wrap table in a scroll pane
        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false); // vertical scrolling only

        // Add scrollPane to the root
        root.add(scrollPane).expand().fill();
    }


    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }


    public TextField getNewUsernameField() {
        return newUsernameField;
    }

    public TextField getNewPasswordField() {
        return newPasswordField;
    }

    public Label getPasswordMessageLabel() {
        return passwordMessageLabel;
    }

    public Label getUsernameMessageLabel() {
        return usernameMessageLabel;
    }
}
