package dev.treshgame.avgfileenjoyer.comparatos;

import dev.treshgame.avgfileenjoyer.models.FileListItemType;
import dev.treshgame.avgfileenjoyer.models.FilesListItem;

import java.util.Comparator;

public class SortByFileTypeComparator implements Comparator<FilesListItem> {

    @Override
    public int compare(FilesListItem t1, FilesListItem t2) {
        if (t1.getType() != t2.getType()) {
            if (t1.getType() == FileListItemType.DIR) {
                return -1;
            }
            else {
                return 1;
            }
        } else {
            return t1.getPath().toString().compareTo(t2.getPath().toString());
        }
    }
}
