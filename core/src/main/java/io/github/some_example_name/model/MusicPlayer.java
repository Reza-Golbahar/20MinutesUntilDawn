package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer {
    private Music music;

    public void play(String filePath) {
        try {
            if (music != null) {
                music.stop();
                music.dispose();
            }
            music = Gdx.audio.newMusic(Gdx.files.internal(filePath));
            music.setLooping(true);
            music.play();
        } catch (Exception e) {
            Gdx.app.error("MusicPlayer", "Failed to play music: " + filePath, e);
        }
    }

    public void stop() {
        if (music != null) {
            music.stop();
            music.dispose();
            music = null;
        }
    }

    public void setVolume(float volume) {
        if (music != null)
            music.setVolume(volume);
    }
}
