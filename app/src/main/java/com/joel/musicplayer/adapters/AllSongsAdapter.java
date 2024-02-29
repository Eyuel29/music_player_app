package com.joel.musicplayer.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaMetadataRetriever;
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
import com.joel.musicplayer.model.Song;

import java.util.ArrayList;
import java.util.List;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.SongHolder> {

    private List<Song> allSongs = new ArrayList<>();
    private final Context context;
    private SelectionListener selectionListener;

    public AllSongsAdapter(Context context){
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateIndex(List<Song> allSongs){
        this.allSongs = allSongs;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_card, parent, false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, @SuppressLint("RecyclerView") int position) {

        String title = allSongs.get(position).getTitle();
        String artist = allSongs.get(position).getArtistId();
        String path = allSongs.get(position).getPath();
        long durationInSec = allSongs.get(position).getDuration();
        durationInSec /= 1000;

        if (title.length() > 25){title= title.substring(0,25)+"...";}
        if (artist.length() > 15){artist= artist.substring(0,15)+"...";}

        holder.songArtist.setText(artist);
        holder.songDuration.setText(String.valueOf(convertDurationToMinutes(durationInSec)));
        holder.songTitle.setText(title);

        byte[] coverImage = getSongCover(path);

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
                    .load(R.drawable.playing_icon)
                    .into(holder.albumCover);
        }

        holder.itemView.setOnClickListener(view -> {
            selectionListener.clicked(allSongs.get(position));
        });
    }

    @SuppressLint("DefaultLocale")
    public String convertDurationToMinutes(long durationInSeconds) {
        long minutes = durationInSeconds / 60;
        long seconds = durationInSeconds % 60;

        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    public int getItemCount() {
        return allSongs.size();
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
        byte[] data = null;
        MediaMetadataRetriever mediaMetadataRetriever;
            try {
                mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(path);
                data = mediaMetadataRetriever.getEmbeddedPicture();
                mediaMetadataRetriever.release();
            }catch (Exception e){
                e.printStackTrace();
            }
        return data;
    }
}