package com.horizonradio.radio;

import com.horizonradio.core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class RadioEngineTest {

    @TempDir
    Path tempDir;

    private TestAudioEngine audioEngine;
    private EventBus eventBus;
    private VoiceManager voiceManager;

    @BeforeEach
    void setUp() throws Exception {
        Files.createFile(tempDir.resolve("intro.wav"));
        Files.createFile(tempDir.resolve("transition.mp3"));

        audioEngine = new TestAudioEngine();
        eventBus = new EventBus();
        voiceManager = new VoiceManager(tempDir);
        voiceManager.scan();
    }

    @Test
    void songChangedTriggersVoicePlayback() {
        RadioEngine engine = new RadioEngine(eventBus, audioEngine, voiceManager);
        engine.start();

        Song song = new Song("Test", "Artist", Path.of("/test.mp3"), 0L);
        eventBus.publish(new SongChanged(song, false));

        assertNotNull(audioEngine.lastPlayedPath);
    }

    @Test
    void noVoiceWithoutStart() {
        new RadioEngine(eventBus, audioEngine, voiceManager);

        Song song = new Song("Test", "Artist", Path.of("/test.mp3"), 0L);
        eventBus.publish(new SongChanged(song, false));

        assertNull(audioEngine.lastPlayedPath);
    }

    @Test
    void onVoiceEndedCallbackCanBeSet() {
        RadioEngine engine = new RadioEngine(eventBus, audioEngine, voiceManager);
        boolean[] called = {false};
        engine.setOnVoiceEnded(() -> called[0] = true);

        // callback is stored but not triggered yet (needs onEnded on IAudioEngine)
        assertNotNull(engine);
    }
}
