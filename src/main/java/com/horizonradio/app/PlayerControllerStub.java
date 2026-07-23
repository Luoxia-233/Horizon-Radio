package com.horizonradio.app;

import com.horizonradio.core.PlayerState;
import com.horizonradio.core.Song;
import com.horizonradio.player.PlayerController;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class PlayerControllerStub implements PlayerController {

    private final List<Song> songs;
    private PlayerState state;
    private boolean shuffle;

    PlayerControllerStub() {
        this.songs = new ArrayList<>();
        this.state = PlayerState.IDLE;
        this.shuffle = false;

        songs.add(new Song("Forza Horizon Theme", "Unknown Artist", Path.of("/theme.mp3"), 0L));
        songs.add(new Song("Night Drive", "Unknown Artist", Path.of("/night.mp3"), 0L));
        songs.add(new Song("Sunset Vibes", "Unknown Artist", Path.of("/sunset.mp3"), 0L));
    }

    @Override
    public void play() {
        state = PlayerState.PLAYING;
    }

    @Override
    public void pause() {
        if (state == PlayerState.PLAYING || state == PlayerState.VOICE_PLAYING) {
            state = PlayerState.PAUSED;
        }
    }

    @Override
    public void next() {
        state = PlayerState.TRANSITION;
    }

    @Override
    public void previous() {
        state = PlayerState.TRANSITION;
    }

    @Override
    public void toggleShuffle() {
        shuffle = !shuffle;
    }

    @Override
    public boolean isShuffleEnabled() {
        return shuffle;
    }

    @Override
    public PlayerState getState() {
        return state;
    }

    @Override
    public List<Song> getSongs() {
        return List.copyOf(songs);
    }

    @Override
    public Song getCurrentSong() {
        return songs.isEmpty() ? null : songs.get(0);
    }
}
