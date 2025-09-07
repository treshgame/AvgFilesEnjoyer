package dev.treshgame.avgfileenjoyer.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.nio.file.Path;

public class FilesListItem {
    private final StringProperty fileNameProperty;
    private Path path;
    private FileListItemType type;

    public FilesListItem(Path path, String name, FileListItemType type) {
        this.path = path;
        this.fileNameProperty = new SimpleStringProperty(name);
        this.type = type;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public FileListItemType getType() {
        return type;
    }

    public void setType(FileListItemType type) {
        this.type = type;
    }

    public String getFileName() {
        return fileNameProperty.get();
    }

    public void setFileName(String fileNameProperty) {
        this.fileNameProperty.set(fileNameProperty);
    }

    public StringProperty getFileNameProperty() {
        return this.fileNameProperty;
    }

    @Override
    public String toString() {
        return "FilesListItem{" +
                "path='" + path + '\'' +
                ", name='" + fileNameProperty.get() + '\'' +
                ", type=" + type +
                '}';
    }
}
