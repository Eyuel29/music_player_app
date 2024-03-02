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

    @Insert
    void addAllArtists(List<Artist> allArtists);

    @Query("SELECT * from artists")
    LiveData<List<Artist>> getArtistsList();

    @Query("SELECT * from artists WHERE artistId = :albumId;")
    LiveData<Artist> getArtistsWithId(String albumId);

    @Delete
    void deleteArtist(Artist artist);
}
