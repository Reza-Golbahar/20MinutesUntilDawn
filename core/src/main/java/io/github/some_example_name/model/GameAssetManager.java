package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
import io.github.some_example_name.model.enums.HeroType;

import java.util.HashMap;
import java.util.Map;

public class GameAssetManager {
    private static ShaderProgram grayscaleShader;

    private static GameAssetManager gameAssetManager;

    private final Skin skin = new Skin(Gdx.files.internal("skin/star-soldier-ui.json"));

    private final String whiteCircle = "Images/HitImpactFX_0.png";

    private final String[] backGroundMusicPaths = {
        "BackgroundMusic/Hello.mp3",
        "BackgroundMusic/Chandelier.mp3",
        "BackgroundMusic/Venom.mp3"
    };

    private final String[] avatarPaths = {
        "Images/Portraits/T_Abby_Portrait.png",
        "Images/Portraits/T_Dasher_Portrait.png",
        "Images/Portraits/T_Diamond_Portrait.png",
        "Images/Portraits/T_Hastur_Portrait.png",
        "Images/Portraits/T_Hina_Portrait.png",
        "Images/Portraits/T_Lilith_Portrait.png",
        "Images/Portraits/T_Luna_Portrait.png",
        "Images/Portraits/T_Raven_Portrait.png",
        "Images/Portraits/T_Scarlett_Portrait.png",
        "Images/Portraits/T_Shana_Portrait.png",
        "Images/Portraits/T_Spark_Portrait.png",
        "Images/Portraits/T_Yuki_Portrait.png"
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

    private final String[] damageAnimationFrames = {
        "Images/DamageAnimation/ExplosionFX_0.png",
        "Images/DamageAnimation/ExplosionFX_1.png",
        "Images/DamageAnimation/ExplosionFX_2.png",
        "Images/DamageAnimation/ExplosionFX_3.png",
        "Images/DamageAnimation/ExplosionFX_4.png",
        "Images/DamageAnimation/ExplosionFX_5.png"
    };

    private final String SMGDual = "Images/Weapons/smg/T_DualSMGs_Icon.png";

    private final String[] SMGDualReloadFrames = {
        "Images/Weapons/smg/SMGReload_0.png",
        "Images/Weapons/smg/SMGReload_1.png",
        "Images/Weapons/smg/SMGReload_2.png",
        "Images/Weapons/smg/SMGReload_3.png"
    } ;

    private final String shotgun = "Images/Weapons/shotgun/T_Shotgun_SS_0.png";

    private final String[] shotgunReloadFrames = {
        "Images/Weapons/shotgun/T_Shotgun_SS_1.png",
        "Images/Weapons/shotgun/T_Shotgun_SS_2.png",
        "Images/Weapons/shotgun/T_Shotgun_SS_3.png"
    };

    private final String revolver = "Images/Weapons/revolver/RevolverStill.png";

    private final String[] revolverReloadFrames = {
        "Images/Weapons/revolver/RevolverReload_0.png",
        "Images/Weapons/revolver/RevolverReload_1.png",
        "Images/Weapons/revolver/RevolverReload_2.png",
        "Images/Weapons/revolver/RevolverReload_3.png"
    };

    private final String bullet = "Images/Weapons/bullets/Icon_LightBullet.png";
    private String ammo = "Images/Weapons/T_AmmoIcon.png";

    private String[] redHeartFrames = {
        "Images/Heart/HeartAnimation_0.png",
        "Images/Heart/HeartAnimation_1.png",
        "Images/Heart/HeartAnimation_2.png"
    };

    private String blackHeart = "Images/Heart/HeartAnimation_3.png";

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

    public Sound youWinSound;
    public Sound youLostSound;
    public Sound levelUpSound;
    public Sound reloadSound;
    public Sound shootSound;

    public void loadSounds() {
        youWinSound = Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/You Win (2).wav"));
        youLostSound = Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/You Lose (4).wav"));
        levelUpSound = Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Special & Powerup (10).wav"));
        reloadSound = Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/Weapon_Shotgun_Reload.wav"));
        shootSound = Gdx.audio.newSound(Gdx.files.internal("SFX/AudioClip/single_shot.wav"));
    }

    public void disposeSounds() {
        youWinSound.dispose();
        youLostSound.dispose();
        levelUpSound.dispose();
        reloadSound.dispose();
        shootSound.dispose();
    }

    public String getSMGDual(){
        return SMGDual;
    }

    public String getBullet(){
        return bullet;
    }

    public String[] getSMGDualReloadFrames() {
        return SMGDualReloadFrames;
    }

    public String getShotgun() {
        return shotgun;
    }

    public String[] getShotgunReloadFrames() {
        return shotgunReloadFrames;
    }

    public String getRevolver() {
        return revolver;
    }

    public String[] getRevolverReloadFrames() {
        return revolverReloadFrames;
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

    public Animation<Texture> createAnimationFromPaths(String[] paths, float frameDuration) {
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

    public String getAmmo() {
        return ammo;
    }

    public String[] getRedHeartFrames() {
        return redHeartFrames;
    }

    public String getBlackHeart() {
        return blackHeart;
    }

    public String getWhiteCircle() {
        return whiteCircle;
    }

    public static void loadShaders() {
        ShaderProgram.pedantic = false;
        grayscaleShader = new ShaderProgram(
            Gdx.files.internal("shaders/default.vert"), // You can copy LibGDX's default.vert or omit this
            Gdx.files.internal("shaders/grayscale.frag")
        );

        if (!grayscaleShader.isCompiled()) {
            throw new GdxRuntimeException("Grayscale shader failed: " + grayscaleShader.getLog());
        }
    }

    public static ShaderProgram getGrayscaleShader() {
        return grayscaleShader;
    }

    public String[] getDamageAnimationFrames() {
        return damageAnimationFrames;
    }
}
