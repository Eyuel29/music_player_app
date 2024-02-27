package com.joel.musicplayer.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LikedSongAdapter extends RecyclerView.Adapter<LikedSongAdapter.LikedSongHolder> {

    @NonNull
    @Override
    public LikedSongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull LikedSongHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class LikedSongHolder extends RecyclerView.ViewHolder{

            public LikedSongHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }

