package com.joel.musicplayer.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.joel.musicplayer.model.Artist;

import java.util.List;

@Dao
public interface ArtistDao {

    @Insert
    void createArtist(Artist artist);

    @Query("SELECT * from artists")
    LiveData<List<Artist>> getArtistsList();

    @Delete
    void deleteArtist(Artist artist);
}
