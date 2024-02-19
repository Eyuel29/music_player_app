package com.joel.musicplayer.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.joel.musicplayer.R;
import com.joel.musicplayer.model.audio.AudioModel;

import java.util.List;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.SongHolder> {

    private List<AudioModel> allAudio;
    private Context context;
    private SelectionListener selectionListener;

    public AllSongsAdapter(List<AudioModel> allAudio, Context context){
        this.allAudio = allAudio;
        this.context = context;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_card, parent, false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, @SuppressLint("RecyclerView") int position) {

        String title = allAudio.get(holder.getAdapterPosition()).getSongTitle();
        String artist = allAudio.get(holder.getAdapterPosition()).getArtist();
        String path = allAudio.get(holder.getAdapterPosition()).getSongPath();
        int durationInSec = allAudio.get(holder.getAdapterPosition()).getSongDuration();
        durationInSec /= 1000;

        if (title.length() > 25){title= title.substring(0,25)+"...";}
        if (artist.length() > 15){artist= artist.substring(0,15)+"...";}

        holder.songArtist.setText(artist);
        holder.songDuration.setText(String.valueOf(convertDurationToMinutes(durationInSec)));
        holder.songTitle.setText(title);

        byte[] coverImage = getSongCover(path);
        Log.i("COVER","COVER SIZE : " + ""+coverImage.length);

        if (coverImage != null){
            RequestOptions requestOptions = new RequestOptions()
                    .transform(new RoundedCorners(10));
            Glide.with(context)
                    .asBitmap()
                    .load(coverImage)
                    .apply(requestOptions)
                    .into(holder.albumCover);
        }else{
            Glide.with(context)
                    .load(R.drawable.music_player)
                    .into(holder.albumCover);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionListener.clicked(position);
            }
        });
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

    public void setSelectionListener(SelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    public byte[] getSongCover(String path){
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(path);
        byte[] data = null;
        try {
            data = mediaMetadataRetriever.getEmbeddedPicture();
            mediaMetadataRetriever.release();
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
}