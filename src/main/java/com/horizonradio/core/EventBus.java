package com.horizonradio.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventBus {

    private final Map<Class<?>, List<Consumer<Object>>> subscribers;

    public EventBus() {
        this.subscribers = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <T> void subscribe(Class<T> eventType, Consumer<T> listener) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>())
                   .add((Consumer<Object>) listener);
    }

    @SuppressWarnings("unchecked")
    public <T> void publish(T event) {
        List<Consumer<Object>> listeners = subscribers.get(event.getClass());
        if (listeners != null) {
            for (Consumer<Object> listener : listeners) {
                listener.accept(event);
            }
        }
    }
}
