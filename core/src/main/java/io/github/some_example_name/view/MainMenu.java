package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.MainMenuController;

public class MainMenu implements Screen {
    private Stage stage;
    private final MainMenuController controller;
    private final Label titleLabel;

    private final TextButton settingsButton;
    private final TextButton profileButton;
    private final TextButton goToPreGameMenuButton;
    private final TextButton scoreboardButton;
    private final TextButton goToHintMenuButton;
    private final TextButton loadLastSavedGameButton;
    private final TextButton logoutButton;

    private final Image avatarImage;
    private final Label usernameLabel;
    private final Label scoreLabel;

    private final Table table;

    public MainMenu(MainMenuController mainMenuController, Skin skin) {
        this.controller = mainMenuController;

        this.titleLabel = new Label("Pre-game Menu", skin);
        this.settingsButton = new TextButton("Settings", skin);
        this.profileButton = new TextButton("Profile menu", skin);
        this.goToPreGameMenuButton = new TextButton("Go To Pre-game menu", skin);
        this.scoreboardButton = new TextButton("Scoreboard", skin);
        this.goToHintMenuButton = new TextButton("Go To Hint menu", skin);
        this.loadLastSavedGameButton = new TextButton("Load last saved game", skin);
        this.logoutButton = new TextButton("Logout", skin);

        Texture avatarTexture = new Texture(Gdx.files.internal(Main.getCurrentUser().getAvatarPath()));
        avatarImage = new Image(avatarTexture);
        avatarImage.setScaling(Scaling.fit); // Ensures it scales nicely

        this.usernameLabel = new Label("Username: %s".formatted(Main.getCurrentUser().getUsername()), skin);
        this.scoreLabel = new Label("Score: %s".formatted(Main.getCurrentUser().getScore()), skin);

        this.table = new Table();

        controller.setView(this);

        // Button Listeners (you can move this to a separate method)
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.goToSettings();
            }
        });

        profileButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.goToProfile();
            }
        });

        goToPreGameMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.goToPreGameMenu();
            }
        });

        scoreboardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.goToScoreboard();
            }
        });

        goToHintMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.goToHintMenu();
            }
        });

        loadLastSavedGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.loadLastSavedGame();
            }
        });

        logoutButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.logout();
            }
        });
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        stage.addActor(table);

        // === Left Side Table (Avatar + User Info) ===
        Table leftTable = new Table();
        leftTable.top().left().pad(20);

        leftTable.add(avatarImage).size(500, 500).padBottom(20).row(); // Larger avatar
        leftTable.add(usernameLabel).left().padBottom(10).row();
        leftTable.add(scoreLabel).left().row();

        // === Right Side Table (Buttons) ===
        Table rightTable = new Table();
        rightTable.top().right().pad(20);

        rightTable.add(titleLabel).center().padBottom(20).row();
        rightTable.add(settingsButton).fillX().pad(5).row();
        rightTable.add(profileButton).fillX().pad(5).row();
        rightTable.add(goToPreGameMenuButton).fillX().pad(5).row();
        rightTable.add(scoreboardButton).fillX().pad(5).row();
        rightTable.add(goToHintMenuButton).fillX().pad(5).row();
        rightTable.add(loadLastSavedGameButton).fillX().pad(5).row();
        rightTable.add(logoutButton).fillX().padTop(10).padBottom(10).row();

        // === Combine Both Sides in the Main Table ===
        table.add(leftTable).expand().top().left();
        table.add(rightTable).width(500).top().right();

        // === Button Listeners ===
        settingsButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                controller.goToSettings();
            }
        });

        profileButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                controller.goToProfile();
            }
        });

        goToPreGameMenuButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                controller.goToPreGameMenu();
            }
        });

        scoreboardButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                controller.goToScoreboard();
            }
        });

        goToHintMenuButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                controller.goToHintMenu();
            }
        });

        loadLastSavedGameButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                controller.loadLastSavedGame();
            }
        });

        logoutButton.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                controller.logout();
            }
        });
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
}
