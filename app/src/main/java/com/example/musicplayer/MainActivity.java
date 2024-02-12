package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.musicplayer.adapters.AppFragmentStateAdapter;
import com.example.musicplayer.models.AudioModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements SongGetter{

    private AppFragmentStateAdapter appFragmentStateAdapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private volatile boolean permissionGranted = false;

    private volatile List<AudioModel> allAudio = new LinkedList<>();
    private final String[] Titles = {"Songs", "Albums", "Playlists", "Artists"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        startApp();
    }

    public void startApp(){
        if(checkPermission()){
            setAllAudio(readAudioExternalStorage(MainActivity.this));
            initViewPager();
        }else{
            askPermission();
        }
    }

    public boolean checkPermission(){
        boolean permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        return permission;
    }

    public void askPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                startApp();
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} , 1);
            }
        }
    }

    public List<AudioModel> readAudioExternalStorage(Context context){

        List<AudioModel> allAudio = new LinkedList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA
        };

        Cursor cursor = context.getContentResolver().query(uri,projection,null,null,null,null);

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

    public void initViewPager(){
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        appFragmentStateAdapter = new AppFragmentStateAdapter(this,Titles);
        appFragmentStateAdapter.setSongGetter(this);
        viewPager.setAdapter(appFragmentStateAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(Titles[position]);
        }).attach();
    }



    public void setAllAudio(List<AudioModel> allAudio) {
        this.allAudio = allAudio;
    }

    @Override
    public List<AudioModel> getAllSongs() {
        if (this.allAudio == null){
            Log.i("READING_SONGS", "ERROR OCCURRED!");
            return new ArrayList<AudioModel>();

        }else{ return this.allAudio; }
    }
}