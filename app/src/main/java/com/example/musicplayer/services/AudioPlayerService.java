package com.example.musicplayer.services;

import android.app.NotificationChannel;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Random;

public class AudioPlayerService extends Service {

    private final IBinder binder = new AudioBinder();
    private final Random random = new Random();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class AudioBinder extends Binder{
        public AudioPlayerService getService(){
            return AudioPlayerService.this;
        }
    }

    public int generateRandomNumber(){
        return random.nextInt(1000);
    }

    private void createNotificationChannel(){
        NotificationChannel notificationChannel = new NotificationChannel();

    }


}