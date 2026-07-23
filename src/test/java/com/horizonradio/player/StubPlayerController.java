package com.horizonradio.player;

import com.horizonradio.core.PlayerState;
import com.horizonradio.core.Song;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class StubPlayerController implements PlayerController {

    PlayerState state = PlayerState.IDLE;
    boolean shuffle;
    boolean playCalled;
    boolean pauseCalled;
    boolean nextCalled;
    boolean previousCalled;
    private final List<Song> songs;

    StubPlayerController() {
        this.songs = new ArrayList<>();
        songs.add(new Song("A", "Artist", Path.of("/a.mp3"), 0L));
        songs.add(new Song("B", "Artist", Path.of("/b.mp3"), 0L));
    }

    @Override
    public void play() {
        playCalled = true;
        state = PlayerState.PLAYING;
    }

    @Override
    public void pause() {
        pauseCalled = true;
        state = PlayerState.PAUSED;
    }

    @Override
    public void next() {
        nextCalled = true;
        state = PlayerState.TRANSITION;
    }

    @Override
    public void previous() {
        previousCalled = true;
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
        return songs.get(0);
    }
}
