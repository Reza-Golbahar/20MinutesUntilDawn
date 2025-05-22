package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.controller.TalentMenuController;
import io.github.some_example_name.model.ControlsMapping;
import io.github.some_example_name.model.enums.ActionType;

import java.util.Map;

public class TalentMenu implements Screen {

    private Stage stage;
    private Skin skin;
    private final TalentMenuController controller;
    private final ControlsMapping controlsMapping = new ControlsMapping();

    public TalentMenu(TalentMenuController controller, Skin skin) {
        this.skin = skin;
        this.controller = controller;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);
        root.pad(20);
        root.defaults().pad(10);

        Table heroHelpTable = new Table();
        heroHelpTable.defaults().pad(5);
        heroHelpTable.add(new Label("Hero Help", skin)).colspan(2).center().row();

        addHeroHelp(heroHelpTable, "Hero A", "Fast and agile.");
        addHeroHelp(heroHelpTable, "Hero B", "Strong and tanky.");
        addHeroHelp(heroHelpTable, "Hero C", "Has healing ability.");

        Table keyHelpTable = new Table();
        keyHelpTable.defaults().pad(5);
        keyHelpTable.add(new Label("Current Game Controls", skin)).colspan(2).center().row();

        for (Map.Entry<ActionType, Integer> entry : controlsMapping.getAllMappings().entrySet()) {
            ActionType action = entry.getKey();
            int keycode = entry.getValue();
            keyHelpTable.add(new Label(action.name() + ":", skin));
            keyHelpTable.add(new Label(com.badlogic.gdx.Input.Keys.toString(keycode), skin)).row();
        }

        Table main = new Table();
        main.setFillParent(true);
        main.add(heroHelpTable).top().expand().left().padRight(50);
        main.add(keyHelpTable).top().expand().right();

        stage.addActor(main);
    }

    private void addHeroHelp(Table table, String name, String description) {
        table.add(new Label(name + ":", skin)).left();
        table.add(new Label(description, skin)).left().row();
    }

    @Override
    public void show() {}

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
