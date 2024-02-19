package com.joel.musicplayer.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.joel.musicplayer.fragments.AlbumsList;
import com.joel.musicplayer.fragments.LikedSongsList;
import com.joel.musicplayer.fragments.Playlists;
import com.joel.musicplayer.activities.SongGetter;
import com.joel.musicplayer.fragments.SongsList;
import com.joel.musicplayer.model.audio.AudioModel;

import java.util.ArrayList;
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
//        List<AudioModel> allSongsList = songGetter.getAllSongs();
        List<AudioModel> allSongsList = new ArrayList<>();
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