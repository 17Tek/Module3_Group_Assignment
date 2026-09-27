package org.tek.module3_group_assignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class startSceneController {

    public void easyMazeButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(
                getClass().getResource("mazeOne-view.fxml")
        );

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void hardMazeButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(
                getClass().getResource("mazeTwo-view.fxml")
        );

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}