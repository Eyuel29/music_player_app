package com.joel.musicplayer.adapters;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.joel.musicplayer.R;
import com.joel.musicplayer.activities.MainActivity;
import com.joel.musicplayer.model.Artist;
import com.joel.musicplayer.model.Song;
import com.joel.musicplayer.room.SongDatabase;
import com.joel.musicplayer.viewmodel.ArtistViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllSongsAdapter extends RecyclerView.Adapter<AllSongsAdapter.SongHolder> {

    private List<Song> allSongs = new ArrayList<>();
    private List<Artist> allArtists = new ArrayList<>();
    private ArtistViewModel artistViewModel;
    private final Context context;
    private SelectionListener selectionListener;
    private Activity activity;

    public AllSongsAdapter(Context context, Activity activity){
        this.context = context;
        artistViewModel = new ArtistViewModel(activity.getApplication());
        this.activity = activity;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateSongs(List<Song> allSongs){
        this.allSongs = allSongs;
        this.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateArtist(List<Artist> allArtists){
        this.allArtists = allArtists;
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

        String artistId = allSongs.get(position).getArtistId();
        String title = allSongs.get(position).getTitle();
        String path = allSongs.get(position).getPath();
        String artistName = "Unknown artist";
        for (Artist artist : allArtists) {
            if (artist.getArtistId().equals(artistId)){
                artistName = artist.getArtistName();
            }
        }

        long durationInSec = allSongs.get(position).getDuration();
        durationInSec /= 1000;

        if (title.length() > 25){title= title.substring(0,25)+"...";}
        if (artistName.length() > 15){artistName= artistName.substring(0,15)+"...";}

        holder.songArtist.setText(artistName);
        holder.songDuration.setText(String.valueOf(convertDurationToMinutes(durationInSec)));
        holder.songTitle.setText(title);

        byte[] coverImage = getSongCover(path);

        if (coverImage != null){
            RequestOptions requestOptions = new RequestOptions()
                    .transform(new RoundedCorners(5));
            Glide.with(context)
                    .asBitmap()
                    .load(coverImage)
                    .override(70,70)
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