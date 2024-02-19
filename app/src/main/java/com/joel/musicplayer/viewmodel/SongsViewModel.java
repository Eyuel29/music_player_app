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

import java.util.List;

public class SongsViewModel extends AndroidViewModel {
    private LiveData<List<Song>> allObservedSongs;

    public SongsViewModel(@NonNull Application application) {
        super(application);

    }

    LiveData<List<Song>> getAllObservedSongs(){
        return this.allObservedSongs;
    }

    LiveData<List<Playlist>> getAllPlaylists(){
        return null;
    }

    LiveData<List<Song>> getArtistsSongs(String artistName){
        return allObservedSongs;
    }

    LiveData<List<Artist>> getAllArtists(){
        return null;
    }

    LiveData<List<Song>> getLikedSongs (){
        return allObservedSongs;
    }

    void createPlaylist(Playlist playlist){

    }

    void addSongToPlaylist(List<Song> songs, Playlist playlist){

    }

    void likeSong (Song song){

    }

    void unlikeSong (Song song){

    }
}
