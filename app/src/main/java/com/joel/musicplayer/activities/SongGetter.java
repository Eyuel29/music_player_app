package com.joel.musicplayer.activities;

import com.joel.musicplayer.model.audio.AudioModel;

import java.util.List;

public interface SongGetter {
    List<AudioModel> getAllSongs();
}
