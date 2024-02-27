package com.joel.musicplayer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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
import com.joel.musicplayer.repo.MusicRepo;
import com.joel.musicplayer.services.AudioReader;
import com.joel.musicplayer.viewmodel.SongsViewModel;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SongGetter, SelectionListener {

    private AppFragmentStateAdapter appFragmentStateAdapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private final String[] Titles = {"Songs", "Albums", "Playlists", "Artists"};
    private String[] permissions;
    private AudioReader audioReader;
    private MusicRepo musicRepo;
    private SongsViewModel songsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null){getSupportActionBar().hide();}
        musicRepo = new MusicRepo(this.getApplication());
        audioReader = new AudioReader(getApplicationContext());
        songsViewModel = new ViewModelProvider(MainActivity.this).get(SongsViewModel.class);
        setPermission();
        startApp();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void startApp(){
        if(checkAllPermissions(permissions)){
            updateDB();
            initViewPager();
        }else{
            askPermission();
        }
    }

    public void readAllArtists(){
        try {
            List<Artist> allArtist = audioReader.getAllArtists();
            if (allArtist != null){
                musicRepo.addAllArtists(allArtist);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readAllSongs(){
        try {
            List<Song> allSongs = audioReader.readAllSongs();
            if (allSongs != null){
                musicRepo.addAllSongs(allSongs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readAllAlbums(){
        try {
            List<Album> allAlbums = audioReader.readAlbums();
            if (allAlbums != null){
                musicRepo.addAllAlbums(allAlbums);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateDB(){
        songsViewModel.getAllSongs().observe(this, songs -> {
            if (songs == null || songs.size() < 1){
                readAllArtists();
                readAllSongs();
                readAllAlbums();
            }
        });
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
        return ContextCompat.checkSelfPermission(
                getApplicationContext(),
                permission) == PackageManager.PERMISSION_GRANTED;
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
        appFragmentStateAdapter = new AppFragmentStateAdapter(this,Titles,this, this.getApplication());
        appFragmentStateAdapter.setSongGetter(this);
        viewPager.setAdapter(appFragmentStateAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(Titles[position])).attach();
    }

    @Override
    public List<AudioModel> getAllSongs() {
//        TODO
        return null;
    }

    @Override
    public void clicked(int position) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}