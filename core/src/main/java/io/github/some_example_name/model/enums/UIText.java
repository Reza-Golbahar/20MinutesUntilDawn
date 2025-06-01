package io.github.some_example_name.model.enums;

import io.github.some_example_name.Main;

public enum UIText {
    PRE_GAME_MENU_TITLE("Pre-Game Menu", "Menu Pré-Jeu"),
    SELECTED_HERO("Selected Hero: ", "Héros Sélectionné : "),
    SELECT_HERO("Choose Hero:", "Choisissez un Héros :"),
    SELECTED_WEAPON("Selected Weapon: ", "Arme Sélectionnée : "),
    SELECT_WEAPON("Choose Weapon:", "Choisissez une Arme :"),
    GAME_DURATION("Game Duration (minutes):", "Durée de Jeu (minutes) :"),
    START_GAME("Start Game", "Démarrer le Jeu"),

    SIGNUP_MENU_TITLE("Signup Menu", "Menu d'inscription"),
    USERNAME("Username", "Nom d'utilisateur"),
    PASSWORD("Password", "Mot de passe"),
    SECURITY_QUESTION("Security Question", "Question de Sécurité"),
    SECURITY_ANSWER("Answer", "Réponse"),
    SIGN_UP("Sign Up", "S'inscrire"),
    CONTINUE_AS_GUEST("Continue as Guest", "Continuer en tant qu'invité"),
    GO_TO_LOGIN_MENU("Go to LogIn Menu", "Aller au menu de connexion"),
    ERROR("Error", "Erreur"),

    LOGIN_MENU_TITLE("Login Menu", "Menu de Connexion"),
    LOGIN("Login", "Connexion"),
    FORGET_PASSWORD("Forget Password", "Mot de passe oublié"),
    GO_BACK_TO_SIGNUP("Go Back To Signup Menu", "Retour au menu d'inscription"),

    // New entries for SettingsMenu
    SETTINGS_TITLE("Settings", "Paramètres"),
    MUSIC_VOLUME("Music Volume:", "Volume de la musique :"),
    SELECT_SONG("Select Song:", "Sélectionner une chanson :"),
    SFX_ACTIVATION("SFX activation", "Activation des effets sonores"),
    AUTO_RELOAD_ACTIVATION("Auto-Reload activation", "Activation du rechargement automatique"),
    BLACK_WHITE_ENVIRONMENT("Black and White Environment", "Environnement en noir et blanc"),
    GO_TO_MAIN_MENU("Go to Main Menu", "Retour au menu principal"),

    // New entries for MainMenu
    SETTINGS("Settings", "Paramètres"),
    PROFILE_MENU("Profile menu", "Menu Profil"),
    GO_TO_PRE_GAME_MENU("Go To Pre-game menu", "Aller au menu pré-jeu"),
    SCOREBOARD("Scoreboard", "Classement"),
    GO_TO_TALENT_MENU("Go To Talent menu", "Aller au menu Talent"),
    LOAD_LAST_SAVED_GAME("Load last saved game", "Charger la dernière sauvegarde"),
    LOGOUT("Logout", "Déconnexion"),
    USERNAME_LABEL("Username:", "Nom d'utilisateur :"),
    SCORE_LABEL("Score:", "Score :"),

    CURRENT_USERNAME("Your current Username: %s", "Votre nom d'utilisateur actuel : %s"),
    CHANGE_USERNAME_PLACEHOLDER("Change username", "Changer le nom d'utilisateur"),
    SUBMIT_USERNAME("Submit Username", "Soumettre le nom d'utilisateur"),
    CURRENT_PASSWORD("Your current Password: %s", "Votre mot de passe actuel : %s"),
    CHANGE_PASSWORD_PLACEHOLDER("Change password", "Changer le mot de passe"),
    SUBMIT_PASSWORD("Submit Password", "Soumettre le mot de passe"),
    DELETE_ACCOUNT("Delete Account", "Supprimer le compte"),
    CHOOSE_FILE("Choose File", "Choisir un fichier"),
    USERNAME_MESSAGE("", ""),  // placeholders for messages (empty by default)
    PASSWORD_MESSAGE("", ""),

    KILLS("Kills : ", "Morts : "),
    TIME("Time : ", "Temps : "),
    AMMO("Ammo : ", "Munitions : "),
    LEVEL("Level : ", "Niveau : ");


    private final String english;
    private final String french;

    UIText(String english, String french) {
        this.english = english;
        this.french = french;
    }

    public String get() {
        // For demo, assume English or French; you can get locale from Gdx or system or config
        Language language;
        if (Main.getCurrentUser() != null)
            language = Main.getCurrentUser().getCurrentLanguage();
        else
            language = Main.getLanguage();
        return language.equals(Language.FRENCH) ? french : english;
    }

}

