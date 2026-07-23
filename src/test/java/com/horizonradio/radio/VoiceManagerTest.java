package com.horizonradio.radio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class VoiceManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void scanFindsVoiceFiles() throws Exception {
        Files.createFile(tempDir.resolve("intro.wav"));
        Files.createFile(tempDir.resolve("transition.mp3"));

        VoiceManager manager = new VoiceManager(tempDir);
        manager.scan();

        assertEquals(2, manager.count());
    }

    @Test
    void scanFiltersNonAudioFiles() throws Exception {
        Files.createFile(tempDir.resolve("intro.wav"));
        Files.createFile(tempDir.resolve("script.txt"));

        VoiceManager manager = new VoiceManager(tempDir);
        manager.scan();

        assertEquals(1, manager.count());
    }

    @Test
    void randomVoiceReturnsNullWhenEmpty() {
        VoiceManager manager = new VoiceManager(tempDir);
        manager.scan();

        assertNull(manager.randomVoice());
    }

    @Test
    void randomVoiceReturnsAFileWhenAvailable() throws Exception {
        Files.createFile(tempDir.resolve("intro.wav"));

        VoiceManager manager = new VoiceManager(tempDir);
        manager.scan();

        Path voice = manager.randomVoice();
        assertNotNull(voice);
        assertTrue(voice.getFileName().toString().endsWith(".wav"));
    }
}
