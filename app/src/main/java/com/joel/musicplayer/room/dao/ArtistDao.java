package com.joel.musicplayer.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.joel.musicplayer.model.Artist;

import java.util.List;

@Dao
public interface ArtistDao {

    @Insert
    void createArtist(Artist artist);

    @Query("SELECT * from artists")
    List<Artist> getArtistsList();
}
