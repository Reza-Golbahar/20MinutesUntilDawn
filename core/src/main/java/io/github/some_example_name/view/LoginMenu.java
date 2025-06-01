package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.controller.LoginMenuController;
import io.github.some_example_name.model.enums.UIText;

public class LoginMenu implements Screen {
    private Stage stage;
    private final LoginMenuController controller;
    private final Label titleLabel;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextButton loginButton;

    private final Label errorLabel;

    private final TextButton forgetPasswordButton;
    private final TextButton goBackToSignupMenuButton;
    private final Table table;

    public LoginMenu(LoginMenuController controller, Skin skin) {
        this.controller = controller;

        this.titleLabel = new Label(UIText.LOGIN_MENU_TITLE.get(), skin);
        this.usernameField = new TextField("", skin);
        this.usernameField.setMessageText(UIText.USERNAME.get());

        this.passwordField = new TextField("", skin);
        this.passwordField.setMessageText(UIText.PASSWORD.get());
        this.passwordField.setPasswordCharacter('*');
        this.passwordField.setPasswordMode(true);

        loginButton = new TextButton(UIText.LOGIN.get(), skin);
        this.errorLabel = new Label("", skin);
        forgetPasswordButton = new TextButton(UIText.FORGET_PASSWORD.get(), skin);
        goBackToSignupMenuButton = new TextButton(UIText.GO_BACK_TO_SIGNUP.get(), skin);

        this.table = new Table();

        controller.setView(this);

        // Add listener to login button
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleLogin();
            }
        });

        // Add listener to forget password button
        forgetPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleForgotPassword(usernameField.getText());
            }
        });

        goBackToSignupMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleGoBackToSignUpMenu();
            }
        });

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.table.setFillParent(true);

        table.center();
        // Add UI elements to the table
        table.add(titleLabel).colspan(2).padBottom(20).row();
        table.add(usernameField).width(200).padBottom(10).colspan(2).row();
        table.add(passwordField).width(200).padBottom(10).colspan(2).row();
        table.add(loginButton).width(200).padBottom(10).colspan(2).row();
        table.add(forgetPasswordButton).width(400).padBottom(10).colspan(2).row();
        table.add(goBackToSignupMenuButton).width(400).padBottom(10).colspan(2).row();
        table.add(errorLabel).colspan(2).padTop(10).row();

        stage.addActor(table);
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

    public TextButton getForgetPasswordButton() {
        return forgetPasswordButton;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextField getUsernameField() {
        return usernameField;
    }
}
