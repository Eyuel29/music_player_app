package com.joel.musicplayer.adapters;

import android.app.Activity;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.joel.musicplayer.fragments.AlbumsList;
import com.joel.musicplayer.fragments.LikedSongsList;
import com.joel.musicplayer.fragments.Playlists;
import com.joel.musicplayer.activities.SongGetter;
import com.joel.musicplayer.fragments.SongsList;

public class AppFragmentStateAdapter extends FragmentStateAdapter {

    private final String[] titles;
    private SongGetter songGetter;
    private final SelectionListener selectionListener;
    private final Activity activity;

    public AppFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, String[] titles, SelectionListener selectionListener, Activity activity) {
        super(fragmentActivity);
        this.titles = titles;
        this.selectionListener = selectionListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new SongsList(selectionListener, activity);
            case 1:
                return new AlbumsList();
            case 2:
                return new Playlists();
            case 3:
                return new LikedSongsList();
        }
        return new SongsList(selectionListener, activity);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public void setSongGetter(SongGetter songGetter) {
        this.songGetter = songGetter;
    }
}