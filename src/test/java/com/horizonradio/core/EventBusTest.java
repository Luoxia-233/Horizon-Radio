package com.horizonradio.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class EventBusTest {

    private EventBus bus;

    @BeforeEach
    void setUp() {
        bus = new EventBus();
    }

    @Test
    void subscriberReceivesPublishedEvent() {
        List<SongChanged> received = new ArrayList<>();
        bus.subscribe(SongChanged.class, received::add);

        Song song = new Song("Test", "Artist", Path.of("/test.mp3"), 0L);
        bus.publish(new SongChanged(song, false));

        assertEquals(1, received.size());
        assertEquals("Test", received.get(0).song().title());
    }

    @Test
    void subscriberDoesNotReceiveOtherEventTypes() {
        List<SongChanged> received = new ArrayList<>();
        bus.subscribe(SongChanged.class, received::add);

        bus.publish(new PlayPause(true));

        assertTrue(received.isEmpty());
    }

    @Test
    void multipleSubscribersReceiveSameEvent() {
        AtomicInteger count1 = new AtomicInteger(0);
        AtomicInteger count2 = new AtomicInteger(0);

        bus.subscribe(PlaylistStarted.class, e -> count1.incrementAndGet());
        bus.subscribe(PlaylistStarted.class, e -> count2.incrementAndGet());

        bus.publish(new PlaylistStarted());

        assertEquals(1, count1.get());
        assertEquals(1, count2.get());
    }

    @Test
    void publishWithoutSubscribersDoesNotThrow() {
        assertDoesNotThrow(() -> bus.publish(new PlaylistEnded()));
    }

    @Test
    void publishSongChangedWithManualFlag() {
        List<SongChanged> received = new ArrayList<>();
        bus.subscribe(SongChanged.class, received::add);

        Song song = new Song("A", "Artist", Path.of("/a.mp3"), 0L);
        bus.publish(new SongChanged(song, true));

        assertTrue(received.get(0).manual());
    }

    @Test
    void publishPlayPauseEvent() {
        List<PlayPause> received = new ArrayList<>();
        bus.subscribe(PlayPause.class, received::add);

        bus.publish(new PlayPause(false));
        bus.publish(new PlayPause(true));

        assertEquals(2, received.size());
        assertFalse(received.get(0).paused());
        assertTrue(received.get(1).paused());
    }
}
