package com.joel.musicplayer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.joel.musicplayer.model.Artist;
import com.joel.musicplayer.repo.MusicRepo;

import java.util.List;

public class ArtistViewModel extends AndroidViewModel {

    private MusicRepo musicRepo;
    public ArtistViewModel(@NonNull Application application) {
        super(application);
        musicRepo = new MusicRepo(application);
    }

    public LiveData<List<Artist>> getAllArtists(){
        return musicRepo.getAllArtists();
    }

    public LiveData<Artist> getArtistsWithId(String albumId){
        return musicRepo.getArtistsWithId(albumId);
    }


}
