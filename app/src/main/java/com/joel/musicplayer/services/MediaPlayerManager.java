package com.joel.musicplayer.services;

import android.media.AudioAttributes;
import android.media.MediaPlayer;

public class MediaPlayerManager {

    private static volatile MediaPlayer instance;

    public static MediaPlayer getMediaPlayer() {
        if (instance == null) {
            synchronized (MediaPlayerManager.class) {
                if (instance == null) {
                    MediaPlayerManager.initializePlayer();
                }
            }
        }
        return instance;
    }

    private static void initializePlayer() {
        instance = new MediaPlayer();
        instance.setAudioAttributes(new AudioAttributes
                .Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build());
    }
}