package com.joel.musicplayer.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.joel.musicplayer.model.Song;

import java.util.List;

@Dao
public interface SongDao {
    @Insert
    void addSong(Song song);

    @Update
    void updateSong(Song song);

    @Delete
    void deleteSong(Song song);

    @Query("SELECT * FROM songs;")
    LiveData<List<Song>> getAllSongs();

    @Query("UPDATE songs SET isLiked = 1 WHERE songId = :songId;")
    void likeASong(String songId);

    @Query("SELECT * FROM songs WHERE songs.isLiked = 1;")
    LiveData<List<Song>> getAllLikedSongs();
}
