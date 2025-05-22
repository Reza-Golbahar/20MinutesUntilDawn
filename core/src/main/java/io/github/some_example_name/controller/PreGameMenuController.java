package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.Pregame;
import io.github.some_example_name.model.enums.HeroType;
import io.github.some_example_name.model.enums.WeaponType;
import io.github.some_example_name.view.GameView;
import io.github.some_example_name.view.PreGameMenu;

public class PreGameMenuController {
    private PreGameMenu view;

    public void setView(PreGameMenu view) {
        this.view = view;
        Main.setPregame(new Pregame());
    }


    public void selectHero(HeroType heroType) {
        Main.getPregame().setHeroType(heroType);
        view.getSelectedHeroLabel().setText("Selected Hero: " + heroType.name());
    }

    public void selectWeapon(WeaponType weaponType) {
        Main.getPregame().setWeaponType(weaponType);
        view.getSelectedWeaponLabel().setText("Selected Weapon: " + weaponType.name());
    }

    public void selectDuration(float duration) {
        Main.getPregame().setDuration(duration * 60);
    }

    public void startGame() {
        if (Main.getPregame().getHeroType() == null) {
            view.getSelectedHeroLabel().setText("Select your Hero first");
            return;
        }
        if (Main.getPregame().getWeaponType() == null) {
            view.getSelectedWeaponLabel().setText("Select your weapon first");
            return;
        }
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new GameView(new GameController(), GameAssetManager.getGameAssetManager().getSkin()));
    }
}
