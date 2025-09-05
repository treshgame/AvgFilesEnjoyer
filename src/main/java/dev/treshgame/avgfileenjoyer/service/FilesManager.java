package dev.treshgame.avgfileenjoyer.service;

import dev.treshgame.avgfileenjoyer.fxml.utils.AlertUtils;
import dev.treshgame.avgfileenjoyer.fxml.utils.PathToFilesListItemConverter;
import dev.treshgame.avgfileenjoyer.models.FilesListItem;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Handles navigation in files system and files create/delete
 */
public class FilesManager {
    // TODO: move to some "Error handler class" for displaying alerts with errors
    private final String DEFAULT_ERROR_HEADER = "ERROR!";

    private final ObservableList<FilesListItem> currentDirFiles;

    private Path currentDir;

    public FilesManager(ObservableList<FilesListItem> currentDirFiles) {
        this.currentDirFiles = currentDirFiles;
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
        var filesListItem = PathToFilesListItemConverter.convertToFilesListItem(path);
        currentDirFiles.add(filesListItem);
    }

    public void createNewFileCurrentDir(String newDirName) {
        Path newDirPath = currentDir.resolve(newDirName);

        try {
            Files.createDirectory(newDirPath);
            var newFilesListItem = PathToFilesListItemConverter.convertToFilesListItem(newDirPath);
            currentDirFiles.add(newFilesListItem);
        } catch (IOException exception) {
            AlertUtils.showErrorAlert("ERROR!", exception.getMessage());
        }
    }
}
