package org.tek.module3_group_assignment;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class HelloController {

    @FXML
    private Pane rootPane;

    @FXML
    private Rectangle playerCar;

    @FXML
    public void initialize() {

        rootPane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {

            if (event.getCode() == KeyCode.UP) {
                playerCar.setLayoutY(playerCar.getLayoutY() - 10);
            }

            if (event.getCode() == KeyCode.DOWN) {
                playerCar.setLayoutY(playerCar.getLayoutY() + 10);
            }

            if (event.getCode() == KeyCode.LEFT) {
                playerCar.setLayoutX(playerCar.getLayoutX() - 10);
            }

            if (event.getCode() == KeyCode.RIGHT) {
                playerCar.setLayoutX(playerCar.getLayoutX() + 10);
            }

        });

        Platform.runLater(() -> rootPane.requestFocus());
    }
}
