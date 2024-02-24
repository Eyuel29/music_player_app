package com.joel.musicplayer.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist")
public class Playlist {
    @NonNull
    @PrimaryKey
    private String playlistId;
    private String playlistName;
    private String createdAt;
    private int numberOfSongs = 0;
    private int totalDuration = 0;

    public Playlist() {}
    public Playlist(@NonNull String playlistId, String playlistName, String createdAt, int numberOfSongs, int totalDuration) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.createdAt = createdAt;
        this.numberOfSongs = numberOfSongs;
        this.totalDuration = totalDuration;
    }

    public String getPlaylistId() {
        return playlistId;
    }
    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
    public String getPlaylistName() {
        return playlistName;
    }
    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public int getNumberOfSongs() {
        return numberOfSongs;
    }
    public void setNumberOfSongs(int numberOfSongs) {
        this.numberOfSongs = numberOfSongs;
    }
    public int getTotalDuration() {
        return totalDuration;
    }
    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }
}