package com.joel.musicplayer.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.joel.musicplayer.model.Playlist;
import com.joel.musicplayer.model.Song;
import com.joel.musicplayer.model.SongPlaylistCR;

import java.util.List;
@Dao
public interface PlaylistDao {

    @Insert
    void insertPlaylist(Playlist playlist);

    @Query("SELECT * FROM playlist")
    LiveData<List<Playlist>> getAllPlaylists();

    @Update
    void updatePlaylist(Playlist playlist);

    @Delete
    void deletePlaylist(Playlist playlist);

    @Insert
    void createPlaylistSong(SongPlaylistCR songPlaylistCR);

    @Query("SELECT songs.songId, playlistId  from songs inner join song_playlist on songs.songId where song_playlist.playlistId = :playlistId;")
    List<SongPlaylistCR> getAllPlaylistSongRef(String playlistId);

    @Query("SELECT songs.songId, playlistId  from songs inner join song_playlist on songs.songId where song_playlist.playlistId = :playlistId;")
    LiveData<List<SongPlaylistCR>> getAllPlaylistSongReference(String playlistId);
}
