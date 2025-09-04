package dev.treshgame.avgfileenjoyer.controller;

import dev.treshgame.avgfileenjoyer.fxml.cell.FilesListItemCell;
import dev.treshgame.avgfileenjoyer.fxml.utils.AlertUtils;
import dev.treshgame.avgfileenjoyer.models.FileListItemType;
import dev.treshgame.avgfileenjoyer.models.FilesListItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class MainWindowController implements Initializable {
    private final String ROOT_DIRECTORY = "/";
    private final String DEFAULT_ERROR_HEADER = "ERROR!";

    @FXML
    private ListView<FilesListItem> filesListView;
    @FXML
    private Label currentDirLabel;
    @FXML
    private Button goBackButton;

    private ObservableList<FilesListItem> filesObservable;

    private Path currentDir;
    private LinkedList<Path> dirsPaths;

    private String currentDirecotry = "/";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initFilesListView();

        dirsPaths = new LinkedList<>();
        goBackButton.setOnMouseClicked(event -> moveToPreviousDirectory());

        var rootDirectoryPath = Path.of(currentDirecotry);
        dirsPaths.add(rootDirectoryPath);

        try (Stream<Path> filesStream = Files.list(rootDirectoryPath) ){
            filesStream.forEach(this::addPathToFilesList);
        } catch (IOException e) {
            AlertUtils.showErrorAlert(DEFAULT_ERROR_HEADER, e.getMessage());
        }

        currentDir = Path.of(currentDirecotry);
        currentDirLabel.setText(currentDirecotry);
    }

    private void initFilesListView() {
        filesObservable = FXCollections.observableArrayList();
        filesListView.setItems(filesObservable);

        filesListView.setCellFactory(listView -> new FilesListItemCell());

        filesListView.setOnMouseClicked(this::filesListDoubleClickHandler);
    }

    private void filesListDoubleClickHandler(MouseEvent event) {
        if (event.getClickCount() == 2) {
            FilesListItem chosenItem = filesListView.getSelectionModel().getSelectedItem();
            handleFilesItemAction(chosenItem);
        }
    }

    private void addPathToFilesList(Path path) {
        var fileName = path.getFileName().toString();
        FileListItemType filesItemType = Files.isDirectory(path) ? FileListItemType.DIR : FileListItemType.FILE;
        var filesListItem = new FilesListItem(path, fileName, filesItemType);
        filesObservable.add(filesListItem);
    }

    private void handleFilesItemAction(FilesListItem filesListItem) {
        switch (filesListItem.getType()) {
            case DIR ->  {
                moveToAnotherDir(filesListItem.getPath());
                dirsPaths.add(filesListItem.getPath());
            }
            case FILE -> System.out.println("FIle is chosen, so there's nothing to do yet");
        }
    }

    private void moveToAnotherDir(Path destinationDir) {
        System.out.println("Moving to another directory: " + destinationDir.toString());
        filesObservable.clear();
        try (Stream<Path> filesStream = Files.list(destinationDir)){;
            filesStream.forEach(this::addPathToFilesList);
        } catch(IOException e) {
            AlertUtils.showErrorAlert(DEFAULT_ERROR_HEADER, e.getMessage());
        }
    }

    private void moveToPreviousDirectory() {
        // We are already in the root
        if (dirsPaths.size() <= 1) {
            return;
        }
        dirsPaths.removeLast();
        moveToAnotherDir(dirsPaths.getLast());
    }
}
