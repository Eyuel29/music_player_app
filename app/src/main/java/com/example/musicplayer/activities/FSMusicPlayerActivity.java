package com.example.musicplayer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicplayer.R;
import com.example.musicplayer.models.AudioModel;
import com.example.musicplayer.models.MusicPlayer;

import java.io.IOException;
import java.util.List;

public class FSMusicPlayerActivity extends AppCompatActivity implements MusicActivityListener{

    private ImageButton playPauseButton;
    private TextView artistView;
    private TextView titleView;
    private ImageView songCover;
    private MusicPlayer musicPlayer;
    private String path, album, title, artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fsmusic_player);
        if (!getSupportActionBar().equals(null)){ getSupportActionBar().hide(); }

        getAndSetData();
        musicPlayer = MusicPlayer.getInstance();
        musicPlayer.setPlayerListener(this);

        try {
            musicPlayer.initPrepareAsync(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicPlayer.isPlaying()){
                    musicPlayer.pause();
                }else{
                    musicPlayer.play();
                }
            }
        });
    }


    public void loadSongImage(String path, ImageView view){
        RequestOptions requestOptions = new RequestOptions()
                .transform(new CenterCrop(),new RoundedCorners(30));

        byte[] data = getSongImageData(path);
        if (data != null){
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(data)
                    .apply(requestOptions)
                    .into(view);
        }else{
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(R.drawable.music_player)
                    .apply(requestOptions)
                    .into(view);
        }
    }

    public byte[] getSongImageData(String path){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        byte[] data;
        try {
            data = retriever.getEmbeddedPicture();
            retriever.release();
        }catch (Exception e){ throw e; }
        return  data;
    }

    public void getAndSetData(){
        List<AudioModel> allAudio = (MainActivity)getParent().
        int position = getIntent().getIntExtra("POSITION", 0);
//        path = getIntent().getStringExtra("PATH");
//        album = getIntent().getStringExtra("ALBUM");
//        title = getIntent().getStringExtra("TITLE");
//        artist = getIntent().getStringExtra("ARTIST");

        path = allAudio.get(position).getSongPath();
        album = allAudio.get(position).getAlbumName();
        title = allAudio.get(position).getSongTitle();
        artist = allAudio.get(position).getArtist();

        if (path == null || album == null || title == null || artist == null){
            finish();
        }else{

            playPauseButton = findViewById(R.id.musicPlayerPlayPause);
            artistView = findViewById(R.id.musicPlayerSongArtist);
            titleView = findViewById(R.id.musicPlayerSongName);
            songCover = findViewById(R.id.songCoverLarge);

            titleView.setText(title);
            artistView.setText(artist);
            loadSongImage(path, songCover);
        }
    }

    @Override
    public void playerPrepared() {
        musicPlayer.play();
    }

    @Override
    public void playerStarted() {
        playPauseButton.setImageDrawable(getDrawable(R.drawable.pause));
    }

    @Override
    public void playerPaused() {
        playPauseButton.setImageDrawable(getDrawable(R.drawable.play));
    }

    @Override
    public void playerStopped() {
        finish();
    }

    @Override
    public void playerReset() {

    }

    @Override
    public void playerCompleted() {
        playPauseButton.setImageDrawable(getDrawable(R.drawable.play));
    }

    @Override
    public void playerError(MediaPlayer mediaPlayer, int i, int i1) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}