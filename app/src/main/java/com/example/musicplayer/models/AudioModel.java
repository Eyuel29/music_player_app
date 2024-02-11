package com.example.musicplayer.models;

public class AudioModel {
    private String artist,songTitle, albumName, songPath;
    private int songDuration;


    public AudioModel() {
    }

    public AudioModel(String artist, String songTitle, String albumName, String songPath, int songDuration) {
        this.artist = artist;
        this.songTitle = songTitle;
        this.albumName = albumName;
        this.songPath = songPath;
        this.songDuration = songDuration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public int getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(int songDuration) {
        this.songDuration = songDuration;
    }
}
