package com.example.musicplayer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.models.AudioModel;

import java.util.List;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.SongHolder> {

    private List<AudioModel> allAudio;

    public AllSongsAdapter(List<AudioModel> allAudio){
        this.allAudio = allAudio;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_card, parent, false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {

        String title = allAudio.get(position).getSongTitle();
        String artist = allAudio.get(position).getArtist();
        int durationInSec = allAudio.get(position).getSongDuration();
        durationInSec /= 1000;

        if (title.length() > 15){title= title.substring(0,15);}
        if (artist.length() > 15){artist= artist.substring(0,15);}

        holder.songArtist.setText(artist);
        holder.songDuration.setText(String.valueOf(convertDurationToMinutes(durationInSec)));
        holder.songTitle.setText(title);
    }

    public String convertDurationToMinutes(int durationInSeconds) {
        int minutes = durationInSeconds / 60;
        int seconds = durationInSeconds % 60;

        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    public int getItemCount() {
        return allAudio.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        ImageView albumCover;
        TextView songDuration;
        TextView songTitle;
        TextView songArtist;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
             albumCover = itemView.findViewById(R.id.songImage);
             songDuration = itemView.findViewById(R.id.songDuration);
             songTitle = itemView.findViewById(R.id.songTitle);
             songArtist = itemView.findViewById(R.id.songArtist);
        }
    }

}
