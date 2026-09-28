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
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polyline;

public class mazeOneController {

    @FXML
    private Pane rootPane;

    @FXML
    private Rectangle playerCar;

    // #Elan: Droid, its route, and the status label (added in the maze FXML files)
    @FXML
    private ImageView droid;

    @FXML
    private Polyline solutionPath;

    @FXML
    private Label statusLabel;

    private Timeline solveAnimation;
    private double carStartX;
    private double carStartY;

    @FXML
    public void initialize() {

        Image carImage = new Image(
                getClass().getResource("/images/car.png").toExternalForm()
        );

        playerCar.setFill(new ImagePattern(carImage));


        // #Elan: Remember where the car starts so Reset can put it back
        carStartX = playerCar.getLayoutX();
        carStartY = playerCar.getLayoutY();

        rootPane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {

            if (event.getCode() == KeyCode.W) {
                playerCar.setRotate(-90);
                playerCar.setScaleY(1);
                playerCar.setLayoutY(playerCar.getLayoutY() - 10);
            }

            else if (event.getCode() == KeyCode.S) {
                playerCar.setRotate(90);
                playerCar.setScaleY(1);
                playerCar.setLayoutY(playerCar.getLayoutY() + 10);
            }

            else if (event.getCode() == KeyCode.A) {
                playerCar.setRotate(180);
                playerCar.setScaleY(-1);
                playerCar.setLayoutX(playerCar.getLayoutX() - 10);
            }

            else if (event.getCode() == KeyCode.D) {
                playerCar.setRotate(0);
                playerCar.setScaleY(1);
                playerCar.setLayoutX(playerCar.getLayoutX() + 10);
            }

        });

        Platform.runLater(() -> rootPane.requestFocus());

    }

    // #Elan: Auto Solve button: hides the car and animates the droid from start to exit
    @FXML
    private void handleAutoSolve() {
        if (solveAnimation != null) {
            solveAnimation.stop();
        }

        playerCar.setVisible(false);
        droid.setVisible(true);
        statusLabel.setText("Droid is driving to the exit...");

        solveAnimation = DroidAnimator.buildAnimation(droid, solutionPath.getPoints());
        solveAnimation.setOnFinished(event -> statusLabel.setText("Droid reached the exit!"));
        solveAnimation.play();
    }

    // #Elan: Reset button: stops the droid and puts the car back at the start
    @FXML
    private void handleReset() {
        if (solveAnimation != null) {
            solveAnimation.stop();
        }

        droid.setVisible(false);
        playerCar.setVisible(true);
        playerCar.setLayoutX(carStartX);
        playerCar.setLayoutY(carStartY);
        playerCar.setRotate(0);
        playerCar.setScaleY(1);

        statusLabel.setText("Drive with W A S D, or press Auto Solve.");
        rootPane.requestFocus();
    }
}