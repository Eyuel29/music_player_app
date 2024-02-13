package com.example.musicplayer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicplayer.R;

public class FSMusicPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fsmusic_player);

        if (!getSupportActionBar().equals(null)){
            getSupportActionBar().hide();
        }

        String path = getIntent().getStringExtra("PATH");
        String album = getIntent().getStringExtra("ALBUM");
        String title = getIntent().getStringExtra("TITLE");
        String artist = getIntent().getStringExtra("ARTIST");

        TextView artistView = findViewById(R.id.musicPlayerSongArtist);
        TextView titleView = findViewById(R.id.musicPlayerSongName);
        ImageView songCover = findViewById(R.id.songCoverLarge);

        loadSongImage(path, songCover);
        titleView.setText(title);
        artistView.setText(artist);
    }

    public void loadSongImage(String path, ImageView view){
        RequestOptions requestOptions = new RequestOptions()
                .transform(new CenterCrop(),new RoundedCorners(20));

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
        }catch (Exception e){
            throw e;
        }

        return  data;
    }

}