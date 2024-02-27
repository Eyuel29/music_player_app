package com.joel.musicplayer;

import android.Manifest;
import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;

import com.joel.musicplayer.model.audio.AudioModel;
import com.joel.musicplayer.services.AudioReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AudioReaderTest {

    private AudioReader audioReader;
    private Context context;

    @Before
    public void setUp(){
        audioReader = new AudioReader(context);
        context = InstrumentationRegistry.getInstrumentation().getContext();
    }


    @Test
    public void readArtists(){
        List<AudioModel> retrievedFiles = audioReader.readAudioForUpToBuild33();
    }

    @After
    public void tearDown(){

    }

}
