package com.joel.musicplayer.activities;

import android.media.MediaPlayer;

public interface MusicActivityListener {

    void playerPrepared ();
    void playerStarted();
    void playerPaused();
    void playerStopped();
    void playerReset();
    void playerCompleted();
    void playerError(MediaPlayer mediaPlayer, int i, int i1);
}
