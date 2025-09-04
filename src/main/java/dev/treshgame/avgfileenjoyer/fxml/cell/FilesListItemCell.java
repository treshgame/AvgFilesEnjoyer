package dev.treshgame.avgfileenjoyer.fxml.cell;

import dev.treshgame.avgfileenjoyer.models.FilesListItem;
import javafx.scene.control.ListCell;

public class FilesListItemCell extends ListCell<FilesListItem> {
    @Override
    protected void updateItem(FilesListItem listItem, boolean empty) {
        super.updateItem(listItem, empty);
        if (empty || listItem == null)  {
            setText(null); } else {
            setText(listItem.getName() + "\t" + listItem.getType().getName());
        }
    }
}
