package com.joel.musicplayer.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.joel.musicplayer.model.Album;
import com.joel.musicplayer.model.Song;

import java.util.List;
@Dao
public interface AlbumDao {

    @Insert
    void addAlbum(Album album);

    @Delete
    void deleteAlbum(Album album);

    @Update
    void updateAlbum(Album album);

    @Query("SELECT * FROM songs INNER JOIN albums ON songs.songId = albums.albumId WHERE albums.albumId = :albumId;")
    LiveData<List<Song>> getAllSongFromAlbum(String albumId);

    @Query("SELECT * FROM albums;")
    LiveData<List<Album>> getAllAlbums();
}
