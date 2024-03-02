package com.joel.musicplayer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joel.musicplayer.R;
import com.joel.musicplayer.adapters.AppFragmentStateAdapter;
import com.joel.musicplayer.adapters.SelectionListener;
import com.joel.musicplayer.model.Album;
import com.joel.musicplayer.model.Artist;
import com.joel.musicplayer.model.Song;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.joel.musicplayer.repo.MusicRepo;
import com.joel.musicplayer.services.AudioPlayerCallback;
import com.joel.musicplayer.services.AudioPlayerService;
import com.joel.musicplayer.services.AudioReader;
import com.joel.musicplayer.utis.CustomLogger;
import com.joel.musicplayer.utis.ImageLoader;
import com.joel.musicplayer.viewmodel.SongsViewModel;

import java.io.IOException;
import java.util.List;

public class MainActivity extends
        AppCompatActivity implements
        SongGetter,
        SelectionListener, AudioPlayerCallback {
    private AppFragmentStateAdapter appFragmentStateAdapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private final int PERMISSION_CODE = 1001;
    private String[] permissions;
    private final String[] Titles = {"Songs", "Albums", "Playlists", "Artists"};
    private AudioReader audioReader;
    private MusicRepo musicRepo;
    private SongsViewModel songsViewModel;
    private boolean serviceBound = false;
    private AudioPlayerService.AudioBinder audioBinder;
    private AudioPlayerService audioPlayerService;
    private ImageView playPauseButton, skipNextButton, skipPrevButton, songCoverMini;
    private ImageButton closeButton;
    private TextView miniSongTitle, miniSongArtist;
    private byte[] songArtMini;
    private View miniView;

    private List<Song> allSongs;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            audioBinder = (AudioPlayerService.AudioBinder) iBinder;
            audioPlayerService = audioBinder.getService();
            audioPlayerService.setAudioPlayerCallbackMiniPlayer(MainActivity.this);
            audioPlayerService.setAudioPlayerCallbackFullPlayer(MainActivity.this);
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null){getSupportActionBar().hide();}
        musicRepo = new MusicRepo(this.getApplication());
        audioReader = new AudioReader(getApplicationContext());
        songsViewModel = new ViewModelProvider(MainActivity.this).get(SongsViewModel.class);

        playPauseButton = findViewById(R.id.buttonPlayPauseMini);
        skipNextButton = findViewById(R.id.buttonSkipNextMini);
        skipPrevButton = findViewById(R.id.buttonSkipPreMini);
        closeButton = findViewById(R.id.closeMiniPlayer);

        miniSongTitle = findViewById(R.id.songTitleMini);
        miniSongArtist = findViewById(R.id.songArtistMini);
        songCoverMini = findViewById(R.id.songImageMini);
        miniView = findViewById(R.id.mini_player);
        miniView.setOnClickListener(view -> {
            Intent intent = new Intent(this, SongPlayerActivity.class);
            startActivity(intent);
        });
        closeButton.setOnClickListener(view -> {
            stopSong();
        });

        setPermission();
        startApp();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void setPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            this.permissions = new String[]{
                    Manifest.permission.READ_MEDIA_AUDIO,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        }else{
            this.permissions = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
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
        return ContextCompat.checkSelfPermission(
                getApplicationContext(),permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void askPermission(){
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean permissionStatus = false;
        if(requestCode == PERMISSION_CODE){
            for (int perResult : grantResults) {
                permissionStatus = (perResult == PackageManager.PERMISSION_GRANTED);
            }
            if (permissionStatus){
                startApp();
            }else{
                askPermission();
            }
        }
    }

    public void startApp(){
        if(checkAllPermissions(permissions)){
            updateDB();
            initViewPager();
            Intent serviceIntent = new Intent(MainActivity.this,AudioPlayerService.class);
            bindService(serviceIntent, serviceConnection, 1);
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
                readAllAlbums();
                readAllSongs();
            }
            this.allSongs = songs;
        });
    }

    public void initViewPager(){
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        appFragmentStateAdapter = new AppFragmentStateAdapter(this,Titles,this, this);

        viewPager.setAdapter(appFragmentStateAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(Titles[position])).attach();
    }

    public void startSong(Song song){
        ImageLoader.loadSongImage(this, song.getPath(), songCoverMini);
        miniSongTitle.setText(song.getTitle());
        miniSongArtist.setText(song.getArtistId());
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void clicked(Song song) {
        audioPlayerService.initializePlayer(song.getPath());
        audioPlayerService.prepare();
        miniView.setVisibility(View.VISIBLE);
        startSong(song);
    }

    public void stopSong(){
        audioPlayerService.stop();
        miniView.setVisibility(View.GONE);
    }

    @Override
    public List<Song> getAllSongs() {
        return allSongs;
    }

    @Override
    public void playerStateChanged(String newState) {

    }
}