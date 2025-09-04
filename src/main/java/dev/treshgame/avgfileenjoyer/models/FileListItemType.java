package dev.treshgame.avgfileenjoyer.models;

public enum FileListItemType {
    FILE("File"),
    DIR("Directory");

    private final String name;

    public String getName() {
        return name;
    }

    FileListItemType(String name) {
        this.name = name;
    }
}
