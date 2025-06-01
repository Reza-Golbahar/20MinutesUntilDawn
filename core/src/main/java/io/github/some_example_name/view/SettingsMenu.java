package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.SettingsMenuController;
import io.github.some_example_name.model.ControlsMapping;
import com.badlogic.gdx.Input;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.enums.ActionType;
import io.github.some_example_name.model.enums.Language;
import io.github.some_example_name.model.enums.UIText;

import java.util.Map;

public class SettingsMenu implements Screen {
    private Stage stage;
    private final SettingsMenuController controller;

    private final Label titleLabel;

    private final Label musicVolumeLabel;
    private final Slider musicVolumeSlider;

    private final Label selectSongLabel;
    private final SelectBox<String> songSelectBox;

    private final CheckBox sfxToggle;
    private final CheckBox autoReloadToggle;
    private final CheckBox blackWhiteDisplayToggle;
    private final TextButton goToMainMenuButton;

    private final Table leftTable;
    private final Table controlTable;
    private final Label languageLabel;
    private final SelectBox<Language> languageSelectBox;


    private final Table mainTable;

    public SettingsMenu(SettingsMenuController controller, Skin skin) {
        if (skin == null) {
            throw new IllegalArgumentException("Skin must not be null");
        }
        this.controller = controller;

        titleLabel = new Label(UIText.SETTINGS_TITLE.get(), skin);

        musicVolumeLabel = new Label(UIText.MUSIC_VOLUME.get(), skin);
        this.musicVolumeSlider = new Slider(0f, 1f, 0.01f, false, skin);

        selectSongLabel = new Label(UIText.SELECT_SONG.get(), skin);
        this.songSelectBox = new SelectBox<>(skin);
        this.songSelectBox.setItems("Hello-Adele", "Venom-Eminem", "Chandelier-Sia");

        sfxToggle = new CheckBox(UIText.SFX_ACTIVATION.get(), skin);
        autoReloadToggle = new CheckBox(UIText.AUTO_RELOAD_ACTIVATION.get(), skin);
        blackWhiteDisplayToggle = new CheckBox(UIText.BLACK_WHITE_ENVIRONMENT.get(), skin);
        goToMainMenuButton = new TextButton(UIText.GO_TO_MAIN_MENU.get(), skin);

        this.leftTable = new Table(skin);
        this.controlTable = new Table(skin);
        this.mainTable = new Table(skin);

        this.languageLabel = new Label("Select Language: ", skin);
        this.languageSelectBox = new SelectBox<>(skin);
        this.languageSelectBox.setItems(Language.values());
        this.languageSelectBox.setSelected(Main.getCurrentUser().getCurrentLanguage());

        languageSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.changeLanguage(languageSelectBox.getSelected());
            }
        });


        // Listeners
        musicVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.setMusicVolume(musicVolumeSlider.getValue());
            }
        });


        songSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                controller.changeMusic(songSelectBox.getSelected());
            }
        });

        sfxToggle.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                controller.toggleSFX(sfxToggle.isChecked());
            }
        });

        autoReloadToggle.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                controller.setAutoReload(autoReloadToggle.isChecked());
            }
        });

        blackWhiteDisplayToggle.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                controller.setBlackWhiteDisplay(blackWhiteDisplayToggle.isChecked());
            }
        });

        goToMainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToMainMenu();
            }
        });
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        leftTable.add(titleLabel).colspan(2).pad(20).center().row();

        leftTable.add(musicVolumeLabel).left().pad(10);
        leftTable.add(musicVolumeSlider).fillX().pad(10).row();

        leftTable.add(selectSongLabel).left().pad(10);
        leftTable.add(songSelectBox).fillX().pad(10).row();
        leftTable.add(sfxToggle).colspan(2).left().pad(10).row();

        leftTable.add(autoReloadToggle).colspan(2).left().pad(10).row();
        leftTable.add(blackWhiteDisplayToggle).colspan(2).left().pad(10).row();
        leftTable.add(languageLabel).left().pad(10);
        leftTable.add(languageSelectBox).fillX().pad(10).row();
        leftTable.add(goToMainMenuButton).colspan(2).fillX().pad(10).row();


        ControlsMapping controlsMapping = ControlsMapping.getInstance();

        Map<ActionType, Integer> defaultMap = controlsMapping.getAllMappings();
        controlTable.defaults().pad(5);

        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        for (Map.Entry<ActionType, Integer> entry : defaultMap.entrySet()) {
            ActionType action = entry.getKey();
            int defaultKey = entry.getValue();

            Label actionLabel = new Label(action.name() + ": ", skin);
            TextField keyField = new TextField(Input.Keys.toString(defaultKey), skin);
            keyField.setDisabled(true); // prevent manual typing

            keyField.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.input.setInputProcessor(new InputAdapter() {
                        @Override
                        public boolean keyDown(int keycode) {
                            keyField.setText(Input.Keys.toString(keycode));
                            controlsMapping.setKey(action, keycode);
                            Gdx.input.setInputProcessor(stage); // restore normal input
                            return true;
                        }
                    });
                }
            });

            controlTable.add(actionLabel).left();
            controlTable.add(keyField).width(200).row();
        }

        // Layout the two sections side-by-side
        mainTable.setFillParent(true);
        mainTable.add(leftTable).expand().fill().left().pad(20);
        mainTable.add(controlTable).expand().fill().right().pad(20);

        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
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
}
