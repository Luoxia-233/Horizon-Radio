package com.horizonradio.core;

import java.nio.file.Path;
import java.util.Set;

public final class AudioFormat {

    private static final Set<String> AUDIO_EXTENSIONS = Set.of(
            ".mp3", ".wav", ".flac", ".aac", ".ogg", ".wma", ".m4a"
    );

    private AudioFormat() {
    }

    public static boolean isAudioFile(Path path) {
        String name = path.getFileName().toString().toLowerCase();
        for (String ext : AUDIO_EXTENSIONS) {
            if (name.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}
