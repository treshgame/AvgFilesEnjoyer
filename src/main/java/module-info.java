module dev.treshgame.avgfileenjoyer {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.treshgame.avgfileenjoyer to javafx.fxml, javafx.controls;
    opens dev.treshgame.avgfileenjoyer.controller to javafx.fxml, javafx.controls;
    opens dev.treshgame.avgfileenjoyer.models to javafx.fxml, javafx.controls;
    exports dev.treshgame.avgfileenjoyer;
    exports dev.treshgame.avgfileenjoyer.models;
    exports dev.treshgame.avgfileenjoyer.controller;
}