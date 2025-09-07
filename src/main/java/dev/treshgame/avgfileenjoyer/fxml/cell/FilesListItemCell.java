package dev.treshgame.avgfileenjoyer.fxml.cell;

import dev.treshgame.avgfileenjoyer.models.FilesListItem;
import javafx.scene.control.ListCell;

public class FilesListItemCell extends ListCell<FilesListItem> {
    @Override
    protected void updateItem(FilesListItem listItem, boolean empty) {
        super.updateItem(listItem, empty);
        if (empty || listItem == null)  {
            textProperty().unbind();
            setText(null);
        } else {
            textProperty().unbind();
            textProperty().bind(listItem.getFileNameProperty());
        }
    }
}
