package com.horizonradio.player;

import com.horizonradio.audio.IAudioEngine;
import com.horizonradio.core.*;
import com.horizonradio.radio.RadioEngine;

import java.nio.file.Path;
import java.util.List;

public class PlayerControllerImpl implements PlayerController {

    private final IAudioEngine audioEngine;
    private final PlaylistQueue queue;
    private final StateMachine stateMachine;
    private final EventBus eventBus;
    private final RadioEngine radioEngine;
    private List<Song> songs;
    private Path currentFilePath;

    public PlayerControllerImpl(IAudioEngine audioEngine, PlaylistQueue queue,
                                RadioEngine radioEngine, EventBus eventBus) {
        this.audioEngine = audioEngine;
        this.queue = queue;
        this.stateMachine = new StateMachine();
        this.eventBus = eventBus;
        this.radioEngine = radioEngine;
        this.songs = List.of();

        audioEngine.setOnEnded(() -> {
            if (stateMachine.current() == PlayerState.PLAYING) {
                Song current = queue.current();
                if (current != null) {
                    eventBus.publish(new SongChanged(current, false));
                }
                stateMachine.goTo(PlayerState.TRANSITION);
                stateMachine.goTo(PlayerState.VOICE_PLAYING);
            } else if (stateMachine.current() == PlayerState.VOICE_PLAYING) {
                Song next = queue.next();
                currentFilePath = next.filePath();
                audioEngine.play(currentFilePath);
                stateMachine.goTo(PlayerState.PLAYING);
            }
        });

        radioEngine.start();
    }

    @Override
    public void play() {
        if (stateMachine.current() == PlayerState.IDLE) {
            Song song = queue.next();
            currentFilePath = song.filePath();
            audioEngine.play(currentFilePath);
            stateMachine.goTo(PlayerState.PLAYING);
        } else if (stateMachine.current() == PlayerState.PAUSED) {
            audioEngine.play(currentFilePath);
            stateMachine.goTo(PlayerState.PLAYING);
        }
    }

    @Override
    public void pause() {
        if (stateMachine.current() == PlayerState.PLAYING
                || stateMachine.current() == PlayerState.VOICE_PLAYING) {
            audioEngine.pause();
            stateMachine.goTo(PlayerState.PAUSED);
        }
    }

    @Override
    public void next() {
        if (stateMachine.current() == PlayerState.IDLE) {
            play();
            return;
        }
        audioEngine.stop();
        Song song = queue.next();
        currentFilePath = song.filePath();
        stateMachine.goTo(PlayerState.TRANSITION);
        eventBus.publish(new SongChanged(song, true));
        stateMachine.goTo(PlayerState.VOICE_PLAYING);
    }

    @Override
    public void previous() {
        if (stateMachine.current() == PlayerState.IDLE) {
            play();
            return;
        }
        audioEngine.stop();
        Song song = queue.previous();
        currentFilePath = song.filePath();
        stateMachine.goTo(PlayerState.TRANSITION);
        eventBus.publish(new SongChanged(song, true));
        stateMachine.goTo(PlayerState.VOICE_PLAYING);
    }

    @Override
    public void toggleShuffle() {
        queue.setShuffle(!queue.isShuffle());
    }

    @Override
    public boolean isShuffleEnabled() {
        return queue.isShuffle();
    }

    @Override
    public PlayerState getState() {
        return stateMachine.current();
    }

    @Override
    public List<Song> getSongs() {
        return songs;
    }

    @Override
    public Song getCurrentSong() {
        return queue.current();
    }

    @Override
    public void setMusicDirectory(Path directory) {
        MusicLibrary library = new MusicLibrary(directory);
        library.scan();
        this.songs = library.getSongs();
        queue.setSongs(songs);
    }
}
