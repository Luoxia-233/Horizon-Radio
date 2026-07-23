package com.horizonradio.player;

import com.horizonradio.core.AudioFormat;
import com.horizonradio.core.Song;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MusicLibrary {

    private final Path directory;
    private List<Song> songs;

    public MusicLibrary(Path directory) {
        this.directory = directory;
        this.songs = new ArrayList<>();
    }

    public void scan() {
        List<Song> result = new ArrayList<>();
        try (Stream<Path> files = Files.walk(directory)) {
            files.filter(Files::isRegularFile)
                 .filter(AudioFormat::isAudioFile)
                 .forEach(path -> {
                     Song song = Song.fromFile(path);
                     result.add(song);
                 });
        } catch (IOException e) {
            throw new RuntimeException("Failed to scan directory: " + directory, e);
        }
        this.songs = result;
    }

    public List<Song> getSongs() {
        return List.copyOf(songs);
    }

    public Path getDirectory() {
        return directory;
    }
}
