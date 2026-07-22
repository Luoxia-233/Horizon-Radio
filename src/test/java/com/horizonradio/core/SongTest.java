package com.horizonradio.core;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SongTest {

    @Test
    void fromFileExtractsTitleFromFilename() {
        Song song = Song.fromFile(Path.of("C:\\Music\\Test Song.mp3"));

        assertEquals("Test Song", song.title());
        assertEquals("Unknown Artist", song.artist());
        assertEquals(Path.of("C:\\Music\\Test Song.mp3"), song.filePath());
        assertEquals(0L, song.durationMs());
    }

    @Test
    void recordsWithSameValuesAreEqual() {
        Song a = new Song("Title", "Artist", Path.of("/path/to/file.mp3"), 210_000L);
        Song b = new Song("Title", "Artist", Path.of("/path/to/file.mp3"), 210_000L);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void recordsWithDifferentValuesAreNotEqual() {
        Song a = new Song("Title A", "Artist", Path.of("/path.mp3"), 0L);
        Song b = new Song("Title B", "Artist", Path.of("/path.mp3"), 0L);

        assertNotEquals(a, b);
    }
}
