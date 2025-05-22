package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.some_example_name.model.enums.HeroType;

import java.util.HashMap;
import java.util.Map;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;

    private final Skin skin = new Skin(Gdx.files.internal("skin/star-soldier-ui.json"));

    private final String[] backGroundMusicPaths = {
        "BackgroundMusic/Hello.mp3",
        "BackgroundMusic/Chandelier.mp3",
        "BackgroundMusic/Venom.mp3"
    };

    private final String[] avatarPaths = {
        "Images/Texture2D/T_Abby_Portrait.png",
        "Images/Texture2D/T_Dasher_Portrait.png",
        "Images/Texture2D/T_Diamond_Portrait.png",
        "Images/Texture2D/T_Hastur_Portrait.png",
        "Images/Texture2D/T_Hina_Portrait.png",
        "Images/Texture2D/T_Lilith_Portrait.png",
        "Images/Texture2D/T_Luna_Portrait.png",
        "Images/Texture2D/T_Raven_Portrait.png",
        "Images/Texture2D/T_Scarlett_Portrait.png",
        "Images/Texture2D/T_Shana_Portrait.png",
        "Images/Texture2D/T_Spark_Portrait.png",
        "Images/Texture2D/T_Yuki_Portrait.png"
    };

    private final Map<String, Texture> avatarTextures = new HashMap<>();

    private final String elderBrain = "Images/Elder/ElderBrain.png";

    private final String[] tentacleSpawnFrames = {
        "Images/Tentacle Monster/T_TentacleSpawn_0.png",
        "Images/Tentacle Monster/T_TentacleSpawn_1.png",
        "Images/Tentacle Monster/T_TentacleSpawn_2.png"
    };

    private final String[] tentacleIdleFrames = {
        "Images/Tentacle Monster/T_TentacleIdle_0.png",
        "Images/Tentacle Monster/T_TentacleIdle_1.png",
        "Images/Tentacle Monster/T_TentacleIdle_2.png",
        "Images/Tentacle Monster/T_TentacleIdle_3.png"
    };

    private final String tentacleAttack = "Images/Tentacle Monster/TentacleAttack.png";

    private final String[] eyeBatFrames = {
        "Images/Eyebat/T_EyeBat_0.png",
        "Images/Eyebat/T_EyeBat_1.png",
        "Images/Eyebat/T_EyeBat_2.png",
        "Images/Eyebat/T_EyeBat_3.png"
    };

    private final String eyeBatProjectile = "Images/Eyebat/T_EyeBat_EM.png";

    private final String treeMonsterFrames = "Images/Tree/T_TreeMonster_0.png";

    private GameAssetManager() {
        for (String path : avatarPaths) {
            String key = extractAvatarName(path); // e.g., "Abby"
            avatarTextures.put(key, new Texture(Gdx.files.internal(path)));
        }
    }

    private String extractAvatarName(String path) {
        String filename = path.substring(path.lastIndexOf("/") + 1); // T_Abby_Portrait.png
        return filename.substring(2, filename.indexOf("_Portrait")); // "Abby"
    }

    public static GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null)
            gameAssetManager = new GameAssetManager();
        return gameAssetManager;
    }

    public Skin getSkin() {
        return skin;
    }

    public Texture getTextureForAvatar(String name) {
        return avatarTextures.get(name);
    }

    public String[] getAvatarNames() {
        return avatarTextures.keySet().toArray(new String[0]);
    }

    public void dispose() {
        for (Texture texture : avatarTextures.values()) {
            texture.dispose();
        }
        skin.dispose();
    }

    public String[] getAvatarPaths() {
        return avatarPaths;
    }

    public String[] getBackGroundMusicPaths() {
        return backGroundMusicPaths;
    }

    //needs to be modified
    private final String smg = "Images/Sprite/SMGStill.png";
    private final Texture smgTexture = new Texture(smg);

    private final String bullet = "Images/Sprite/Icon_LightBullet.png";

    private final String[] DasherIdleFrames = {
        "Images/Hero/Dasher/Idle/0.png",
        "Images/Hero/Dasher/Idle/1.png",
        "Images/Hero/Dasher/Idle/2.png",
        "Images/Hero/Dasher/Idle/3.png",
        "Images/Hero/Dasher/Idle/4.png",
        "Images/Hero/Dasher/Idle/5.png"
    };

    private final String[] DasherWalkFrames = {
        "Images/Hero/Dasher/Walk1/0.png",
        "Images/Hero/Dasher/Walk1/1.png",
        "Images/Hero/Dasher/Walk1/2.png",
        "Images/Hero/Dasher/Walk1/3.png"
    };

    private final String[] DasherRunFrames = {
        "Images/Hero/Dasher/Run/0.png",
        "Images/Hero/Dasher/Run/1.png",
        "Images/Hero/Dasher/Run/2.png",
        "Images/Hero/Dasher/Run/3.png"
    };

    private final String[] ScarlettIdleFrames = {
        "Images/Hero/Scarlett/Idle/0.png",
        "Images/Hero/Scarlett/Idle/1.png",
        "Images/Hero/Scarlett/Idle/2.png",
        "Images/Hero/Scarlett/Idle/3.png",
        "Images/Hero/Scarlett/Idle/4.png",
        "Images/Hero/Scarlett/Idle/5.png"
    };

    private final String[] ScarlettWalkFrames = {
        "Images/Hero/Scarlett/Walk/0.png",
        "Images/Hero/Scarlett/Walk/1.png",
        "Images/Hero/Scarlett/Walk/2.png",
        "Images/Hero/Scarlett/Walk/3.png",
        "Images/Hero/Scarlett/Walk/4.png",
        "Images/Hero/Scarlett/Walk/5.png",
        "Images/Hero/Scarlett/Walk/6.png",
        "Images/Hero/Scarlett/Walk/7.png"
    };

    private final String[] ScarlettRunFrames = {
        "Images/Hero/Scarlett/Run/0.png",
        "Images/Hero/Scarlett/Run/1.png",
        "Images/Hero/Scarlett/Run/2.png",
        "Images/Hero/Scarlett/Run/3.png"
    };

    private final String[] DiamondIdleFrames = {
        "Images/Hero/Diamond/Idle/0.png",
        "Images/Hero/Diamond/Idle/1.png",
        "Images/Hero/Diamond/Idle/2.png",
        "Images/Hero/Diamond/Idle/3.png",
        "Images/Hero/Diamond/Idle/4.png",
        "Images/Hero/Diamond/Idle/5.png",
    };

    private final String[] DiamondWalkFrames = {
        "Images/Hero/Diamond/Walk/0.png",
        "Images/Hero/Diamond/Walk/1.png",
        "Images/Hero/Diamond/Walk/2.png",
        "Images/Hero/Diamond/Walk/3.png",
        "Images/Hero/Diamond/Walk/4.png",
        "Images/Hero/Diamond/Walk/5.png",
        "Images/Hero/Diamond/Walk/6.png",
        "Images/Hero/Diamond/Walk/7.png"
    };

    private final String[] DiamondRunFrames = {
        "Images/Hero/Diamond/Run/0.png",
        "Images/Hero/Diamond/Run/1.png",
        "Images/Hero/Diamond/Run/2.png",
        "Images/Hero/Diamond/Run/3.png"
    };

    private final String[] ShanaIdleFrames = {
        "Images/Hero/Shana/Idle/0.png",
        "Images/Hero/Shana/Idle/1.png",
        "Images/Hero/Shana/Idle/2.png",
        "Images/Hero/Shana/Idle/3.png",
        "Images/Hero/Shana/Idle/4.png",
        "Images/Hero/Shana/Idle/5.png",
    };

    private final String[] ShanaWalkFrames = {
        "Images/Hero/Shana/Walk/0.png",
        "Images/Hero/Shana/Walk/1.png",
        "Images/Hero/Shana/Walk/2.png",
        "Images/Hero/Shana/Walk/3.png",
        "Images/Hero/Shana/Walk/5.png",
        "Images/Hero/Shana/Walk/6.png",
        "Images/Hero/Shana/Walk/7.png"
    };

    private final String[] ShanaRunFrames = {
        "Images/Hero/Shana/Run/0.png",
        "Images/Hero/Shana/Run/1.png",
        "Images/Hero/Shana/Run/2.png",
        "Images/Hero/Shana/Run/3.png"
    };

    private final String[] LilithIdleFrames = {
        "Images/Hero/Lilith/Idle/0.png",
        "Images/Hero/Lilith/Idle/1.png",
        "Images/Hero/Lilith/Idle/2.png",
        "Images/Hero/Lilith/Idle/3.png",
        "Images/Hero/Lilith/Idle/4.png",
        "Images/Hero/Lilith/Idle/5.png",
    };

    private final String[] LilithWalkFrames = {
        "Images/Hero/Lilith/Walk/0.png",
        "Images/Hero/Lilith/Walk/1.png",
        "Images/Hero/Lilith/Walk/2.png",
        "Images/Hero/Lilith/Walk/3.png",
        "Images/Hero/Lilith/Walk/4.png",
        "Images/Hero/Lilith/Walk/5.png",
        "Images/Hero/Lilith/Walk/6.png",
        "Images/Hero/Lilith/Walk/7.png"
    };

    private final String[] LilithRunFrames = {
        "Images/Hero/Lilith/Run/0.png",
        "Images/Hero/Lilith/Run/1.png",
        "Images/Hero/Lilith/Run/2.png",
        "Images/Hero/Lilith/Run/3.png"
    };

    public Texture getSmgTexture(){
        return smgTexture;
    }

    public String getSmg(){
        return smg;
    }

    public String getBullet(){
        return bullet;
    }

    public String getElderBrain() {
        return elderBrain;
    }

    public String[] getTentacleSpawnFrames() {
        return tentacleSpawnFrames;
    }

    public String[] getTentacleIdleFrames() {
        return tentacleIdleFrames;
    }

    public String getTentacleAttack() {
        return tentacleAttack;
    }

    public String[] getEyeBatFrames() {
        return eyeBatFrames;
    }

    public String getTreeMonsterFrame() {
        return treeMonsterFrames;
    }

    public String getEyeBatProjectile() {
        return eyeBatProjectile;
    }

    public String[] getDasherIdleFrames() {
        return DasherIdleFrames;
    }

    public String[] getScarlettIdleFrames() {
        return ScarlettIdleFrames;
    }

    public String[] getDiamondIdleFrames() {
        return DiamondIdleFrames;
    }

    public String[] getShanaIdleFrames() {
        return ShanaIdleFrames;
    }

    public String[] getLilithIdleFrames() {
        return LilithIdleFrames;
    }

    private Animation<Texture> createAnimationFromPaths(String[] paths, float frameDuration) {
        Texture[] textures = new Texture[paths.length];
        for (int i = 0; i < paths.length; i++) {
            textures[i] = new Texture(Gdx.files.internal(paths[i]));
        }
        return new Animation<>(frameDuration, textures);
    }

    public Animation<Texture> getIdleAnimation(HeroType type) {
         switch (type) {
             case DASHER:  return createAnimationFromPaths(DasherIdleFrames, 0.1f);
             case SCARLET: return createAnimationFromPaths(ScarlettIdleFrames, 0.1f);
             case DIAMOND: return createAnimationFromPaths(DiamondIdleFrames, 0.1f);
             case SHANA: return createAnimationFromPaths(ShanaIdleFrames, 0.1f);
             case LILITH: return createAnimationFromPaths(LilithIdleFrames, 0.1f);
        };
         return null;
    }

    // You can add more like this:
    public Animation<Texture> getWalkAnimation(HeroType type) {
        switch (type) {
            case DASHER: return createAnimationFromPaths(DasherWalkFrames, 0.08f);
            case SCARLET: return createAnimationFromPaths(ScarlettWalkFrames, 0.08f);
            case DIAMOND: return createAnimationFromPaths(DiamondWalkFrames, 0.08f);
            case SHANA: return createAnimationFromPaths(ShanaWalkFrames, 0.08f);
            case LILITH: return createAnimationFromPaths(LilithWalkFrames, 0.08f);
        };
        return null;
    }

    public Animation<Texture> getRunAnimation(HeroType type) {
        switch (type) {
            case DASHER: return createAnimationFromPaths(DasherRunFrames, 0.05f);
            case SCARLET: return createAnimationFromPaths(ScarlettRunFrames, 0.05f);
            case DIAMOND: return createAnimationFromPaths(DiamondRunFrames, 0.05f);
            case SHANA: return createAnimationFromPaths(ShanaRunFrames, 0.05f);
            case LILITH: return createAnimationFromPaths(LilithRunFrames, 0.05f);
        };
        return null;
    }

}
