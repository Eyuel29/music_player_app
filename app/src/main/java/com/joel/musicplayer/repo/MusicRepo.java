package com.joel.musicplayer.repo;

import android.app.Application;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import com.joel.musicplayer.model.Album;
import com.joel.musicplayer.model.Playlist;
import com.joel.musicplayer.model.Song;
import com.joel.musicplayer.room.SongDatabase;
import com.joel.musicplayer.room.dao.AlbumDao;
import com.joel.musicplayer.room.dao.ArtistDao;
import com.joel.musicplayer.room.dao.PlaylistDao;
import com.joel.musicplayer.room.dao.SongDao;

import java.util.List;

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
    public void addAlbum(Album album){
        SongDatabase.databaseExecutor.execute(() ->{
            albumDao.addAlbum(album);
        });
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
    public void addSong(Song song){
        SongDatabase.databaseExecutor.execute(() ->{
            songDao.addSong(song);
        });
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

    public LiveData<List<Song>> getAllSongs(){ return  songDao.getAllSongs(); }
    public LiveData<List<Song>> getAllSongFromAlbum(String albumId){
        return albumDao.getAllSongFromAlbum(albumId);
    }
    public LiveData<List<Album>> getAllAlbums(){
        return albumDao.getAllAlbums();
    }
    public LiveData<List<Playlist>> getAllPlaylists(){
        return playlistDao.getAllPlaylists();
    }
    public LiveData<List<Song>> getAllLikedSongs(){
        return songDao.getAllLikedSongs();
    }
    public LiveData<List<Song>> getSongsInPlaylist(String playlistId){
        return playlistDao.getSongsInPlaylist(playlistId);
    }
}
