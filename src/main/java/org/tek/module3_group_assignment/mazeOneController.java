package org.tek.module3_group_assignment;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class mazeOneController {

    @FXML
    private Pane rootPane;

    @FXML
    private Rectangle playerCar;

    @FXML
    public void initialize() {

        Image carImage = new Image(
                getClass().getResource("/images/car.png").toExternalForm()
        );

        playerCar.setFill(new ImagePattern(carImage));

        rootPane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {

            if (event.getCode() == KeyCode.W) {
                playerCar.setLayoutY(playerCar.getLayoutY() - 10);
            }

            if (event.getCode() == KeyCode.S) {
                playerCar.setLayoutY(playerCar.getLayoutY() + 10);
            }

            if (event.getCode() == KeyCode.A) {
                playerCar.setLayoutX(playerCar.getLayoutX() - 10);
            }

            if (event.getCode() == KeyCode.D) {
                playerCar.setLayoutX(playerCar.getLayoutX() + 10);
            }

        });

        Platform.runLater(() -> rootPane.requestFocus());
    }
}