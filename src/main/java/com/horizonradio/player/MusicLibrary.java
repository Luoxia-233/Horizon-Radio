package com.horizonradio.player;

import com.horizonradio.core.Song;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class MusicLibrary {

    private static final Set<String> AUDIO_EXTENSIONS = Set.of(
            ".mp3", ".wav", ".flac", ".aac", ".ogg", ".wma", ".m4a"
    );

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
                 .filter(this::isAudioFile)
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

    private boolean isAudioFile(Path path) {
        String name = path.getFileName().toString().toLowerCase();
        for (String ext : AUDIO_EXTENSIONS) {
            if (name.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}
