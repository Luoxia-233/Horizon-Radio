package com.horizonradio.radio;

import com.horizonradio.audio.IAudioEngine;
import com.horizonradio.core.EventBus;
import com.horizonradio.core.SongChanged;

import java.nio.file.Path;

public class RadioEngine {

    private final EventBus eventBus;
    private final IAudioEngine audioEngine;
    private final VoiceManager voiceManager;

    public RadioEngine(EventBus eventBus, IAudioEngine audioEngine, VoiceManager voiceManager) {
        this.eventBus = eventBus;
        this.audioEngine = audioEngine;
        this.voiceManager = voiceManager;
    }

    public void start() {
        eventBus.subscribe(SongChanged.class, this::onSongChanged);
    }

    public void onVoiceEnded() {
    }

    private void onSongChanged(SongChanged event) {
        Path voicePath = voiceManager.randomVoice();
        if (voicePath != null) {
            audioEngine.play(voicePath);
        }
    }
}
