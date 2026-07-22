package com.horizonradio.player;

import com.horizonradio.core.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaylistQueue {

    private final List<Song> songs;
    private int currentIndex;
    private List<Integer> shuffledOrder;
    private boolean shuffle;

    public PlaylistQueue(List<Song> songs) {
        this.songs = new ArrayList<>(songs);
        this.currentIndex = -1;
        this.shuffle = false;
    }

    public Song current() {
        if (songs.isEmpty() || currentIndex < 0) {
            return null;
        }
        return songs.get(resolveOrder().get(currentIndex));
    }

    public Song next() {
        if (songs.isEmpty()) {
            return null;
        }
        currentIndex++;
        if (currentIndex >= resolveOrder().size()) {
            if (shuffle) {
                shuffledOrder = buildShuffledIndices(null);
            }
            currentIndex = 0;
        }
        return songs.get(resolveOrder().get(currentIndex));
    }

    public Song previous() {
        if (songs.isEmpty()) {
            return null;
        }
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = resolveOrder().size() - 1;
        }
        return songs.get(resolveOrder().get(currentIndex));
    }

    public void setShuffle(boolean enable) {
        if (enable == this.shuffle) {
            return;
        }
        Song currentSong = current();
        this.shuffle = enable;
        if (enable) {
            Integer keepIndex = currentSong != null ? songs.indexOf(currentSong) : null;
            shuffledOrder = buildShuffledIndices(keepIndex);
            currentIndex = keepIndex != null ? 0 : -1;
        } else {
            currentIndex = currentSong != null ? songs.indexOf(currentSong) : -1;
            shuffledOrder = null;
        }
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public int size() {
        return songs.size();
    }

    public void setSongs(List<Song> newSongs) {
        songs.clear();
        songs.addAll(newSongs);
        currentIndex = -1;
        shuffledOrder = null;
        shuffle = false;
    }

    private List<Integer> resolveOrder() {
        if (shuffledOrder != null) {
            return shuffledOrder;
        }
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < songs.size(); i++) {
            order.add(i);
        }
        return order;
    }

    private List<Integer> buildShuffledIndices(Integer keepAtStart) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < songs.size(); i++) {
            if (keepAtStart == null || i != keepAtStart) {
                indices.add(i);
            }
        }
        Collections.shuffle(indices);
        if (keepAtStart != null) {
            indices.add(0, keepAtStart);
        }
        return indices;
    }
}
