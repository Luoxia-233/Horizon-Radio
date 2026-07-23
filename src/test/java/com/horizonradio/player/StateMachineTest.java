package com.horizonradio.player;

import com.horizonradio.core.PlayerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateMachineTest {

    private StateMachine machine;

    @BeforeEach
    void setUp() {
        machine = new StateMachine();
    }

    @Test
    void startsInIdle() {
        assertEquals(PlayerState.IDLE, machine.current());
    }

    @Test
    void idleToPlaying() {
        machine.goTo(PlayerState.PLAYING);
        assertEquals(PlayerState.PLAYING, machine.current());
    }

    @Test
    void playingToPaused() {
        machine.goTo(PlayerState.PLAYING);
        machine.goTo(PlayerState.PAUSED);
        assertEquals(PlayerState.PAUSED, machine.current());
    }

    @Test
    void pausedBackToPlaying() {
        machine.goTo(PlayerState.PLAYING);
        machine.goTo(PlayerState.PAUSED);
        machine.goTo(PlayerState.PLAYING);
        assertEquals(PlayerState.PLAYING, machine.current());
    }

    @Test
    void playingToTransition() {
        machine.goTo(PlayerState.PLAYING);
        machine.goTo(PlayerState.TRANSITION);
        assertEquals(PlayerState.TRANSITION, machine.current());
    }

    @Test
    void transitionToVoicePlaying() {
        machine.goTo(PlayerState.PLAYING);
        machine.goTo(PlayerState.TRANSITION);
        machine.goTo(PlayerState.VOICE_PLAYING);
        assertEquals(PlayerState.VOICE_PLAYING, machine.current());
    }

    @Test
    void voicePlayingToPlaying() {
        machine.goTo(PlayerState.PLAYING);
        machine.goTo(PlayerState.TRANSITION);
        machine.goTo(PlayerState.VOICE_PLAYING);
        machine.goTo(PlayerState.PLAYING);
        assertEquals(PlayerState.PLAYING, machine.current());
    }

    @Test
    void voicePlayingToPaused() {
        machine.goTo(PlayerState.PLAYING);
        machine.goTo(PlayerState.TRANSITION);
        machine.goTo(PlayerState.VOICE_PLAYING);
        machine.goTo(PlayerState.PAUSED);
        assertEquals(PlayerState.PAUSED, machine.current());
    }

    @Test
    void playingToIdle() {
        machine.goTo(PlayerState.PLAYING);
        machine.goTo(PlayerState.IDLE);
        assertEquals(PlayerState.IDLE, machine.current());
    }

    @Test
    void idleToPausedIsInvalid() {
        assertThrows(IllegalStateException.class, () -> machine.goTo(PlayerState.PAUSED));
    }

    @Test
    void transitionToPlayingIsInvalid() {
        machine.goTo(PlayerState.PLAYING);
        machine.goTo(PlayerState.TRANSITION);
        assertThrows(IllegalStateException.class, () -> machine.goTo(PlayerState.PLAYING));
    }

    @Test
    void idleToTransitionIsInvalid() {
        assertThrows(IllegalStateException.class, () -> machine.goTo(PlayerState.TRANSITION));
    }

    @Test
    void listenerNotifiedOnValidTransition() {
        PlayerState[] captured = {null, null};
        machine.onChange((oldState, newState) -> {
            captured[0] = oldState;
            captured[1] = newState;
        });

        machine.goTo(PlayerState.PLAYING);

        assertEquals(PlayerState.IDLE, captured[0]);
        assertEquals(PlayerState.PLAYING, captured[1]);
    }

    @Test
    void listenerNotNotifiedOnInvalidTransition() {
        boolean[] called = {false};
        machine.onChange((oldState, newState) -> called[0] = true);

        try {
            machine.goTo(PlayerState.PAUSED);
        } catch (IllegalStateException ignored) {
        }

        assertFalse(called[0]);
    }

    @Test
    void goToSameStateIsInvalid() {
        machine.goTo(PlayerState.PLAYING);
        assertThrows(IllegalStateException.class, () -> machine.goTo(PlayerState.PLAYING));
    }
}
