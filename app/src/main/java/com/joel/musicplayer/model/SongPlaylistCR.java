package com.joel.musicplayer.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
    tableName = "song_playlist",
    primaryKeys = {"songId","playlistId"},
    foreignKeys = {
        @ForeignKey(
            entity = Song.class,
            parentColumns = "songId",
            childColumns = "songId",
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Playlist.class,
                parentColumns = "playlistId",
                childColumns = "playlistId",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE
        )
    }
)
public class SongPlaylistCR {
    @NonNull
    private String songId;
    @NonNull
    private String playlistId;

    public SongPlaylistCR() {}

    public SongPlaylistCR(String songId, String playlistId) {
        this.songId = songId;
        this.playlistId = playlistId;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
}
