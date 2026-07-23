package com.horizonradio.radio;

import com.horizonradio.audio.IAudioEngine;
import com.horizonradio.core.EventBus;
import com.horizonradio.core.Song;
import com.horizonradio.core.SongChanged;

import java.nio.file.Path;

class TestAudioEngine implements IAudioEngine {

    Path lastPlayedPath;
    boolean playing;

    @Override
    public void play(Path filePath) {
        this.lastPlayedPath = filePath;
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
        return 0;
    }

    @Override
    public double getDurationSeconds() {
        return 0;
    }

    @Override
    public void setVolume(double volume) {
    }
}
