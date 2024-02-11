package com.example.musicplayer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayer.adapters.AllSongsAdapter;
import com.example.musicplayer.models.AudioModel;

import java.util.List;

public class SongsList extends Fragment {

    private List<AudioModel> allSongs;

    public SongsList(List<AudioModel> allSongs) {
        this.allSongs = allSongs;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View songsListView = inflater.inflate(R.layout.fragment_songs_list, container, false);

        AllSongsAdapter allSongsAdapter = new AllSongsAdapter(allSongs);
        RecyclerView songRecyclerView = songsListView.findViewById(R.id.songRecycler);
        songRecyclerView.setAdapter(allSongsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        songRecyclerView.setLayoutManager(linearLayoutManager);

        return songsListView;
    }
}