package com.horizonradio.player;

import com.horizonradio.core.Song;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MusicLibraryTest {

    @TempDir
    Path tempDir;

    @Test
    void scanFindsAudioFiles() throws Exception {
        Files.createFile(tempDir.resolve("song1.mp3"));
        Files.createFile(tempDir.resolve("song2.wav"));
        Files.createFile(tempDir.resolve("readme.txt"));

        MusicLibrary library = new MusicLibrary(tempDir);
        library.scan();

        List<Song> songs = library.getSongs();
        assertEquals(2, songs.size());
    }

    @Test
    void scanRecursivelyIntoSubdirectories() throws Exception {
        Path subDir = Files.createDirectory(tempDir.resolve("sub"));
        Files.createFile(tempDir.resolve("song1.mp3"));
        Files.createFile(subDir.resolve("song2.mp3"));

        MusicLibrary library = new MusicLibrary(tempDir);
        library.scan();

        assertEquals(2, library.getSongs().size());
    }

    @Test
    void scanFiltersNonAudioFiles() throws Exception {
        Files.createFile(tempDir.resolve("song.mp3"));
        Files.createFile(tempDir.resolve("cover.jpg"));
        Files.createFile(tempDir.resolve("lyrics.txt"));
        Files.createFile(tempDir.resolve("notes.md"));

        MusicLibrary library = new MusicLibrary(tempDir);
        library.scan();

        List<Song> songs = library.getSongs();
        assertEquals(1, songs.size());
        assertTrue(songs.get(0).title().startsWith("song"));
    }

    @Test
    void scanEmptyDirectoryReturnsEmptyList() {
        MusicLibrary library = new MusicLibrary(tempDir);
        library.scan();

        assertTrue(library.getSongs().isEmpty());
    }

    @Test
    void getSongsReturnsUnmodifiableList() throws Exception {
        Files.createFile(tempDir.resolve("song.mp3"));
        MusicLibrary library = new MusicLibrary(tempDir);
        library.scan();

        List<Song> songs = library.getSongs();
        assertThrows(UnsupportedOperationException.class, () -> songs.add(null));
    }

    @Test
    void getDirectoryReturnsBoundDirectory() {
        MusicLibrary library = new MusicLibrary(tempDir);
        assertEquals(tempDir, library.getDirectory());
    }
}
