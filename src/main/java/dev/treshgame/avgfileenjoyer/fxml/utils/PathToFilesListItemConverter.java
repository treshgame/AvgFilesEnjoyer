package dev.treshgame.avgfileenjoyer.fxml.utils;

import dev.treshgame.avgfileenjoyer.models.FileListItemType;
import dev.treshgame.avgfileenjoyer.models.FilesListItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathToFilesListItemConverter {
    public static FilesListItem convertToFilesListItem(Path path) throws IOException {
        var fileName = path.getFileName().toString();
        FileListItemType filesItemType = Files.isDirectory(path) ? FileListItemType.DIR : FileListItemType.FILE;
        boolean hidden = Files.isHidden(path);
        return new FilesListItem(path, fileName, filesItemType, hidden);
    }
}
