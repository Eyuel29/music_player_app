package com.joel.musicplayer.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.joel.musicplayer.model.audio.AudioModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AudioPlayerService extends Service {

    private final IBinder binder = new AudioBinder();
    private final Random random = new Random();
    private Notification notification;
    private final int NOTIFICATION_ID = 1001;
    private int randomNumber = 29;
    private boolean foreground = false;

    @Override
    public void onCreate() {
        super.onCreate();
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
    }

    public void updateRandomNumber(){
        this.randomNumber = generateRandomNumber();
        notification = createNotification();
        showNotification(notification);
    }

    public int getRandomNumber() {
        return this.randomNumber;
    }

    private int generateRandomNumber(){
        return this.random.nextInt(1000);
    }

    private void showNotification(Notification notification){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(new NotificationChannel(
                    "1234JOEL",
                    "Random Notification",
                    NotificationManager.IMPORTANCE_DEFAULT
            ));
        }
            startForeground(NOTIFICATION_ID,notification);
    }

    public Notification createNotification(){
        Notification notification = new NotificationCompat.Builder(this,"1234JOEL")
                .setContentTitle("Random number")
                .setContentText("Current number : "+randomNumber)
                .setSmallIcon(android.R.drawable.star_on)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        return notification;
    }


    public List<AudioModel> readAudioExternalStorage(Context context){
        List<AudioModel> allAudio = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
        };

        Cursor cursor = context.getContentResolver()
                .query(uri,projection,
                        null,
                        null,
                        null,
                        null);

        if(cursor != null){
            while(cursor.moveToNext()){
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String artist = cursor.getString(2);
                String duration = cursor.getString(3);
                String data = cursor.getString(4);
                AudioModel audioModel = new AudioModel(artist,title,album,data,Integer.valueOf(duration));
                Log.i("AUDIO_EXTRACT",title +" "+ artist+" "+duration+" "+album);
                allAudio.add(audioModel);
            }
            cursor.close();
        }
        return allAudio;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }
}