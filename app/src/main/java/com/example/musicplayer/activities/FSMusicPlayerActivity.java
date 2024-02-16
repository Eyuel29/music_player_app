package com.example.musicplayer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.models.MusicPlayer;
import com.example.musicplayer.utis.ImageLoader;

public class FSMusicPlayerActivity extends AppCompatActivity{

    private ImageButton playPauseButton;
    private MusicPlayer musicPlayer;
    private String path,album,title,artist;
    private int duration;
    private TextView artistView, titleView;
    private ImageView songCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fsmusic_player);
        if (getSupportActionBar() != null){ getSupportActionBar().hide(); }
    }

    public void getAndSetData(){
        path = getIntent().getStringExtra("PATH");
        album = getIntent().getStringExtra("ALBUM");
        title = getIntent().getStringExtra("TITLE");
        artist = getIntent().getStringExtra("ARTIST");
        duration = getIntent().getIntExtra("DURATION",0);

        if (path == null || album == null || title == null || artist == null){
            finish();
        }else{
            playPauseButton = findViewById(R.id.musicPlayerPlayPause);
            artistView = findViewById(R.id.musicPlayerSongArtist);
            titleView = findViewById(R.id.musicPlayerSongName);
            songCover = findViewById(R.id.songCoverLarge);
            titleView.setText(title);
            artistView.setText(artist);
            ImageLoader.loadSongImage(this, path, songCover);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}