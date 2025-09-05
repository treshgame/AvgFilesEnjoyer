package dev.treshgame.avgfileenjoyer.service;

import dev.treshgame.avgfileenjoyer.fxml.utils.AlertUtils;
import dev.treshgame.avgfileenjoyer.models.FileListItemType;
import dev.treshgame.avgfileenjoyer.models.FilesListItem;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Handles navigation in files system
 */
public class FilesManager {
    // TODO: move to some "Error handler class" for displaying alerts with errors
    private final String DEFAULT_ERROR_HEADER = "ERROR!";

    private final ObservableList<FilesListItem> currentDirFiles;

    private Path currentDir;

    public FilesManager(ObservableList<FilesListItem> currentDirFiles) {
        this.currentDirFiles = currentDirFiles;
    }

    public FilesManager(ObservableList<FilesListItem> currentDirFiles, Path initDir) {
        this.currentDirFiles = currentDirFiles;
        moveToDirectory(initDir);
    }

    public void moveToDirectory(Path destinationDir) {
        try (Stream<Path> filesStream = Files.list(destinationDir)){;
            currentDirFiles.clear();
            filesStream.forEach(this::addPathToFilesList);
            currentDir = destinationDir;
        } catch(IOException e) {
            AlertUtils.showErrorAlert(DEFAULT_ERROR_HEADER, e.getMessage());
        }
    }


    public void moveToPreviousDirectory() {
        Path parent = currentDir.getParent();
        // We're already in the root
        if (parent == null) {
            return;
        }

        moveToDirectory(parent);
    }

    private void addPathToFilesList(Path path) {
        var fileName = path.getFileName().toString();
        FileListItemType filesItemType = Files.isDirectory(path) ? FileListItemType.DIR : FileListItemType.FILE;
        var filesListItem = new FilesListItem(path, fileName, filesItemType);
        currentDirFiles.add(filesListItem);
    }

    public Path getCurrentDir() {
        return this.currentDir;
    }
}
