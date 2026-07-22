package com.horizonradio.audio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class IAudioEngineTest {

    private IAudioEngine engine;

    @BeforeEach
    void setUp() {
        engine = new StubAudioEngine();
    }

    @Test
    void playSetsPlayingState() {
        engine.play(Path.of("/test/song.mp3"));
        assertTrue(engine.isPlaying());
    }

    @Test
    void pauseStopsPlaying() {
        engine.play(Path.of("/test/song.mp3"));
        engine.pause();
        assertFalse(engine.isPlaying());
    }

    @Test
    void pauseOnIdleDoesNothing() {
        engine.pause();
        assertFalse(engine.isPlaying());
    }

    @Test
    void stopSetsNotPlaying() {
        engine.play(Path.of("/test/song.mp3"));
        engine.stop();
        assertFalse(engine.isPlaying());
    }

    @Test
    void playAfterStopRestarts() {
        engine.play(Path.of("/test/first.mp3"));
        engine.stop();
        engine.play(Path.of("/test/second.mp3"));
        assertTrue(engine.isPlaying());
    }

    @Test
    void getCurrentTimeAfterPlayReturnsValue() {
        engine.play(Path.of("/test/song.mp3"));
        double time = engine.getCurrentTimeSeconds();
        assertTrue(time > 0.0);
    }

    @Test
    void getCurrentTimeBeforePlayReturnsZero() {
        assertEquals(0.0, engine.getCurrentTimeSeconds());
    }

    @Test
    void setVolumeBetweenZeroAndOne() {
        engine.setVolume(0.5);
        assertEquals(0.5, ((StubAudioEngine) engine).volume);
    }

    @Test
    void getDurationReturnsValueWhenPlaying() {
        engine.play(Path.of("/test/song.mp3"));
        assertTrue(engine.getDurationSeconds() > 0.0);
    }

    @Test
    void getDurationReturnsZeroWhenIdle() {
        assertEquals(0.0, engine.getDurationSeconds());
    }
}
