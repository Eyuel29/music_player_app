package com.joel.musicplayer.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity( tableName = "artists")
public class Artist {
    @NonNull
    @PrimaryKey
    private String artistId;
    private String artistName;

    public Artist() {}

    public Artist(String artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public void setArtistId(String artistId) {this.artistId = artistId; }
    public void setArtistName(String artistName) { this.artistName = artistName; }
    public String getArtistId() {  return artistId; }
    public String getArtistName() {  return artistName;  }
}