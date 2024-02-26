package com.joel.musicplayer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.joel.musicplayer.model.Artist;
import com.joel.musicplayer.model.Playlist;
import com.joel.musicplayer.model.Song;
import com.joel.musicplayer.model.Playlist;
import com.joel.musicplayer.model.Song;
import com.joel.musicplayer.repo.MusicRepo;

import java.util.List;

public class SongsViewModel extends AndroidViewModel {
    private LiveData<List<Song>> allSongs;
    private LiveData<List<Song>> likedSongs;
    
    private final MusicRepo musicRepo;
    public SongsViewModel(@NonNull Application application) {
        super(application);
        musicRepo = new MusicRepo(application);
    }



}
