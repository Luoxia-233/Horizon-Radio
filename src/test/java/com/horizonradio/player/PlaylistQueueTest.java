package com.horizonradio.player;

import com.horizonradio.core.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistQueueTest {

    private List<Song> threeSongs;

    @BeforeEach
    void setUp() {
        threeSongs = List.of(
                new Song("A", "Artist", Path.of("/a.mp3"), 0L),
                new Song("B", "Artist", Path.of("/b.mp3"), 0L),
                new Song("C", "Artist", Path.of("/c.mp3"), 0L)
        );
    }

    @Test
    void currentReturnsNullBeforeAnyNavigation() {
        PlaylistQueue queue = new PlaylistQueue(threeSongs);
        assertNull(queue.current());
    }

    @Test
    void nextReturnsSongsInSequence() {
        PlaylistQueue queue = new PlaylistQueue(threeSongs);
        assertEquals("A", queue.next().title());
        assertEquals("B", queue.next().title());
        assertEquals("C", queue.next().title());
    }

    @Test
    void nextWrapsToStart() {
        PlaylistQueue queue = new PlaylistQueue(threeSongs);
        queue.next(); // A
        queue.next(); // B
        queue.next(); // C
        assertEquals("A", queue.next().title());
    }

    @Test
    void previousReturnsLastSongWhenAtStart() {
        PlaylistQueue queue = new PlaylistQueue(threeSongs);
        queue.next(); // current = A
        assertEquals("C", queue.previous().title());
    }

    @Test
    void previousGoesBackward() {
        PlaylistQueue queue = new PlaylistQueue(threeSongs);
        queue.next(); // A
        queue.next(); // B
        assertEquals("A", queue.previous().title());
    }

    @Test
    void shuffleProducesAllSongs() {
        PlaylistQueue queue = new PlaylistQueue(threeSongs);
        queue.setShuffle(true);

        HashSet<String> titles = new HashSet<>();
        titles.add(queue.next().title());
        titles.add(queue.next().title());
        titles.add(queue.next().title());

        assertEquals(3, titles.size());
        assertTrue(titles.contains("A"));
        assertTrue(titles.contains("B"));
        assertTrue(titles.contains("C"));
    }

    @Test
    void shuffleExhaustedReshuffles() {
        PlaylistQueue queue = new PlaylistQueue(threeSongs);
        queue.setShuffle(true);
        queue.next(); // song 1
        queue.next(); // song 2
        queue.next(); // song 3
        // should wrap to fresh shuffle
        Song song = queue.next();
        assertNotNull(song);
    }

    @Test
    void togglingShuffleOnPreservesCurrentSong() {
        PlaylistQueue queue = new PlaylistQueue(threeSongs);
        queue.next(); // A
        queue.next(); // B = current

        queue.setShuffle(true);
        assertEquals("B", queue.current().title());
    }

    @Test
    void togglingShuffleOffPreservesCurrentSong() {
        PlaylistQueue queue = new PlaylistQueue(threeSongs);
        queue.setShuffle(true);
        queue.next(); // random song becomes current

        Song current = queue.current();
        queue.setShuffle(false);
        assertEquals(current, queue.current());
    }

    @Test
    void togglingSameModeDoesNothing() {
        PlaylistQueue queue = new PlaylistQueue(threeSongs);
        queue.setShuffle(true);
        assertTrue(queue.isShuffle());
        queue.setShuffle(true);
        assertTrue(queue.isShuffle());
    }

    @Test
    void emptyListNextReturnsNull() {
        PlaylistQueue queue = new PlaylistQueue(List.of());
        assertNull(queue.next());
    }

    @Test
    void emptyListPreviousReturnsNull() {
        PlaylistQueue queue = new PlaylistQueue(List.of());
        assertNull(queue.previous());
    }

    @Test
    void emptyListCurrentReturnsNull() {
        PlaylistQueue queue = new PlaylistQueue(List.of());
        assertNull(queue.current());
    }

    @Test
    void singleSongNextAlwaysReturnsSameSong() {
        List<Song> single = List.of(new Song("Only", "Artist", Path.of("/only.mp3"), 0L));
        PlaylistQueue queue = new PlaylistQueue(single);
        assertEquals("Only", queue.next().title());
        assertEquals("Only", queue.next().title());
    }

    @Test
    void sizeReturnsSongCount() {
        PlaylistQueue queue = new PlaylistQueue(threeSongs);
        assertEquals(3, queue.size());
    }

    @Test
    void setSongsReplacesListAndResetsState() {
        PlaylistQueue queue = new PlaylistQueue(threeSongs);
        queue.setShuffle(true);
        queue.next();

        List<Song> newSongs = List.of(new Song("X", "Artist", Path.of("/x.mp3"), 0L));
        queue.setSongs(newSongs);

        assertEquals(1, queue.size());
        assertFalse(queue.isShuffle());
        assertNull(queue.current());
    }
}
