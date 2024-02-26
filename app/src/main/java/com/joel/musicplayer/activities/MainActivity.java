package com.joel.musicplayer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.joel.musicplayer.R;
import com.joel.musicplayer.adapters.AppFragmentStateAdapter;
import com.joel.musicplayer.adapters.SelectionListener;
import com.joel.musicplayer.model.Album;
import com.joel.musicplayer.model.Artist;
import com.joel.musicplayer.model.Song;
import com.joel.musicplayer.model.audio.AudioModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.joel.musicplayer.services.AudioReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements SongGetter, SelectionListener {

    private AppFragmentStateAdapter appFragmentStateAdapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private List<AudioModel> allAudio = new ArrayList<>();
    private final String[] Titles = {"Songs", "Albums", "Playlists", "Artists"};
    private String[] permissions;
    private Button click;
    private AudioReader audioReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null){getSupportActionBar().hide();}
        click = findViewById(R.id.click);
        audioReader = new AudioReader(getApplicationContext());
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SongPlayerActivity.class);
                startActivity(intent);
            }
        });
        setPermission();
        startApp();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void startApp(){
        if(checkAllPermissions(permissions)){
            initViewPager();
//            readSongs();
//              getAllAlbums();
            readArtists();
        }else{
            askPermission();
        }
    }

    public void readArtists(){
        List<Artist> allArtist = null;
        try {
            allArtist = audioReader.getAllArtists();
            for (Artist artist : allArtist) {
                Log.i("ALL_ARTIST","Artist id : " + artist.getArtistId());
                Log.i("ALL_ARTIST","Artist name : " + artist.getArtistName());
                Log.i("ALL_ARTIST","--------------------------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readSongs(){
        List<Song> allSongz = null;
        try {
            allSongz = audioReader.readAllSongs();

            for (Song song : allSongz) {
                Log.i("ALL_SONG","song title : " + song.getTitle());
                Log.i("ALL_SONG","song path : " + song.getPath());
                Log.i("ALL_SONG","song id : " + song.getSongId());
                Log.i("ALL_SONG","song artist id : " + song.getArtistId());
                Log.i("ALL_SONG","song duration : " + song.getDuration());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAllAlbums(){
        try {
            List<Album> allAlbums = audioReader.readAlbums();
            for (Album album: allAlbums) {
                Log.i("ALBUMS", "Album id : "+album.getAlbumId());
                Log.i("ALBUMS", "Album title : "+album.getAlbumTitle());
                Log.i("ALBUMS", "Album date : "+album.getReleaseDate());
                Log.i("ALBUMS", "Album artist id : "+album.getArtistId());
                Log.i("ALBUMS", "-----------------------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkAllPermissions(String[] allPermissions){
        boolean permissionStatus = true;

        for (String permission : allPermissions) {
            if (!checkSinglePermission(permission)){
                permissionStatus = false;
            }
        }
        return permissionStatus;
    }

    public boolean checkSinglePermission(String permission){
        boolean permissionStatus = ContextCompat.checkSelfPermission(
                getApplicationContext(),
                permission) == PackageManager.PERMISSION_GRANTED;
        return permissionStatus;
    }

    public void setPermission(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2){
            this.permissions = new String[]{
                    Manifest.permission.READ_MEDIA_AUDIO,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
            };
        }else{
            this.permissions = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        }
    }

    public void askPermission(){
        ActivityCompat.requestPermissions(this, permissions,1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean permissionStatus = false;
        if(requestCode == 1){
            for (int perResult: grantResults) {
                permissionStatus = perResult != PackageManager.PERMISSION_DENIED;
            }
            if (permissionStatus){
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                startApp();
            }else{
                askPermission();
            }
        }
    }

    public void initViewPager(){
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        appFragmentStateAdapter = new AppFragmentStateAdapter(this,Titles,this);
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
        return null;
//        Todo
    }

    @Override
    public void clicked(int position) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}