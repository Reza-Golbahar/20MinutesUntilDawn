package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.ScoreBoardMenuController;
import io.github.some_example_name.model.User;

import java.util.Comparator;
import java.util.List;

public class ScoreBoardMenu implements Screen {
    private Stage stage;
    private final ScoreBoardMenuController controller;
    private final Skin skin;
    private final User currentUser;
    private final TextButton sortByUsernameButton;
    private final TextButton sortByScoreButton;
    private final TextButton sortByKillsButton;
    private final TextButton sortBySurvivalTimeButton;

    private final ScrollPane scrollPane;;
    private final Label title;
    private final Table table;

    private final Table root;

    public ScoreBoardMenu(ScoreBoardMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        this.currentUser = Main.getCurrentUser();

        this.title = new Label("Top 10 Players", skin, "title");

        this.table = new Table(skin);

        scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollbarsOnTop(true);
        scrollPane.setScrollingDisabled(true, false);

        this.sortByUsernameButton = new TextButton("Username", skin);
        this.sortByKillsButton = new TextButton("Score", skin);
        this.sortByScoreButton = new TextButton("Kills", skin);
        this.sortBySurvivalTimeButton = new TextButton("Survival Time", skin);

        this.root = new Table();
        controller.setView(this);

        this.sortByUsernameButton.addListener(controller.getSortListener("username"));
        this.sortByScoreButton.addListener(controller.getSortListener("score"));
        this.sortByKillsButton.addListener(controller.getSortListener("kills"));
        this.sortBySurvivalTimeButton.addListener(controller.getSortListener("survivalTime"));
    }

    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        root.setFillParent(true);
        root.top().pad(20);
        title.setAlignment(Align.center);
        root.add(title).colspan(5).center().padBottom(20).row();

        // Table for scoreboard content
        table.defaults().pad(5);

        table.add(new Label("Rank", skin)).padRight(20);
        table.add(sortByUsernameButton);
        table.add(sortByScoreButton);
        table.add(sortByKillsButton);
        table.add(sortBySurvivalTimeButton);

        List<User> topUsers = controller.getSortedUsers(); // returns sorted top 10 users
        for (int i = 0; i < topUsers.size(); i++) {
            User user = topUsers.get(i);
            Label rank = new Label(String.valueOf(i + 1), skin);
            Label username = new Label(user.getUsername(), skin);
            Label score = new Label(String.valueOf((int) user.getScore()), skin);
            Label kills = new Label(String.valueOf(user.getKillCount()), skin);
            Label time = new Label(controller.getFormattedSurvivalTime(user), skin);

            // Highlight top 3 and current user
            if (i < 3) {
                rank.setColor(Color.GOLD);
                username.setColor(Color.GOLD);
                score.setColor(Color.GOLD);
                kills.setColor(Color.GOLD);
                time.setColor(Color.GOLD);
            }
            if (user.getUsername().equalsIgnoreCase(currentUser.getUsername())) {
                username.setColor(Color.CYAN);
                score.setColor(Color.CYAN);
                kills.setColor(Color.CYAN);
                time.setColor(Color.CYAN);
            }

            table.add(rank);
            table.add(username);
            table.add(score);
            table.add(kills);
            table.add(time).row();
        }

        root.row();
        root.add(scrollPane).expand().fill().colspan(5);

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

    public Stage getStage() {
        return stage;
    }

    public ScoreBoardMenuController getController() {
        return controller;
    }

    public Skin getSkin() {
        return skin;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public TextButton getSortByUsernameButton() {
        return sortByUsernameButton;
    }

    public TextButton getSortByScoreButton() {
        return sortByScoreButton;
    }

    public TextButton getSortByKillsButton() {
        return sortByKillsButton;
    }

    public TextButton getSortBySurvivalTimeButton() {
        return sortBySurvivalTimeButton;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public Label getTitle() {
        return title;
    }

    public Table getTable() {
        return table;
    }

    public Table getRoot() {
        return root;
    }

    public void refreshTable() {
        table.clear();

        table.add(new Label("Rank", skin)).padRight(20);
        table.add(sortByUsernameButton);
        table.add(sortByScoreButton);
        table.add(sortByKillsButton);
        table.add(sortBySurvivalTimeButton).row();

        List<User> topUsers = controller.getSortedUsers();

        for (int i = 0; i < topUsers.size(); i++) {
            User user = topUsers.get(i);
            Label rank = new Label(String.valueOf(i + 1), skin);
            Label username = new Label(user.getUsername(), skin);
            Label score = new Label(String.valueOf((int) user.getScore()), skin);
            Label kills = new Label(String.valueOf(user.getKillCount()), skin);
            Label time = new Label(controller.getFormattedSurvivalTime(user), skin);

            if (i < 3) {
                rank.setColor(Color.GOLD);
                username.setColor(Color.GOLD);
                score.setColor(Color.GOLD);
                kills.setColor(Color.GOLD);
                time.setColor(Color.GOLD);
            }
            if (user.getUsername().equalsIgnoreCase(currentUser.getUsername())) {
                username.setColor(Color.CYAN);
                score.setColor(Color.CYAN);
                kills.setColor(Color.CYAN);
                time.setColor(Color.CYAN);
            }

            table.add(rank);
            table.add(username);
            table.add(score);
            table.add(kills);
            table.add(time).row();
        }
    }

}
