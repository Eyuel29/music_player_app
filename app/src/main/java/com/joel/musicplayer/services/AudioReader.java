package com.joel.musicplayer.services;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.joel.musicplayer.model.Album;
import com.joel.musicplayer.model.Artist;
import com.joel.musicplayer.model.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class AudioReader {
    private final Context context;
    public AudioReader(Context context) {
        this.context = context;
    }

    public List<Artist> getAllArtists() throws IOException{
        List<Artist> allArtist = new ArrayList<>();
        Uri artistUrl = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ARTIST,
        };
        Cursor cursor = context.getContentResolver().query(artistUrl,projection,null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                Artist newArtist = new Artist(cursor.getString(0), cursor.getString(1) );
                boolean repeated = false;

                for (Artist artist: allArtist) {
                    if (newArtist.getArtistId().equals(artist.getArtistId())){
                        repeated = true;
                    }
                }
                if (!repeated){
                    allArtist.add(newArtist);
                }
            }
            cursor.close();
        }
        return allArtist;
    }

    public List<Song> readAllSongs() throws IOException {

        List<Song> allSongs = new ArrayList<>();
        Uri artistUrl = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = new String[]{
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION
        };

        Cursor cursor = context.getContentResolver().query(artistUrl,projection,null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                Double durationDouble  = Double.valueOf(cursor.getString(5));
                allSongs.add(
                        new Song(
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                0,
                                durationDouble.longValue()
                        )
                );
            }
            cursor.close();
        }
        return allSongs;
    }

    public List<Album> readAlbums() throws IOException{
        List<Album> allAlbums = new ArrayList<>();
        Uri artistUrl = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = new String[]{
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.DATE_ADDED
        };

        Cursor cursor = context.getContentResolver().query(
                artistUrl,
                projection,
                null,
                null,
                null
        );
        while (cursor.moveToNext()){
            Album newAlbum = new Album(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            boolean repeated = false;
            for (Album album: allAlbums) {
                if (album.getAlbumId().equals(newAlbum.getAlbumId())){
                    repeated = true;
                }
            }
            if (!repeated){
                allAlbums.add(newAlbum);
            }
        }
        cursor.close();
        return allAlbums;
    }
}