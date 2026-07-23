package com.horizonradio.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Path;

public class JavaFXAudioEngine implements IAudioEngine {

    private static final double DEFAULT_VOLUME = 1.0;

    private MediaPlayer mediaPlayer;
    private double volume = DEFAULT_VOLUME;
    private Runnable onEndedCallback;

    @Override
    public void play(Path filePath) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            mediaPlayer.play();
            return;
        }
        disposeCurrentPlayer();
        String uri = filePath.toUri().toString();
        Media media = new Media(uri);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(volume);
        if (onEndedCallback != null) {
            mediaPlayer.setOnEndOfMedia(onEndedCallback);
        }
        mediaPlayer.play();
    }

    @Override
    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer != null
                && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    @Override
    public double getCurrentTimeSeconds() {
        if (mediaPlayer == null) {
            return 0.0;
        }
        Duration time = mediaPlayer.getCurrentTime();
        return time == Duration.UNKNOWN ? 0.0 : time.toSeconds();
    }

    @Override
    public double getDurationSeconds() {
        if (mediaPlayer == null || mediaPlayer.getMedia() == null) {
            return 0.0;
        }
        Duration duration = mediaPlayer.getMedia().getDuration();
        return duration == Duration.UNKNOWN ? 0.0 : duration.toSeconds();
    }

    @Override
    public void setVolume(double volume) {
        this.volume = Math.max(0.0, Math.min(1.0, volume));
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(this.volume);
        }
    }

    @Override
    public void setOnEnded(Runnable callback) {
        this.onEndedCallback = callback;
        if (mediaPlayer != null) {
            mediaPlayer.setOnEndOfMedia(callback);
        }
    }

    private void disposeCurrentPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }
}
