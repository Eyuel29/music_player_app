package com.joel.musicplayer;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.test.platform.app.InstrumentationRegistry;

import com.joel.musicplayer.model.Album;
import com.joel.musicplayer.model.Artist;
import com.joel.musicplayer.model.Playlist;
import com.joel.musicplayer.model.Song;
import com.joel.musicplayer.model.SongPlaylistCR;
import com.joel.musicplayer.room.SongDatabase;
import com.joel.musicplayer.room.dao.AlbumDao;
import com.joel.musicplayer.room.dao.ArtistDao;
import com.joel.musicplayer.room.dao.PlaylistDao;
import com.joel.musicplayer.room.dao.SongDao;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class RoomTest {

    private ArtistDao artistDao;
    private AlbumDao albumDao;
    private SongDao songDao;
    private PlaylistDao playlistDao;
    private Context context = InstrumentationRegistry.getInstrumentation().getContext();
    private SongDatabase roomDb;


    @Before
    public void setup(){
        roomDb = Room.inMemoryDatabaseBuilder(
                context,
                SongDatabase.class
        ).build();
        artistDao = roomDb.getArtistDao();
        albumDao = roomDb.getAlbumDao();
        songDao = roomDb.getSongDao();
        playlistDao = roomDb.getPlaylistDao();
        setAndGetArtist();
        setAndGetAlbum();
        setAndGetSong();
    }


    @Test
    public void setAndGetArtist(){
                Artist artist = new Artist(
                        "MULATU",
                        "Mulatu Astatqe"
                );

                Artist artist2 = new Artist(
                        "TWXO",
                        "Abel Tesfaye"
                );
                artistDao.createArtist(artist);
                artistDao.createArtist(artist2);
                List<Artist> allArtist = artistDao.getArtistsList();
                assertEquals(allArtist.size(),2);
    }

    @Test
    public void setAndGetAlbum(){

        Album album1 = new Album(
            "SE",
            "Sketches of Ethiopia",
            "MULATU",
            "12,12,2018"
        );

        Album album2 = new Album(
                "BBTM",
                "Beauty behind the madness",
                "TWXO",
                "12,12,2016"
        );

        albumDao.addAlbum(album1);
        albumDao.addAlbum(album2);
        List<Album> allAlbums = albumDao.getAllAlbumsList();
        assertEquals(2,allAlbums.size());
    }

    @Test
    public void setAndGetSong(){

        Song song1 = new Song(
                "DARKTIMES",
                "TWXO",
                "Dark times",
                "BBTM",
                "/BBTM/TWXO/darktimes.mp3",
                1,
                4500
        );

        Song song2 = new Song(
                "THEHILLS",
                "TWXO",
                "The Hills",
                "BBTM",
                "/BBTM/TWXO/thehills.mp3",
                1,
                4500
        );

        songDao.addSong(song1);
        songDao.addSong(song2);
        List<Song> allSongs = songDao.getAllSongsAsList();
        assertEquals(2,allSongs.size());
    }

    @Test
    public void setAndGetPlaylist(){
        Playlist playlist1 = new Playlist(
                "RNB",
                "Rgb songs",
                "12,33,2024",
                2,
                10000
        );

        playlistDao.insertPlaylist(playlist1);
        playlistDao.createPlaylistSong(new SongPlaylistCR("DARKTIMES","RNB"));
        playlistDao.createPlaylistSong(new SongPlaylistCR("THEHILLS","RNB"));
        List<SongPlaylistCR> allRef = playlistDao.getAllPlaylistSongRef("RNB");
        assertEquals(2 ,allRef.size());
    }


    @Test
    public void deleteSong(){

    }


    @After
    public void tearDown(){
        roomDb.close();
    }

}
