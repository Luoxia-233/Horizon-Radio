package com.horizonradio.core;

import java.nio.file.Path;

public record Song(String title, String artist, Path filePath, long durationMs) {

    public static Song fromFile(Path path) {
        String fileName = path.getFileName().toString();
        String title = stripExtension(fileName);
        return new Song(title, "Unknown Artist", path, 0L);
    }

    private static String stripExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(0, dotIndex);
        }
        return fileName;
    }
}
