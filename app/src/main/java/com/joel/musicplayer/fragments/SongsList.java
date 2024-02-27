package com.joel.musicplayer.fragments;


import android.app.Application;
import android.graphics.Color;
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
import com.joel.musicplayer.model.Song;
import com.joel.musicplayer.model.audio.AudioModel;
import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.joel.musicplayer.viewmodel.SongsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SongsList extends Fragment {

    private List<Song> allSongs = new ArrayList<>();
    private SelectionListener selectionListener;
    private AllSongsAdapter allSongsAdapter;
    private SongsViewModel songsViewModel;

    public SongsList(SelectionListener selectionListener,@NonNull Application application) {
        this.selectionListener = selectionListener;
        songsViewModel= new SongsViewModel(application);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songsViewModel.getAllSongs().observe(requireActivity(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                allSongsAdapter.updateIndex(songs);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View songsListView = inflater.inflate(R.layout.fragment_songs_list, container, false);
        allSongsAdapter = new AllSongsAdapter(requireContext());
        allSongsAdapter.setSelectionListener(selectionListener);
        RecyclerView songRecyclerView = songsListView.findViewById(R.id.songRecycler);
        MaterialDividerItemDecoration divider = new MaterialDividerItemDecoration(requireContext(), LinearLayout.VERTICAL);
        divider.setDividerColor(getResources().getColor(R.color.divider));
        songRecyclerView.addItemDecoration(divider);
        songRecyclerView.setAdapter(allSongsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        songRecyclerView.setLayoutManager(linearLayoutManager);

        return songsListView;
    }
}