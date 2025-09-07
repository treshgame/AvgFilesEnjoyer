package dev.treshgame.avgfileenjoyer.comparatos;

import dev.treshgame.avgfileenjoyer.models.FilesListItem;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortingTypeContext {
    private final Map<FilesSortType, Comparator<FilesListItem>> comparators;

    private Comparator<FilesListItem> currentComparator;
    private FilesSortType currentSortType;

    {
        comparators = new HashMap<>();

        comparators.put(FilesSortType.FILE_TYPE, new SortByFileTypeComparator());
        comparators.put(FilesSortType.FILE_NAME, new SortByFileNameComparator());
    }

    public SortingTypeContext() {
        // getting default one if not defined
        currentSortType = FilesSortType.FILE_TYPE;
        currentComparator = comparators.get(currentSortType);
    }

    public SortingTypeContext(FilesSortType filesSortType) {
        currentComparator = comparators.get(filesSortType);
    }


    public void sort(ObservableList<FilesListItem> filesListItems) {
        filesListItems.sort(currentComparator);
    }

    public void setSortType(FilesSortType filesSortType) {
        currentComparator = comparators.get(filesSortType);
        currentSortType = filesSortType;
    }

    /**
     * Return sort type that is currently used by context
     */
    public FilesSortType getSortType() {
        return currentSortType;
    }
}
