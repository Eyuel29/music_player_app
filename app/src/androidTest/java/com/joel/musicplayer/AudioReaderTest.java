package com.joel.musicplayer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import com.joel.musicplayer.activities.MainActivity;
import com.joel.musicplayer.model.audio.AudioModel;
import com.joel.musicplayer.services.AudioReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;

public class AudioReaderTest {

    private AudioReader audioReader;
    private Context context;

    @Before
    public void setUp(){
        audioReader = new AudioReader(context);
        context = InstrumentationRegistry.getInstrumentation().getContext();
    }

    public void checkPermission(){
        askPermission();
        if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ){
            askPermission();
        }
    }

    public void askPermission(){
        ActivityCompat.requestPermissions(new MainActivity(),new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE },1);
        checkPermission();
    }


    @Test
    public void readArtists(){
        List<AudioModel> retrievedFiles = audioReader.readAudioForUpToBuild33();
    }

    @After
    public void tearDown(){

    }

}
