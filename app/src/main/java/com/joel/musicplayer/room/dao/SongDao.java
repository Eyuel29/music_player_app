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

    @Insert
    void addAllSongs(List<Song> song);

    @Update
    void updateSong(Song song);

    @Delete
    void deleteSong(Song song);

    @Query("DELETE FROM songs WHERE songs.songId = :songId;")
    void deleteSongWithId(String songId);

    @Query("SELECT * FROM songs;")
    LiveData<List<Song>> getAllSongs();

    @Query("UPDATE songs SET isLiked = 1 WHERE songId = :songId;")
    void likeASong(String songId);

    @Query("UPDATE songs SET isLiked = 0 WHERE songId = :songId;")
    void unlikeASong(String songId);

    @Query("SELECT * FROM songs WHERE songs.isLiked = 1;")
    LiveData<List<Song>> getAllLikedSongs();

    @Query("SELECT * FROM songs WHERE songs.title like :songName;")
    LiveData<List<Song>> getRelatedSong(String songName);
}
