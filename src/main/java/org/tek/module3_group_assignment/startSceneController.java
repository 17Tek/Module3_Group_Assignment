package org.tek.module3_group_assignment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class startSceneController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab maze1Tab;

    @FXML
    private Tab maze2Tab;

    public void easyMazeButton() throws IOException {
        startMaze(maze1Tab, "mazeOne-view.fxml");
    }

    public void hardMazeButton() throws IOException {
        startMaze(maze2Tab, "mazeTwo-view.fxml");
    }

    private void startMaze(Tab tab, String viewResource) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewResource));
        Pane maze = loader.load();
        tab.setContent(maze);
    }
}