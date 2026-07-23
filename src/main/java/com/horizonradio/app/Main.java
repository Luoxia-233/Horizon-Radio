package com.horizonradio.app;

import com.horizonradio.ui.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        PlayerControllerStub controller = new PlayerControllerStub();
        MainWindow window = new MainWindow(controller);
        Scene scene = new Scene(window.getRoot(), 800, 500);
        primaryStage.setTitle("Horizon Radio");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
