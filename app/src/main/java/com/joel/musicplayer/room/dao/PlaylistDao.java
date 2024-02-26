package com.joel.musicplayer.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.joel.musicplayer.model.Playlist;
import com.joel.musicplayer.model.SongPlaylistCR;

import java.util.List;
@Dao
public interface PlaylistDao {

    @Insert
    void insertPlaylist(Playlist playlist);

    @Query("SELECT * FROM playlist")
    LiveData<List<Playlist>> getAllPlaylists();

    @Query("SELECT * FROM playlist")
    List<Playlist> getAllPlaylistsList();

    @Update
    void updatePlaylist(Playlist playlist);

    @Delete
    void deletePlaylist(Playlist playlist);

    @Insert
    void createPlaylistSong(SongPlaylistCR songPlaylistCR);

    @Delete
    void removePlaylistSong(SongPlaylistCR songPlaylistCR);

    @Query("SELECT songs.songId, song_playlist.playlistId FROM songs INNER JOIN song_playlist ON songs.songId = song_playlist.songId WHERE song_playlist.playlistId = :playlistId;")
    LiveData<List<SongPlaylistCR>> getAllPlaylistSongReference(String playlistId);
}
