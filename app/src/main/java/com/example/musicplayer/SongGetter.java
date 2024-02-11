package com.example.musicplayer;

import com.example.musicplayer.models.AudioModel;

import java.util.List;

public interface SongGetter {
    List<AudioModel> getAllSongs();
}
