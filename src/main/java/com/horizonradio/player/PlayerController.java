package com.horizonradio.player;

import com.horizonradio.core.PlayerState;
import com.horizonradio.core.Song;

import java.util.List;

public interface PlayerController {

    void play();
    void pause();
    void next();
    void previous();
    void toggleShuffle();
    boolean isShuffleEnabled();
    PlayerState getState();
    List<Song> getSongs();
    Song getCurrentSong();
}
