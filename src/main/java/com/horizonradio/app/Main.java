package com.horizonradio.app;

import com.horizonradio.audio.IAudioEngine;
import com.horizonradio.audio.JavaFXAudioEngine;
import com.horizonradio.core.EventBus;
import com.horizonradio.player.PlayerController;
import com.horizonradio.player.PlayerControllerImpl;
import com.horizonradio.player.PlaylistQueue;
import com.horizonradio.radio.RadioEngine;
import com.horizonradio.radio.VoiceManager;
import com.horizonradio.ui.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        EventBus eventBus = new EventBus();
        IAudioEngine audioEngine = new JavaFXAudioEngine();
        VoiceManager voiceManager = new VoiceManager(Path.of("assets/voice"));
        RadioEngine radioEngine = new RadioEngine(eventBus, audioEngine, voiceManager);
        PlaylistQueue queue = new PlaylistQueue(List.of());
        PlayerController controller = new PlayerControllerImpl(audioEngine, queue, radioEngine, eventBus);

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
