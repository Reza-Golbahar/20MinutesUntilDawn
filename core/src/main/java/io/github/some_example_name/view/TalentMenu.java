package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.controller.TalentMenuController;
import io.github.some_example_name.model.ControlsMapping;
import io.github.some_example_name.model.enums.AbilityType;
import io.github.some_example_name.model.enums.ActionType;
import io.github.some_example_name.model.enums.HeroType;

import java.util.Map;

public class TalentMenu implements Screen {

    private final Stage stage;
    private final Skin skin;
    private final TalentMenuController controller;
    private final ControlsMapping controlsMapping;
    private final TextButton goToMainMenu;
    private final TextButton loadLastSavedGame;

    private final Table contentTable;

    public TalentMenu(TalentMenuController controller, Skin skin) {
        this.controlsMapping = ControlsMapping.getInstance();

        this.controller = controller;
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // === Master container ===
        this.contentTable = new Table(skin);
        contentTable.defaults().pad(10).left();

        // === Section: Heroes ===
        contentTable.add(new Label("Heroes", skin, "title")).left().row();
        for (HeroType heroType : HeroType.values()) {
            contentTable.add(new Label(heroType.name() + " - HP: " + heroType.getHp() + " | Speed: " + heroType.getSpeed(), skin)).left().row();
        }

        // === Section: Abilities ===
        contentTable.add(new Label("Abilities", skin, "title")).padTop(20).left().row();
        for (AbilityType abilityType : AbilityType.values()) {
            contentTable.add(new Label(abilityType.name() + ": " + abilityType.getDescription(), skin)).left().row();
        }

        // === Section: Cheat Codes ===
        contentTable.add(new Label("Cheat Codes", skin, "title")).padTop(20).left().row();
        for (ActionType actionType : ActionType.values()) {
            if (actionType.isCheatCode()) {
                contentTable.add(new Label(actionType.getDisplayName() + ": " + actionType.getCheatCodeDescription(), skin)).left().row();
            }
        }

        // === Section: Key Bindings ===
        contentTable.add(new Label("Current Game Controls", skin, "title")).padTop(20).left().row();
        for (Map.Entry<ActionType, Integer> entry : controlsMapping.getAllMappings().entrySet()) {
            String keyName = Input.Keys.toString(entry.getValue());
            contentTable.add(new Label(entry.getKey().getDisplayName() + ": " + keyName, skin)).left().row();
        }

        this.goToMainMenu = new TextButton("Go To Main Menu", skin);
        this.loadLastSavedGame = new TextButton("Load Last Saved Game", skin);  // initialize button

        contentTable.add(goToMainMenu);

        goToMainMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToMainMenu();
            }
        });

        loadLastSavedGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.loadSavedGame();
            }
        });
    }

    @Override
    public void show() {
        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);

        Table root = new Table();
        root.setFillParent(true);
        root.pad(20);
        root.add(scrollPane).expand().fill();
        stage.addActor(root);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
