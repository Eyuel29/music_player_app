package com.example.musicplayer.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.musicplayer.R;
import com.example.musicplayer.adapters.AllSongsAdapter;
import com.example.musicplayer.adapters.SelectionListener;
import com.example.musicplayer.models.AudioModel;
import com.google.android.material.divider.MaterialDividerItemDecoration;

import java.util.List;

public class SongsList extends Fragment {

    private List<AudioModel> allSongs;
    private SelectionListener selectionListener;

    public SongsList(List<AudioModel> allSongs, SelectionListener selectionListener) {
        this.allSongs = allSongs;
        this.selectionListener = selectionListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View songsListView = inflater.inflate(R.layout.fragment_songs_list, container, false);
        AllSongsAdapter allSongsAdapter = new AllSongsAdapter(allSongs, requireContext());
        allSongsAdapter.setSelectionListener(selectionListener);
        RecyclerView songRecyclerView = songsListView.findViewById(R.id.songRecycler);
        MaterialDividerItemDecoration divider = new MaterialDividerItemDecoration(requireContext(), LinearLayout.VERTICAL);
        divider.setDividerColor(requireContext().getResources().getColor(R.color.default_dark_400));
        songRecyclerView.addItemDecoration(divider);
        songRecyclerView.setAdapter(allSongsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        songRecyclerView.setLayoutManager(linearLayoutManager);
        return songsListView;
    }
}