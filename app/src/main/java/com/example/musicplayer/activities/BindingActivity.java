package com.example.musicplayer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ServiceCompat;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.R;
import com.example.musicplayer.services.AudioPlayerService;

public class BindingActivity extends AppCompatActivity {

    private AudioPlayerService audioPlayerService;
    private AudioPlayerService.AudioBinder audioBinder;
    private boolean isBound = false;
    private TextView boundMessage;
    private Button randomButton;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            audioBinder = (AudioPlayerService.AudioBinder) iBinder;
            audioPlayerService = audioBinder.getService();
            boundMessage.setText("Current number : "+ audioPlayerService.getRandomNumber());
            isBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
        boundMessage = findViewById(R.id.boundMessage);
        randomButton = findViewById(R.id.randomButton);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (isBound){
                        audioPlayerService.updateRandomNumber();
                        boundMessage.setText("Current number : "+ audioPlayerService.getRandomNumber());
                    }else{
                        Toast.makeText(BindingActivity.this, "Service is not bound!", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this,AudioPlayerService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound){
            unbindService(serviceConnection);
            isBound = false;
        }
    }
}