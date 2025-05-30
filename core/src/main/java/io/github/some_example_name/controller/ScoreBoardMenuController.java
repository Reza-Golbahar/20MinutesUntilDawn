package io.github.some_example_name.controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.some_example_name.model.User;
import io.github.some_example_name.model.UserRepository;
import io.github.some_example_name.view.ScoreBoardMenu;

import java.util.*;
import java.util.stream.Collectors;

public class ScoreBoardMenuController {
    private ScoreBoardMenu view;
    private final List<User> allUsers;
    private String currentSort = "score"; // Default sort

    public ScoreBoardMenuController() {
        this.allUsers = UserRepository.getAllUsers();
    }

    public void setView(ScoreBoardMenu scoreBoardMenu) {
        this.view = scoreBoardMenu;
    }

    public List<User> getSortedUsers() {
        Comparator<User> comparator;

        switch (currentSort) {
            case "username":
                comparator = Comparator.comparing(User::getUsername, String.CASE_INSENSITIVE_ORDER);
                break;
            case "kills":
                comparator = Comparator.comparingInt(User::getKillCount);
                comparator = comparator.reversed();
                break;
            case "survivalTime":
                comparator = Comparator.comparingDouble(User::getSurvivalTime);
                comparator = comparator.reversed();
                break;
            case "score":
            default:
                comparator = Comparator.comparingDouble(User::getScore);
                comparator = comparator.reversed();
                break;
        }

        return allUsers.stream()
            .sorted(comparator)
            .limit(10)
            .collect(Collectors.toList());
    }

    public ClickListener getSortListener(final String sortKey) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (sortKey.equals(currentSort)) {
                } else {
                    currentSort = sortKey;
                }

                if (view != null) {
                    view.refreshTable(); // 🔁 refresh table!
                }
            }
        };
    }


    public String getFormattedSurvivalTime(User user) {
        double seconds = user.getSurvivalTime();
        int mins = (int) seconds / 60;
        int secs = (int) seconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }
}
