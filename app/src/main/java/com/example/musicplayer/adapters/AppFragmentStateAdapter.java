package com.example.musicplayer.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.musicplayer.AlbumsList;
import com.example.musicplayer.LikedSongsList;
import com.example.musicplayer.MainActivity;
import com.example.musicplayer.Playlists;
import com.example.musicplayer.SongGetter;
import com.example.musicplayer.SongsList;
import com.example.musicplayer.models.AudioModel;

import java.util.List;

public class AppFragmentStateAdapter extends FragmentStateAdapter {

    private String[] titles;
    SongGetter songGetter;
    private SelectionListener selectionListener;

    public AppFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, String[] titles, SelectionListener selectionListener) {
        super(fragmentActivity);
        this.titles = titles;
        this.selectionListener = selectionListener;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        List<AudioModel> allSongsList = songGetter.getAllSongs();
        Log.i("Obtained_Songs", "" + allSongsList.size());

        switch(position){
            case 0:
                return new SongsList(allSongsList, selectionListener);
            case 1:
                return new AlbumsList();
            case 2:
                return new Playlists();
            case 3:
                return new LikedSongsList();
        }
        return new SongsList(allSongsList, selectionListener);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public void setSongGetter(SongGetter songGetter) {
        this.songGetter = songGetter;
    }
}
