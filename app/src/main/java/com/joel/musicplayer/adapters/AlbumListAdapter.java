package com.joel.musicplayer.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumHolder> {

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AlbumHolder extends RecyclerView.ViewHolder{

        public AlbumHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
