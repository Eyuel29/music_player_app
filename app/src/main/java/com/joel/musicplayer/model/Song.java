package com.joel.musicplayer.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "songs",
    foreignKeys = {
        @ForeignKey(entity = Artist.class,parentColumns = "artistId",
                    childColumns = "artistId", onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Album.class, parentColumns = "albumId",
                    childColumns = "albumId", onUpdate = ForeignKey.CASCADE,
                    onDelete = ForeignKey.CASCADE)
})
public class Song {
    @NonNull
    @PrimaryKey
    private String songId;
    private String artistId;
    private String title;
    private String albumId = "UNKNOWN";
    private String path;
    private int isLiked = 0;
    private long duration;

    public Song() {}

    public Song(String songId, String artistId, String title, String albumId, String path, int isLiked, long duration) {
        this.songId = songId;
        this.artistId = artistId;
        this.title = title;
        this.albumId = albumId;
        this.path = path;
        this.isLiked = isLiked;
        this.duration = duration;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int isLiked() {
        return isLiked;
    }

    public void setLiked(int liked) {
        isLiked = liked;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

}