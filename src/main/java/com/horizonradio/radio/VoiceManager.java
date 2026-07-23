package com.horizonradio.radio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class VoiceManager {

    private static final Set<String> AUDIO_EXTENSIONS = Set.of(
            ".mp3", ".wav", ".flac", ".aac", ".ogg", ".wma", ".m4a"
    );

    private final Path directory;
    private List<Path> voiceFiles;

    public VoiceManager(Path directory) {
        this.directory = directory;
        this.voiceFiles = new ArrayList<>();
    }

    public void scan() {
        List<Path> result = new ArrayList<>();
        try (Stream<Path> files = Files.walk(directory)) {
            files.filter(Files::isRegularFile)
                 .filter(this::isAudioFile)
                 .forEach(result::add);
        } catch (IOException e) {
            throw new RuntimeException("Failed to scan voice directory: " + directory, e);
        }
        this.voiceFiles = result;
    }

    public Path randomVoice() {
        if (voiceFiles.isEmpty()) {
            return null;
        }
        int index = (int) (Math.random() * voiceFiles.size());
        return voiceFiles.get(index);
    }

    public int count() {
        return voiceFiles.size();
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
