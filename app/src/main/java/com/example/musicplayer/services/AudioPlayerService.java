package com.example.musicplayer.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }
}