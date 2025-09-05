package dev.treshgame.avgfileenjoyer.fxml.utils;

import dev.treshgame.avgfileenjoyer.models.FileListItemType;
import dev.treshgame.avgfileenjoyer.models.FilesListItem;

import java.nio.file.Files;
import java.nio.file.Path;

public class PathToFilesListItemConverter {
    public static FilesListItem convertToFilesListItem(Path path) {
        var fileName = path.getFileName().toString();
        FileListItemType filesItemType = Files.isDirectory(path) ? FileListItemType.DIR : FileListItemType.FILE;
        return new FilesListItem(path, fileName, filesItemType);
    }
}
