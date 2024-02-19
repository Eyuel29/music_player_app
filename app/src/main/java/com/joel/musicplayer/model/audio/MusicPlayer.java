package com.joel.musicplayer.model.audio;
import android.media.AudioAttributes;
import android.media.MediaPlayer;

import com.joel.musicplayer.activities.MusicActivityListener;

import java.io.IOException;

public class MusicPlayer {

    private static MusicPlayer instance;
    private MediaPlayer mediaPlayer;
    private boolean prepared, completed, paused;
    private MusicActivityListener playerListener;

    private MusicPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build());

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                playerListener.playerPrepared();
                prepared = true;
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playerListener.playerCompleted();
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                playerListener.playerError(mediaPlayer, i, i1);
                return false;
            }
        });
    }

    public static synchronized MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    public void setPlayerListener(MusicActivityListener playerListener) {
        this.playerListener = playerListener;
    }

    public void play() {
        if (prepared) {
            mediaPlayer.start();
            playerListener.playerStarted();
        }
    }

    public void pause() {
        if (prepared && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playerListener.playerPaused();
            paused = true;
        }
    }

    public void stop() {
        if (prepared) {
            mediaPlayer.stop();
            playerListener.playerStopped();
        }
    }

    public synchronized void initPrepareAsync(String path) throws IOException {
        mediaPlayer.reset();
        mediaPlayer.setDataSource(path);
        mediaPlayer.prepareAsync();
    }

    public void resetPlayer() {
        mediaPlayer.reset();
        playerListener.playerReset();
        prepared = false;
        completed = false;
        paused = false;
    }

    public void seekTo(int msc) {
        if (prepared || mediaPlayer.isPlaying() || paused || completed) {
            mediaPlayer.seekTo(msc);
        }
    }

    public boolean isPaused(){
        return paused;
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }
}
