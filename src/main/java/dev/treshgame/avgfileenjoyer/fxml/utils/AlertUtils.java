package dev.treshgame.avgfileenjoyer.fxml.utils;

import javafx.scene.control.Alert;

public class AlertUtils {
    public static void showErrorAlert(String headerMessage, String contextMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(headerMessage);
        alert.setContentText(contextMessage);
        alert.show();
    }
}
