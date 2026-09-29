package org.tek.module3_group_assignment;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polyline;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class MazeController {

    @FXML
    private Pane rootPane;

    @FXML
    private Rectangle playerCar;

    @FXML
    private ImageView droid;

    @FXML
    private Polyline solutionPath;

    @FXML
    private Label statusLabel;

    private Image robotImage;
    private PixelReader robotReader;
    private ImageView mazeView;
    private PixelReader mazeReader;
    private int wallColor;
    private double startX;
    private double startY;
    private Timeline solveAnimation;

    @FXML
    public void initialize() {
        robotImage = new Image(
                getClass().getResource("/images/car.png").toExternalForm()
        );
        robotReader = robotImage.getPixelReader();
        playerCar.setFill(new ImagePattern(robotImage));
        startX = playerCar.getLayoutX();
        startY = playerCar.getLayoutY();
        getMazeOneHitBox();

        rootPane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            double dx = 0;
            double dy = 0;
            if (event.getCode() == KeyCode.W) {
                dy = -1;
                playerCar.setRotate(-90);
                playerCar.setScaleY(1);
            } else if (event.getCode() == KeyCode.S) {
                dy = 1;
                playerCar.setRotate(90);
                playerCar.setScaleY(1);
            } else if (event.getCode() == KeyCode.A) {
                dx = -1;
                playerCar.setRotate(180);
                playerCar.setScaleY(-1);
            } else if (event.getCode() == KeyCode.D) {
                dx = 1;
                playerCar.setRotate(0);
                playerCar.setScaleY(1);
            } else {
                return;
            }

            moveRobot(dx, dy, 10);
            event.consume();
        });

        Platform.runLater(() -> rootPane.requestFocus());
    }

    @FXML
    private void handleReset() {
        if (solveAnimation != null) {
            solveAnimation.stop();
        }
        playerCar.setLayoutX(startX);
        playerCar.setLayoutY(startY);
        playerCar.setVisible(true);
        droid.setVisible(false);
        solutionPath.setVisible(false);
        statusLabel.setText("Drive with W A S D, or press Auto Solve.");
        rootPane.requestFocus();
    }

    @FXML
    private void handleAutoSolve() {
        if (solveAnimation != null) {
            solveAnimation.stop();
        }
        playerCar.setVisible(false);
        droid.setVisible(true);
        solutionPath.setVisible(true);
        statusLabel.setText("Droid is solving the maze...");
        solveAnimation = DroidAnimator.buildAnimation(droid, solutionPath.getPoints());
        solveAnimation.setOnFinished(event -> statusLabel.setText("Maze completed!"));
        solveAnimation.playFromStart();
    }

    private void moveRobot(double dx, double dy, int distance) {
        for (int i = 0; i < distance; i++) {
            double nextX = playerCar.getLayoutX() + dx;
            double nextY = playerCar.getLayoutY() + dy;
            int currentCollisions = countCollisions(
                    playerCar.getLayoutX(), playerCar.getLayoutY()
            );
            int nextCollisions = countCollisions(nextX, nextY);

            // Permit movement out of an initial overlap, but never farther into a wall.
            if (nextCollisions != 0
                    && (currentCollisions == 0 || nextCollisions >= currentCollisions)) {
                break;
            }

            playerCar.setLayoutX(nextX);
            playerCar.setLayoutY(nextY);
        }
    }

    private int countCollisions(double robotX, double robotY) {
        int collisions = 0;
        Bounds mazeBounds = mazeView.getBoundsInParent();
        int robotWidth = (int) Math.ceil(playerCar.getWidth());
        int robotHeight = (int) Math.ceil(playerCar.getHeight());

        for (int y = 0; y < robotHeight; y++) {
            for (int x = 0; x < robotWidth; x++) {
                double robotPixelX = (x + 0.5) / playerCar.getWidth() * robotImage.getWidth();
                double robotPixelY = (y + 0.5) / playerCar.getHeight() * robotImage.getHeight();
                if ((robotReader.getArgb(
                        Math.min((int) robotPixelX, (int) robotImage.getWidth() - 1),
                        Math.min((int) robotPixelY, (int) robotImage.getHeight() - 1)
                ) >>> 24) == 0) {
                    continue;
                }

                double paneX = robotX + x + 0.5;
                double paneY = robotY + y + 0.5;
                if (!rootPane.getBoundsInLocal().contains(paneX, paneY)
                        || !mazeBounds.contains(paneX, paneY)) {
                    collisions++;
                    continue;
                }

                int mazeX = (int) ((paneX - mazeBounds.getMinX())
                        / mazeBounds.getWidth() * mazeView.getImage().getWidth());
                int mazeY = (int) ((paneY - mazeBounds.getMinY())
                        / mazeBounds.getHeight() * mazeView.getImage().getHeight());
                int argb = mazeReader.getArgb(
                        Math.min(mazeX, (int) mazeView.getImage().getWidth() - 1),
                        Math.min(mazeY, (int) mazeView.getImage().getHeight() - 1)
                );
                if (isWall(argb)) {
                    collisions++;
                }
            }
        }
        return collisions;
    }

    private boolean isWall(int argb) {
        if ((argb >>> 24) == 0) {
            return false;
        }
        int redDifference = Math.abs(((argb >> 16) & 0xff) - ((wallColor >> 16) & 0xff));
        int greenDifference = Math.abs(((argb >> 8) & 0xff) - ((wallColor >> 8) & 0xff));
        int blueDifference = Math.abs((argb & 0xff) - (wallColor & 0xff));
        return redDifference + greenDifference + blueDifference < 160;
    }

    private void getMazeOneHitBox() {
        mazeView = rootPane.getChildrenUnmodifiable().stream()
                .filter(ImageView.class::isInstance)
                .map(ImageView.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Maze background ImageView not found"));
        mazeReader = mazeView.getImage().getPixelReader();
        wallColor = findWallColor(mazeReader, (int) mazeView.getImage().getWidth(),
                (int) mazeView.getImage().getHeight());
    }

    private int findWallColor(PixelReader reader, int width, int height) {
        Map<Integer, Integer> colorCounts = new HashMap<>();
        int mostCommonColor = 0;
        int highestCount = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = reader.getArgb(x, y);
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;
                if ((color >>> 24) == 0 || (red > 245 && green > 245 && blue > 245)) {
                    continue;
                }
                int count = colorCounts.merge(color, 1, Integer::sum);
                if (count > highestCount) {
                    highestCount = count;
                    mostCommonColor = color;
                }
            }
        }
        if (highestCount == 0) {
            throw new IllegalStateException("Maze image has no wall pixels");
        }
        return mostCommonColor;
    }
}