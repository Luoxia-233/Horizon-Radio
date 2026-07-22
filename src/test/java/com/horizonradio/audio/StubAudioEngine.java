package com.horizonradio.audio;

import java.nio.file.Path;

class StubAudioEngine implements IAudioEngine {

    boolean playing;
    boolean stopped;
    boolean paused;
    Path lastPlayedPath;
    double volume = 1.0;

    @Override
    public void play(Path filePath) {
        this.lastPlayedPath = filePath;
        this.playing = true;
        this.stopped = false;
        this.paused = false;
    }

    @Override
    public void pause() {
        if (playing) {
            this.playing = false;
            this.paused = true;
        }
    }

    @Override
    public void stop() {
        this.playing = false;
        this.stopped = true;
        this.paused = false;
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
        this.volume = volume;
    }
}
