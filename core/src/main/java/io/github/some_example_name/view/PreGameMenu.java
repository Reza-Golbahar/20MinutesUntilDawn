package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.controller.PreGameMenuController;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.enums.HeroType;
import io.github.some_example_name.model.enums.UIText;
import io.github.some_example_name.model.enums.WeaponType;

public class PreGameMenu implements Screen {
    private Stage stage;
    private final PreGameMenuController controller;

    private final Label titleLabel;
    private final Label selectedHeroLabel;
    private final ScrollPane heroSelectionPane;
    private final Label selectedWeaponLabel;
    private final ScrollPane weaponSelectionPane;
    private final SelectBox<Float> gameDurationBox;
    private final TextButton startGame;

    private final Table mainTable;

    public PreGameMenu(PreGameMenuController controller, Skin skin) {
        this.controller = controller;

        this.titleLabel = new Label(UIText.PRE_GAME_MENU_TITLE.get(), skin);
        this.selectedHeroLabel = new Label(UIText.SELECTED_HERO.get(), skin);

        // Dummy hero and weapon selectors
        Table heroTable = new Table();
        for (HeroType heroType : HeroType.values()) {
            TextButton hero = new TextButton(heroType.name(), skin);
            hero.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.selectHero(heroType);
                }
            });
            heroTable.add(hero).pad(5).row();
        }
        heroSelectionPane = new ScrollPane(heroTable, skin);
        heroSelectionPane.setScrollingDisabled(true, false);
        this.selectedWeaponLabel = new Label(UIText.SELECTED_WEAPON.get(), skin);

        Table weaponTable = new Table();
        for (WeaponType weaponType : WeaponType.values()) {
            TextButton weapon = new TextButton(weaponType.name(), skin);
            weapon.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.selectWeapon(weaponType);
                }
            });
            weaponTable.add(weapon).pad(5).row();
        }
        weaponSelectionPane = new ScrollPane(weaponTable, skin);
        weaponSelectionPane.setScrollingDisabled(true, false);

        gameDurationBox = new SelectBox<>(skin);
        gameDurationBox.setItems(2.5f, 5f, 10f, 20f);
        gameDurationBox.setSelected(10f); // default
        gameDurationBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.selectDuration(gameDurationBox.getSelected());
            }
        });

        startGame = new TextButton(UIText.START_GAME.get(), skin);
        startGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.startGame();
            }
        });

        this.mainTable = new Table();
        this.controller.setView(this);
    }

    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        // Layout
        mainTable.setFillParent(true);
        mainTable.pad(20);
        mainTable.defaults().pad(10);

        mainTable.add(titleLabel).colspan(2).center().row();

        mainTable.add(new Label(UIText.SELECT_HERO.get(), skin)).left();
        mainTable.add(heroSelectionPane).height(150).width(200).row();
        mainTable.add(selectedHeroLabel).height(50).width(200).row();

        mainTable.add(new Label(UIText.SELECT_WEAPON.get(), skin)).left();
        mainTable.add(weaponSelectionPane).height(150).width(200).row();
        mainTable.add(selectedWeaponLabel).height(50).width(200).row();

        mainTable.add(new Label(UIText.GAME_DURATION.get(), skin)).left();
        mainTable.add(gameDurationBox).left().row();

        mainTable.add(startGame).colspan(2).center().padTop(20);

        stage.addActor(mainTable);
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

    public Label getSelectedHeroLabel() {
        return selectedHeroLabel;
    }

    public Label getSelectedWeaponLabel() {
        return selectedWeaponLabel;
    }
}
