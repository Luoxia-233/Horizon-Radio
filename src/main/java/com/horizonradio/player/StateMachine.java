package com.horizonradio.player;

import com.horizonradio.core.PlayerState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class StateMachine {

    private static final Map<PlayerState, Set<PlayerState>> VALID_TRANSITIONS = Map.of(
            PlayerState.IDLE,           Set.of(PlayerState.PLAYING),
            PlayerState.PLAYING,        Set.of(PlayerState.PAUSED, PlayerState.TRANSITION, PlayerState.IDLE),
            PlayerState.TRANSITION,     Set.of(PlayerState.VOICE_PLAYING),
            PlayerState.VOICE_PLAYING,  Set.of(PlayerState.PAUSED, PlayerState.PLAYING),
            PlayerState.PAUSED,         Set.of(PlayerState.PLAYING)
    );

    private PlayerState current;
    private final List<BiConsumer<PlayerState, PlayerState>> listeners;

    public StateMachine() {
        this.current = PlayerState.IDLE;
        this.listeners = new ArrayList<>();
    }

    public void goTo(PlayerState target) {
        Set<PlayerState> allowed = VALID_TRANSITIONS.get(current);
        if (allowed == null || !allowed.contains(target)) {
            throw new IllegalStateException(
                    "Invalid transition: " + current + " -> " + target
            );
        }
        PlayerState old = current;
        current = target;
        for (BiConsumer<PlayerState, PlayerState> listener : listeners) {
            listener.accept(old, current);
        }
    }

    public PlayerState current() {
        return current;
    }

    public void onChange(BiConsumer<PlayerState, PlayerState> listener) {
        listeners.add(listener);
    }
}
