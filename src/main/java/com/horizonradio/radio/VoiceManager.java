package com.horizonradio.radio;

import com.horizonradio.core.AudioFormat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class VoiceManager {

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
                 .filter(AudioFormat::isAudioFile)
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
        int index = ThreadLocalRandom.current().nextInt(voiceFiles.size());
        return voiceFiles.get(index);
    }

    public int count() {
        return voiceFiles.size();
    }
}
