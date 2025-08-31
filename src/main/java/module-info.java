module dev.treshgame.avgfileenjoyer {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.treshgame.avgfileenjoyer to javafx.fxml;
    exports dev.treshgame.avgfileenjoyer;
}