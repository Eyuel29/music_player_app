package com.joel.musicplayer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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
        allSongs = musicRepo.getAllSongs();
        likedSongs = musicRepo.getAllLikedSongs();
    }



    public void addSong(Song song){ musicRepo.addSong(song);}
    public void updateSong(Song song){ musicRepo.updateSong(song); }
    public void deleteSong(Song song){ musicRepo.deleteSong(song);}
    public void likeASong(String songId){ musicRepo.likeASong(songId); }
    void unlikeASong(String songId){ musicRepo.unlikeASong(songId); }
    public LiveData<List<Song>> getAllLikedSongs(){return this.likedSongs;}
    public LiveData<List<Song>> getRelatedSong(String songName){return musicRepo.getRelatedSong(songName); }
    public LiveData<List<Song>> getAllSongs(){ return  this.allSongs; }
    public LiveData<List<Song>> getAllSongFromAlbum(String albumId){
        return musicRepo.getAllSongFromAlbum(albumId);
    }
}