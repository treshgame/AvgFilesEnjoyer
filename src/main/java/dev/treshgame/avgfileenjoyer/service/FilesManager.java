package dev.treshgame.avgfileenjoyer.service;

import dev.treshgame.avgfileenjoyer.comparatos.FilesSortType;
import dev.treshgame.avgfileenjoyer.comparatos.SortingTypeContext;
import dev.treshgame.avgfileenjoyer.fxml.utils.AlertUtils;
import dev.treshgame.avgfileenjoyer.fxml.utils.PathToFilesListItemConverter;
import dev.treshgame.avgfileenjoyer.models.FilesListItem;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

/**
 * Handles navigation in files system and files create/delete
 */
public class FilesManager {
    // TODO: move to some "Error handler class" for displaying alerts with errors
    private final String DEFAULT_ERROR_HEADER = "ERROR!";

    private final ObservableList<FilesListItem> currentDirFiles;

    private Path currentDir;
    private final SortingTypeContext sortingTypeContext;

    private boolean showHiddenFiles = false;

    public FilesManager(ObservableList<FilesListItem> currentDirFiles) {
        this.currentDirFiles = currentDirFiles;
        sortingTypeContext = new SortingTypeContext(FilesSortType.FILE_TYPE);
    }

    public void moveToDirectory(Path destinationDir) {
        try (Stream<Path> filesStream = Files.list(destinationDir)){;
            currentDirFiles.clear();
            filesStream.forEach(this::addPathToFilesList);
            currentDir = destinationDir;
            sortingTypeContext.sort(currentDirFiles);
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
        try {
            var filesListItem = PathToFilesListItemConverter.convertToFilesListItem(path);
            if (showHiddenFiles || !filesListItem.isHidden()) {
                currentDirFiles.add(filesListItem);
            }
        } catch (IOException e) {
            AlertUtils.showErrorAlert(DEFAULT_ERROR_HEADER, e.getMessage());
        }
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

    public void deleteFile(FilesListItem fileToDelete, int fileIndexInList) {
        try {
            deleteFileItemFromList(fileToDelete, fileIndexInList);
        } catch (IOException exception) {
            AlertUtils.showErrorAlert(DEFAULT_ERROR_HEADER, exception.getMessage());
        }
    }

    private void deleteFileItemFromList(FilesListItem filesToDelete, int fileIndexInList) throws IOException {
        Path deletePath = filesToDelete.getPath();
        switch (filesToDelete.getType()) {
            case DIR -> deleteDirRecursive(deletePath);
            case FILE -> Files.deleteIfExists(deletePath);
        }
        currentDirFiles.remove(fileIndexInList);
    }

    private void deleteDirRecursive(Path deletePath) throws IOException {
        Files.walkFileTree(deletePath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public void renameFile(Path fileToRename, String newName, int selectedFileIndex) {
        try {
            Path newFileName = fileToRename.resolveSibling(newName);
            Files.move(fileToRename, newFileName);

            FilesListItem file = currentDirFiles.get(selectedFileIndex);
            file.setPath(newFileName);
            file.setFileName(newFileName.getFileName().toString());
        } catch (IOException exception) {
            AlertUtils.showErrorAlert(DEFAULT_ERROR_HEADER, exception.getMessage());
        }
    }

    /**
     * Change how files are sorted in the list
     *
     * @param filesSortType - type of sorting
     */
    public void changeSortType(FilesSortType filesSortType) {
        if (filesSortType != sortingTypeContext.getSortType()) {
            sortingTypeContext.setSortType(filesSortType);
            sortingTypeContext.sort(currentDirFiles);
        }
    }

    /**
     * Hide/show files depends on the new property
     *
     * @param show - show hidden files or not
     */
    public void setShowHiddenFiles(boolean show) {
        if (show == showHiddenFiles) {
            return;
        }

        showHiddenFiles = show;
        if (!show) {
            currentDirFiles.removeIf(FilesListItem::isHidden);
        } else {
            // Not really efficient to search files in the same directory again
            // But I do it in case if new hidden files were added
            moveToDirectory(currentDir);
        }
    }
}
