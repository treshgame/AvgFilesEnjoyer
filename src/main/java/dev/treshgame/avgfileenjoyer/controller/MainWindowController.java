package dev.treshgame.avgfileenjoyer.controller;

import dev.treshgame.avgfileenjoyer.fxml.cell.FilesListItemCell;
import dev.treshgame.avgfileenjoyer.models.FilesListItem;
import dev.treshgame.avgfileenjoyer.service.FilesManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    private final String ROOT_DIRECTORY = "/";

    private final String CREATE_DIR_DIALOG_HEADER = "Enter new directory name";
    private final String CREATE_DIR_DIALOG_TITLE = "Create dir";

    private final String RENAME_FILE_DIALOG_HEADER = "Enter new name";
    private final String RENAME_FILE_DIALOG_TITLE = "Rename file";

    // TODO: FilesListItem is probably useless, delete it and left just Path object
    @FXML
    private ListView<FilesListItem> filesListView;
    @FXML
    private Label currentDirLabel;
    @FXML
    private Button goBackButton;
    @FXML
    private Button createDirButton;

    private ObservableList<FilesListItem> currentDirFilesList;
    private FilesManager filesManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentDirFilesList = FXCollections.observableArrayList();

        filesListView.setContextMenu(createContextMenuForFilesItem());

        initFilesListView();
        filesManager = new FilesManager(currentDirFilesList);
        filesManager.moveToDirectory(Path.of(ROOT_DIRECTORY));

        goBackButton.setOnMouseClicked(event -> filesManager.moveToPreviousDirectory());
        createDirButton.setOnMouseClicked(this::handleCreateDirectoryButton);

        currentDirLabel.setText(ROOT_DIRECTORY);
    }

    private void initFilesListView() {
        filesListView.setItems(currentDirFilesList);
        filesListView.setCellFactory(listView -> new FilesListItemCell());

        filesListView.setOnMouseClicked(this::filesListDoubleClickHandler);
    }

    private void filesListDoubleClickHandler(MouseEvent event) {
        if (event.getClickCount() == 2) {
            FilesListItem chosenItem = filesListView.getSelectionModel().getSelectedItem();
            handleFilesItemAction(chosenItem);
        }
    }

    private void handleFilesItemAction(FilesListItem filesListItem) {
        switch (filesListItem.getType()) {
            case DIR ->  {
                filesManager.moveToDirectory(filesListItem.getPath());
            }
            case FILE -> System.out.println("FIle is chosen, so there's nothing to do yet");
        }
    }

    private void handleCreateDirectoryButton(MouseEvent event) {
        Optional<String> newDirNameOpt = getInputFromTextInputDialog(CREATE_DIR_DIALOG_HEADER, CREATE_DIR_DIALOG_TITLE);
        if (newDirNameOpt.isEmpty()) {
            return;
        }

        filesManager.createNewFileCurrentDir(newDirNameOpt.get());
    }

    private Optional<String> getInputFromTextInputDialog(String headerText, String title, String defaultValue) {
        var dialog = new TextInputDialog(defaultValue);
        dialog.setHeaderText(headerText);
        dialog.setTitle(title);

        return dialog.showAndWait();
    }

    private Optional<String> getInputFromTextInputDialog(String headerText, String title) {
        return getInputFromTextInputDialog(headerText, title, "");
    }

    private ContextMenu createContextMenuForFilesItem() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteMenuItem = new MenuItem("Delete");
        MenuItem renameMenuItem = new MenuItem("Rename");

        contextMenu.getItems().add(deleteMenuItem);
        contextMenu.getItems().add(renameMenuItem);

        deleteMenuItem.setOnAction(this::onDeleteFileMenuItem);
        renameMenuItem.setOnAction(this::onRenameFileItem);

        return contextMenu;
    }

    private void onDeleteFileMenuItem(ActionEvent event) {
        FilesListItem selectedFile = filesListView.getSelectionModel().getSelectedItem();
        int selectedFileIndex = filesListView.getSelectionModel().getSelectedIndex();
        filesManager.deleteFile(selectedFile, selectedFileIndex);
    }

    private void onRenameFileItem(ActionEvent event) {
        FilesListItem selectedFile = filesListView.getSelectionModel().getSelectedItem();
        String fileName = selectedFile.getPath().getFileName().toString();
        Optional<String> newNameOpt = getInputFromTextInputDialog(
                RENAME_FILE_DIALOG_HEADER, RENAME_FILE_DIALOG_TITLE, fileName
        );

        if (newNameOpt.isEmpty()) {
            return;
        }

        int selectedFileIndex = filesListView.getSelectionModel().getSelectedIndex();
        filesManager.renameFile(selectedFile.getPath(), newNameOpt.get(), selectedFileIndex);
        filesListView.edit(selectedFileIndex);
    }
}
