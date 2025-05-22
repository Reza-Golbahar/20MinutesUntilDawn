package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.controller.SignupMenuController;

public class SignupMenu implements Screen {
    private Stage stage;
    private final SignupMenuController controller;
    private final Label titleLabel;

    private final TextField usernameField;
    private final TextField passwordField;
    private final TextField securityAnswerField;
    private final TextField securityQuestionField;

    private final TextButton registerButton;
    private final Label errorLabel;
    private final TextButton guestButton;
    private final TextButton goToLoginMenuButton;
    private final Table table;

    public SignupMenu(SignupMenuController controller, Skin skin) {
        this.controller = controller;

        // UI Elements using the provided skin
        this.titleLabel = new Label("Signup Menu", skin);
        this.usernameField = new TextField("", skin);
        this.usernameField.setMessageText("Username");

        this.passwordField = new TextField("", skin);
        this.passwordField.setMessageText("Password");
        this.passwordField.setPasswordCharacter('*');
        this.passwordField.setPasswordMode(true);

        this.securityQuestionField = new TextField("", skin);
        securityQuestionField.setMessageText("Security Question");
        this.securityAnswerField = new TextField("", skin);
        securityAnswerField.setMessageText("Answer");

        this.registerButton = new TextButton("Sign Up", skin);
        this.errorLabel = new Label("", skin);

        this.guestButton = new TextButton("Continue as Guest", skin);
        this.goToLoginMenuButton = new TextButton("Go to LogIn Menu", skin);

        this.table = new Table();

        controller.setView(this);

        // Add button click handling here!
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleRegisterButton(); // only called when clicked
            }
        });

        guestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handlePlayAsGuestButton();
            }
        });

        goToLoginMenuButton.addListener(new ClickListener() {
            @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.handleGoToLoginMenuButton();
            }
        });

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.table.setFillParent(true);

        // Layout the UI
        table.center();
        table.add(titleLabel).colspan(2).padBottom(20).row();
        table.add(usernameField).width(200).padBottom(10).colspan(2).row();
        table.add(passwordField).width(200).padBottom(10).colspan(2).row();
        table.add(securityQuestionField).width(200).padBottom(10).colspan(2).row();
        table.add(securityAnswerField).width(200).padBottom(10).colspan(2).row();
        table.add(registerButton).padBottom(10).colspan(2).row();
        table.add(errorLabel).colspan(2).row();
        table.add(guestButton).padTop(10).colspan(2).row();
        table.add(goToLoginMenuButton).padTop(10).colspan(2).row();

        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        //Main.getBatch().begin();
        //Main.getBatch().end();
        stage.act(v);
        stage.draw();
        //called this in ClickListener
        //controller.handleSignUpMenuButtons();
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

    public Table getTable() {
        return table;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextButton getRegisterButton() {
        return registerButton;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public TextField getSecurityAnswerField() {
        return securityAnswerField;
    }

    public TextField getSecurityQuestionField() {
        return securityQuestionField;
    }
}
