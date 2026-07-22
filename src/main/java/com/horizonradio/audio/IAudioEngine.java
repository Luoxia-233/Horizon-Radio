package com.horizonradio.audio;

import java.nio.file.Path;

public interface IAudioEngine {

    void play(Path filePath);

    void pause();

    void stop();

    boolean isPlaying();

    double getCurrentTimeSeconds();

    double getDurationSeconds();

    void setVolume(double volume);
}
