package com.horizonradio.player;

import com.horizonradio.core.PlayerState;
import com.horizonradio.core.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {

    private StubPlayerController controller;

    @BeforeEach
    void setUp() {
        controller = new StubPlayerController();
    }

    @Test
    void startsInIdle() {
        assertEquals(PlayerState.IDLE, controller.getState());
    }

    @Test
    void playTransitionsToPlaying() {
        controller.play();
        assertEquals(PlayerState.PLAYING, controller.getState());
    }

    @Test
    void pauseTransitionsToPaused() {
        controller.play();
        controller.pause();
        assertEquals(PlayerState.PAUSED, controller.getState());
    }

    @Test
    void nextTransitionsToTransition() {
        controller.play();
        controller.next();
        assertEquals(PlayerState.TRANSITION, controller.getState());
    }

    @Test
    void previousTransitionsToTransition() {
        controller.play();
        controller.previous();
        assertEquals(PlayerState.TRANSITION, controller.getState());
    }

    @Test
    void toggleShuffleSwitchesState() {
        assertFalse(controller.isShuffleEnabled());
        controller.toggleShuffle();
        assertTrue(controller.isShuffleEnabled());
        controller.toggleShuffle();
        assertFalse(controller.isShuffleEnabled());
    }

    @Test
    void getSongsReturnsList() {
        List<Song> songs = controller.getSongs();
        assertNotNull(songs);
        assertEquals(2, songs.size());
    }

    @Test
    void getCurrentSongReturnsFirstSong() {
        Song song = controller.getCurrentSong();
        assertNotNull(song);
        assertEquals("A", song.title());
    }
}
