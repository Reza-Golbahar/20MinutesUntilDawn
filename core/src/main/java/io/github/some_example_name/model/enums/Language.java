package io.github.some_example_name.model.enums;

public enum Language {
    ENGLISH("English"),
    FRENCH("Français"),
    ;

    private final String displayName;

    Language(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

