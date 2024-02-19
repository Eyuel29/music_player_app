package com.joel.musicplayer.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.joel.musicplayer.model.Album;
import com.joel.musicplayer.model.Artist;
import com.joel.musicplayer.model.Playlist;
import com.joel.musicplayer.model.Song;
import com.joel.musicplayer.model.SongPlaylistCR;
import com.joel.musicplayer.room.dao.AlbumDao;
import com.joel.musicplayer.room.dao.ArtistDao;
import com.joel.musicplayer.room.dao.PlaylistDao;
import com.joel.musicplayer.room.dao.SongDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Song.class, Playlist.class, Artist.class, Album.class, SongPlaylistCR.class}, version = 1, exportSchema = false)
public abstract class SongDatabase extends RoomDatabase {
    private static volatile SongDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract SongDao getSongDao();
    public abstract AlbumDao getAlbumDao();
    public abstract ArtistDao getArtistDao();
    public abstract PlaylistDao getPlaylistDao();

    public static SongDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (SongDatabase.class){
                INSTANCE = Room.databaseBuilder
                    (context,SongDatabase.class,"songDatabase")
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Log.i("DATABASE_STATUS","Database created successful!");
                        }
                    }).build();
            }
        }
        return INSTANCE;
    }
}