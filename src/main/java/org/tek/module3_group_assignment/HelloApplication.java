package org.tek.module3_group_assignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("startScene.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Pixel Racer");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}