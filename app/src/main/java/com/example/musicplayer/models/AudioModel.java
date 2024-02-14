package com.example.musicplayer.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AudioModel implements Parcelable {
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


    public static final Creator<AudioModel> CREATOR = new Creator<AudioModel>() {
        @Override
        public AudioModel createFromParcel(Parcel in) {
            return new AudioModel(in);
        }

        @Override
        public AudioModel[] newArray(int size) {
            return new AudioModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    protected AudioModel(Parcel in) {
        artist = in.readString();
        songTitle = in.readString();
        albumName = in.readString();
        songPath = in.readString();
        songDuration = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(artist);
        parcel.writeString(albumName);
        parcel.writeString(songTitle);
        parcel.writeString(songPath);
        parcel.writeString(String.valueOf(songDuration));
    }
}
