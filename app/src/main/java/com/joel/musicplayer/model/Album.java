package com.joel.musicplayer.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "albums",
        foreignKeys = @ForeignKey(
        entity = Artist.class,
        parentColumns = "artistId",
        childColumns = "artistId",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )
)
public class Album {
    @NonNull
    @PrimaryKey
    private String albumId;
    private String albumTitle;
    private String artistId;
    private String releaseDate;

    public Album(String albumId, String albumTitle, String artistId, String releaseDate) {
        this.albumId = albumId;
        this.albumTitle = albumTitle;
        this.artistId = artistId;
        this.releaseDate = releaseDate;
    }

    public Album() {}

    public String getAlbumId() {
        return albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}