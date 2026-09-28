package org.tek.module3_group_assignment;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class startSceneController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab maze1Tab;

    @FXML
    private Tab maze2Tab;

    // The maze views included in the tabs (fx:include fx:id="mazeOne" / "mazeTwo")
    @FXML
    private Pane mazeOne;

    @FXML
    private Pane mazeTwo;

    @FXML
    public void initialize() {
        // When a maze tab opens, give that maze keyboard focus so W A S D work
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == maze1Tab) {
                Platform.runLater(() -> mazeOne.requestFocus());
            } else if (newTab == maze2Tab) {
                Platform.runLater(() -> mazeTwo.requestFocus());
            }
        });
    }

    // Easy/Hard now switch to the maze tab instead of replacing the whole scene
    public void easyMazeButton(ActionEvent event) {
        tabPane.getSelectionModel().select(maze1Tab);
    }

    public void hardMazeButton(ActionEvent event) {
        tabPane.getSelectionModel().select(maze2Tab);
    }
}