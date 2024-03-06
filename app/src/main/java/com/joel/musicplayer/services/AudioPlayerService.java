package com.joel.musicplayer.services;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.session.PlaybackState;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.joel.musicplayer.activities.MusicActivityListener;
import com.joel.musicplayer.model.Song;
import com.joel.musicplayer.utis.AudioPlayerState;
import com.joel.musicplayer.utis.CustomLogger;

import java.io.IOException;

public class AudioPlayerService extends Service {

    private MusicActivityListener musicActivityListener;
    private final IBinder binder = new AudioBinder();
    private Notification notification;
    private final int NOTIFICATION_ID = 1001;
    private String songName = "None";
    private String TAG = "MEDIA_PLAYER_ERROR_STATE";
    private boolean INITIALIZED,STOPPED, PREPARED, PAUSED, COMPLETED;
    private MediaPlayer mediaPlayer;
    private AudioPlayerCallback audioPlayerCallbackFullPlayer, audioPlayerCallbackMiniPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayerManager.getMediaPlayer();
        mediaPlayer.setOnErrorListener((mediaPlayer, i, i1) -> {
            switch (i) {
                case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                    Log.e(TAG, "Unknown media player error occurred");
                    break;
                case MediaPlayer.MEDIA_ERROR_IO:
                    Log.e(TAG, "I/O error occurred");
                    break;
                case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                    Log.e(TAG, "Media server died");
                    break;
                case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                    Log.e(TAG, "Unsupported media type");
                    break;
                case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                    Log.e(TAG, "Media operation timed out");
                    break;
                default:
                    Log.e(TAG, "Unknown MediaPlayer error occurred: " + i);
                    break;
            }
            audioPlayerCallbackFullPlayer.playerStateChanged(AudioPlayerState.ERROR);
            audioPlayerCallbackMiniPlayer.playerStateChanged(AudioPlayerState.ERROR);
            return false;
        });
        mediaPlayer.setOnCompletionListener(mediaPlayer -> {
            COMPLETED = true;
            audioPlayerCallbackFullPlayer.playerStateChanged(AudioPlayerState.COMPLETE);
            audioPlayerCallbackMiniPlayer.playerStateChanged(AudioPlayerState.COMPLETE);
        });

        mediaPlayer.setOnPreparedListener(mediaPlayer -> {
            PREPARED = true;
            audioPlayerCallbackFullPlayer.playerStateChanged(AudioPlayerState.PREPARED);
            audioPlayerCallbackMiniPlayer.playerStateChanged(AudioPlayerState.PREPARED);
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        notification = createNotification();
        showNotification(notification);
        return binder;
    }

    public class AudioBinder extends Binder{
        public AudioPlayerService getService(){
            return AudioPlayerService.this;
        }
        public void setMusicActivityListener(MusicActivityListener musicActivityListener){
            AudioPlayerService.this.musicActivityListener = musicActivityListener;
        }
    }

    public void updateSong(Song song){
        String message = "None";
        if (song != null){
            message = song.getTitle();
        }
        this.songName = message;
        notification = createNotification();
        showNotification(notification);
    }

    private void showNotification(Notification showNotification){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(new NotificationChannel(
                    "MUSIC_PLAYER_APP",
                    "Music Player",
                    NotificationManager.IMPORTANCE_DEFAULT
            ));
        }
        startForeground(NOTIFICATION_ID,showNotification);
    }

    public Notification createNotification(){
        return new NotificationCompat.Builder(this,"MUSIC_PLAYER_APP")
                .setContentTitle("Music player")
                .setContentText("Current song : " + songName)
                .setSmallIcon(android.R.drawable.star_on)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
    }

    public void setAudioPlayerCallbackFullPlayer(AudioPlayerCallback audioPlayerCallbackFullPlayer) {
        this.audioPlayerCallbackFullPlayer = audioPlayerCallbackFullPlayer;
    }

    public void setAudioPlayerCallbackMiniPlayer(AudioPlayerCallback audioPlayerCallbackMiniPlayer) {
        this.audioPlayerCallbackMiniPlayer = audioPlayerCallbackMiniPlayer;
    }

    public void initializePlayer(String path){
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        INITIALIZED = true;
        audioPlayerCallbackFullPlayer.playerStateChanged(AudioPlayerState.INITIALIZED);
        audioPlayerCallbackMiniPlayer.playerStateChanged(AudioPlayerState.INITIALIZED);
    }

    public void prepare(){
        if (INITIALIZED){
            mediaPlayer.prepareAsync();
            PREPARED = true;
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    play();
                    audioPlayerCallbackFullPlayer.playerStateChanged(AudioPlayerState.PREPARED);
                    audioPlayerCallbackMiniPlayer.playerStateChanged(AudioPlayerState.PREPARED);
                }
            });
        }
    }

    public void play(){
        if (PREPARED && !mediaPlayer.isPlaying()){
            mediaPlayer.start();
            COMPLETED = false;
            STOPPED = false;
            PAUSED = false;
            audioPlayerCallbackFullPlayer.playerStateChanged(AudioPlayerState.PLAYING);
            audioPlayerCallbackMiniPlayer.playerStateChanged(AudioPlayerState.PLAYING);
        }
    }

    public void pause(){
        if (PREPARED && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            PAUSED = true;
            audioPlayerCallbackFullPlayer.playerStateChanged(AudioPlayerState.PAUSE);
            audioPlayerCallbackMiniPlayer.playerStateChanged(AudioPlayerState.PAUSE);
        }
    }

    public void stop(){
        if (INITIALIZED){
            mediaPlayer.stop();
            PAUSED = false;
            STOPPED = true;
            audioPlayerCallbackFullPlayer.playerStateChanged(AudioPlayerState.STOP);
            audioPlayerCallbackMiniPlayer.playerStateChanged(AudioPlayerState.STOP);
        }
    }

    public void reset(){
        mediaPlayer.reset();
        PREPARED = false;
        INITIALIZED = false;
        PAUSED = false;
        COMPLETED = false;
        STOPPED = false;
    }

    @Override
    public boolean onUnbind(Intent intent) { return super.onUnbind(intent); }

    public boolean isINITIALIZED() { return INITIALIZED; }
    public boolean isSTOPPED() { return STOPPED; }
    public boolean isPREPARED() { return PREPARED; }
    public boolean isPAUSED() { return PAUSED; }
    public boolean isCOMPLETED() { return COMPLETED; }
    public boolean isPLAYING() { return mediaPlayer.isPlaying(); }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        mediaPlayer.release();
    }
}