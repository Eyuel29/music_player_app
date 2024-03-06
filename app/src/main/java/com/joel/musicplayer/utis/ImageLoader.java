package com.joel.musicplayer.utis;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.joel.musicplayer.R;

public class ImageLoader {
    public static void loadSongImage(Context context, String path, ImageView view){

        byte[] data = getSongImageData(path);
        if (data != null){
            Glide.with(context)
                    .asBitmap()
                    .load(data)
                    .into(view);
        }else{
            Glide.with(context)
                    .asBitmap()
                    .load(R.drawable.music_player)
                    .into(view);
        }
    }

    private static  byte[] getSongImageData(String path){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        byte[] data = null;
        try {
            data = retriever.getEmbeddedPicture();
            retriever.release();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  data;
    }

}
