package dev.treshgame.avgfileenjoyer.comparatos;

import dev.treshgame.avgfileenjoyer.models.FilesListItem;

import java.util.Comparator;

public class SortByFileNameComparator implements Comparator<FilesListItem> {
    @Override
    public int compare(FilesListItem t1, FilesListItem t2) {
        return t1.getFileName().compareTo(t2.getFileName());
    }
}
