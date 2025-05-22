package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.controller.ForgetPasswordController;

public class ForgetPassword implements Screen {
    private Stage stage;
    private final ForgetPasswordController controller;
    private final Label titleLabel;

    private final TextField usernameField;
    private final Label securityQuestionLabel;

    private final TextField securityAnswerField;
    private final Label messageLabel;
    private final Label passwordLabel;

    private final TextButton usernameSubmitButton;
    private final TextButton securityAnswerSubmitButton;
    private final TextButton goBackToLoginMenu;

    private final Table table;

    public ForgetPassword(ForgetPasswordController controller, Skin skin) {
        this.controller = controller;

        this.titleLabel = new Label("Forget Password Menu", skin);
        this.usernameField = new TextField("", skin);
        this.usernameField.setMessageText("Username");

        this.securityQuestionLabel = new Label("", skin);
        this.securityAnswerField = new TextField("", skin);
        this.securityAnswerField.setMessageText("Security Answer");

        this.messageLabel = new Label("", skin);
        this.passwordLabel = new Label("", skin);

        this.usernameSubmitButton = new TextButton("Submit Username", skin);
        this.securityAnswerSubmitButton = new TextButton("Submit Answer", skin);
        this.goBackToLoginMenu = new TextButton("Go Back To Login Menu", skin);

        this.table = new Table();

        controller.setView(this);

        usernameSubmitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSubmitUsername(usernameField.getText());
            }
        });

        securityAnswerSubmitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSubmitSecurityAnswer();
            }
        });

        goBackToLoginMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleGoBackToLoginMenu();
            }
        });
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center();

        // Add title
        table.add(titleLabel).colspan(2).padBottom(20).row();

        // Username entry row
        table.add(new Label("Username:", titleLabel.getStyle())).left().padBottom(10);
        table.add(usernameField).width(200).padBottom(10).row();

        // Submit username button row
        table.add(usernameSubmitButton).colspan(2).padBottom(15).center().row();

        // Security question display
        table.add(new Label("Security Question:", titleLabel.getStyle())).left().padBottom(10);
        table.add(securityQuestionLabel).left().padBottom(10).row();

        // Security answer entry row
        table.add(new Label("Answer:", titleLabel.getStyle())).left().padBottom(10);
        table.add(securityAnswerField).width(200).padBottom(10).row();

        // Submit answer button
        table.add(securityAnswerSubmitButton).colspan(2).padBottom(15).center().row();

        // Error message (e.g. incorrect answer)
        table.add(messageLabel).colspan(2).center().padBottom(10).row();

        // Recovered password display
        table.add(new Label("", titleLabel.getStyle())).left().padTop(20);
        table.add(passwordLabel).left().padTop(20).row();
        table.add(goBackToLoginMenu).colspan(2).padBottom(15).center().row();

        // Add the table to stage
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

    public TextField getUsernameField() {
        return usernameField;
    }

    public Label getSecurityQuestionLabel() {
        return securityQuestionLabel;
    }

    public TextField getSecurityAnswerField() {
        return securityAnswerField;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    public Label getPasswordLabel() {
        return passwordLabel;
    }

    public TextButton getUsernameSubmitButton() {
        return usernameSubmitButton;
    }

    public TextButton getSecurityAnswerSubmitButton() {
        return securityAnswerSubmitButton;
    }
}
