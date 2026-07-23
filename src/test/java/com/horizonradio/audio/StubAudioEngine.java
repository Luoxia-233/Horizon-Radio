package com.horizonradio.audio;

import java.nio.file.Path;

class StubAudioEngine implements IAudioEngine {

    boolean playing;

    private Runnable onEnded;

    @Override
    public void play(Path filePath) {
        this.playing = true;
    }

    @Override
    public void pause() {
        this.playing = false;
    }

    @Override
    public void stop() {
        this.playing = false;
    }

    @Override
    public boolean isPlaying() {
        return playing;
    }

    @Override
    public double getCurrentTimeSeconds() {
        return playing ? 30.0 : 0.0;
    }

    @Override
    public double getDurationSeconds() {
        return playing ? 240.0 : 0.0;
    }

    @Override
    public void setVolume(double volume) {
    }

    @Override
    public void setOnEnded(Runnable callback) {
        this.onEnded = callback;
    }
}
