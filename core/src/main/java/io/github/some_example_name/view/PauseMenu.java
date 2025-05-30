package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.PauseMenuController;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.Player;
import io.github.some_example_name.model.enums.AbilityType;
import io.github.some_example_name.model.enums.ActionType;

public class PauseMenu implements Screen {
    private final PauseMenuController controller;
    private Stage stage;
    private Player player;
    private final Table table;

    private final ScrollPane cheatsPane;
    private final ScrollPane abilitiesObtainedPane;
    private final CheckBox blackWhite;
    private final TextButton saveAndQuit;
    private final TextButton giveUp;
    private final TextButton resumeButton;

    public PauseMenu(PauseMenuController controller, Skin skin, Player player) {
        this.controller = controller;
        this.player = player;
        this.table = new Table();

        // Resume
        resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.returnToGame();
            }
        });

        // Cheats
        Table cheatsTable = new Table();
        for (ActionType type : ActionType.values()) {
            if (type.isCheatCode()) {
                Label cheatLabel = new Label(type.getDisplayName() + "   :   " + type.getCheatCodeDescription(), skin);
                cheatLabel.setAlignment(Align.left);
                cheatsTable.add(cheatLabel).left().expandX().padTop(15).padLeft(10).row();
            }
        }
        cheatsPane = new ScrollPane(cheatsTable, skin);

        // Abilities obtained
        Table abilitiesTable = new Table();
        for (AbilityType ability : player.getAbilitiesObtained()) {
            Label abilityLabel = new Label(ability.getTitle() + "   :   " + ability.getDescription(), skin);
            abilityLabel.setAlignment(Align.left);
            abilitiesTable.add(abilityLabel).left().expandX().padTop(15).padLeft(10).row();
        }
        abilitiesObtainedPane = new ScrollPane(abilitiesTable, skin);

        // Black and white toggle
        blackWhite = new CheckBox("Black And White", skin);
        blackWhite.setChecked(Main.getCurrentUser().isBlackAndWhiteWorld());
        blackWhite.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.toggleBlackWhite(blackWhite.isChecked());
            }
        });

        // Save and Quit
        saveAndQuit = new TextButton("Save And Quit", skin);
        saveAndQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.saveAndQuit();
            }
        });

        // Give Up
        giveUp = new TextButton("Give Up", skin);
        giveUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.giveUp();
            }
        });

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.table.setFillParent(true);

        // Layout
        table.add(resumeButton).pad(10).row();
        table.add(new Label("Cheat Codes:", GameAssetManager.getGameAssetManager().getSkin())).padTop(10).row();
        table.add(cheatsPane).height(200).width(600).row();
        table.add(new Label("Obtained Abilities:", GameAssetManager.getGameAssetManager().getSkin())).padTop(10).row();
        table.add(abilitiesObtainedPane).height(200).width(600).row();
        table.add(blackWhite).padTop(10).row();
        table.add(saveAndQuit).padTop(10).row();
        table.add(giveUp).padTop(10).row();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
}
