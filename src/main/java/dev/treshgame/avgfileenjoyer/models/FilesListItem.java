package dev.treshgame.avgfileenjoyer.models;

import java.nio.file.Path;

public class FilesListItem {
    private Path path;
    private String name;
    private FileListItemType type;

    public FilesListItem(Path path, String name, FileListItemType type) {
        this.path = path;
        this.name = name;
        this.type = type;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileListItemType getType() {
        return type;
    }

    public void setType(FileListItemType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FilesListItem{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
