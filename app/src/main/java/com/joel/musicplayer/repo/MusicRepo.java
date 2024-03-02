package com.joel.musicplayer.repo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
import com.joel.musicplayer.utis.CustomLogger;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MusicRepo {
    private PlaylistDao playlistDao;
    private ArtistDao artistDao;
    private AlbumDao albumDao;
    private SongDao songDao;

    public MusicRepo(Application application) {
        SongDatabase db = SongDatabase.getDatabase(application);
        playlistDao = db.getPlaylistDao();
        artistDao = db.getArtistDao();
        albumDao = db.getAlbumDao();
        songDao = db.getSongDao();
    }

    @WorkerThread
    public void addArtist(Artist artist){
        SongDatabase.databaseExecutor.execute(() ->{
            artistDao.createArtist(artist);
        });
    }

    @WorkerThread
    public void addAllArtists(List<Artist> allArtists){
        try {
            Future <?> taskAddAllArtists = SongDatabase.databaseExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    artistDao.addAllArtists(allArtists);
                    Log.i("PROCESS_SONG","ADDING ARTIST....");
                }
            });
            taskAddAllArtists.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @WorkerThread
    public void deleteArtist(Artist artist){
        SongDatabase.databaseExecutor.execute(() ->{
            artistDao.deleteArtist(artist);
        });
    }

    @WorkerThread
    public void addAlbum(Album album){
        SongDatabase.databaseExecutor.execute(() ->{
            albumDao.addAlbum(album);
        });
    }

    @WorkerThread
    public void addAllAlbums(List<Album> allAlbums){
        try {
        Future <?> taskAddAllAlbums = SongDatabase.databaseExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    Log.i("PROCESS_SONG","ADDING ALBUM....");
                    albumDao.addAllAlbums(allAlbums);
                }
            });
            taskAddAllAlbums.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @WorkerThread
    public void deleteAlbum(Album album){
        SongDatabase.databaseExecutor.execute(() ->{
            albumDao.deleteAlbum(album);
        });
    }

    @WorkerThread
    public void updateAlbum(Album album){
        SongDatabase.databaseExecutor.execute(() ->{
            albumDao.updateAlbum(album);
        });
    }

    @WorkerThread
    public void addSong(Song song){
        SongDatabase.databaseExecutor.execute(() ->{
            songDao.addSong(song);
        });
    }

    @WorkerThread
    public void addAllSongs(List<Song> allSongs){
        try {
            Future <?> taskAddAllSongs = SongDatabase.databaseExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    Log.i("PROCESS_SONG","ADDING SONGS....");
                    songDao.addAllSongs(allSongs);
                }
            });
            taskAddAllSongs.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @WorkerThread
    public void updateSong(Song song){
        SongDatabase.databaseExecutor.execute(() ->{
            songDao.updateSong(song);
        });
    }
    @WorkerThread
    public void deleteSong(Song song){
        SongDatabase.databaseExecutor.execute(() ->{
            songDao.deleteSong(song);
        });
    }

    @WorkerThread
    public void likeASong(String songId){
        SongDatabase.databaseExecutor.execute(() ->{
            songDao.likeASong(songId);
        });
    }

    @WorkerThread
    public void unlikeASong(String songId){
        SongDatabase.databaseExecutor.execute(() ->{
            songDao.unlikeASong(songId);
        });
    }

    @WorkerThread
    public void insertPlaylist(Playlist playlist){
        SongDatabase.databaseExecutor.execute(() ->{
            playlistDao.insertPlaylist(playlist);
        });
    }
    @WorkerThread
    public void updatePlaylist(Playlist playlist){
        SongDatabase.databaseExecutor.execute(() ->{
            playlistDao.updatePlaylist(playlist);
        });
    }
    @WorkerThread
    public void deletePlaylist(Playlist playlist){
        SongDatabase.databaseExecutor.execute(() ->{
            playlistDao.deletePlaylist(playlist);
        });
    }

    @WorkerThread
    void createPlaylistSong(SongPlaylistCR songPlaylistCR){
        SongDatabase.databaseExecutor.execute(() ->{
            playlistDao.createPlaylistSong(songPlaylistCR);
        });
    }
    @WorkerThread
    void removePlaylistSong(SongPlaylistCR songPlaylistCR){
        SongDatabase.databaseExecutor.execute(() ->{
            playlistDao.removePlaylistSong(songPlaylistCR);
        });
    }

    public LiveData<List<Song>> getAllLikedSongs(){return songDao.getAllLikedSongs(); }
    public LiveData<List<Song>> getRelatedSong(String songName){return songDao.getRelatedSong(songName); }
    public LiveData<List<Song>> getAllSongs(){
            return songDao.getAllSongs();
    }
    public LiveData<List<Song>> getAllSongFromAlbum(String albumId){
        return albumDao.getAllSongFromAlbum(albumId);
    }
    public LiveData<List<Album>> getAllAlbums(){
        return albumDao.getAllAlbums();
    }
    public LiveData<Artist> getArtistsWithId(String albumId){
        return artistDao.getArtistsWithId(albumId);
    }
    public LiveData<List<Artist>> getAllArtists(){ return artistDao.getArtistsList(); }
    public LiveData<List<Playlist>> getAllPlaylists(){
        return playlistDao.getAllPlaylists();
    }
    public LiveData<List<SongPlaylistCR>> getAllPlaylistSongReference(String playlistId){
        return  playlistDao.getAllPlaylistSongReference(playlistId);
    }

}