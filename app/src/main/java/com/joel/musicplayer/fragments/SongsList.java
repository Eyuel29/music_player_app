package com.joel.musicplayer.fragments;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.joel.musicplayer.R;
import com.joel.musicplayer.adapters.AllSongsAdapter;
import com.joel.musicplayer.adapters.SelectionListener;
import com.joel.musicplayer.model.Artist;
import com.joel.musicplayer.model.Song;
import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.joel.musicplayer.viewmodel.ArtistViewModel;
import com.joel.musicplayer.viewmodel.SongsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SongsList extends Fragment {

    private List<Song> allSongs = new ArrayList<>();
    private SelectionListener selectionListener;
    private AllSongsAdapter allSongsAdapter;
    private Activity activity;
    private SongsViewModel songsViewModel;
    private ArtistViewModel artistViewModel;

    public SongsList(SelectionListener selectionListener, Activity activity) {
        this.selectionListener = selectionListener;
        songsViewModel= new SongsViewModel(activity.getApplication());
        artistViewModel = new ArtistViewModel(activity.getApplication());
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songsViewModel.getAllSongs().observe(requireActivity(), songs -> allSongsAdapter.updateSongs(songs));

        artistViewModel.getAllArtists().observe(requireActivity(), artists -> {
            allSongsAdapter.updateArtist(artists);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View songsListView = inflater.inflate(R.layout.fragment_songs_list, container, false);
        allSongsAdapter = new AllSongsAdapter(requireContext(),activity);
        allSongsAdapter.setSelectionListener(selectionListener);
        RecyclerView songRecyclerView = songsListView.findViewById(R.id.songRecycler);
        songRecyclerView.setAdapter(allSongsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        songRecyclerView.setLayoutManager(linearLayoutManager);

        return songsListView;
    }
}