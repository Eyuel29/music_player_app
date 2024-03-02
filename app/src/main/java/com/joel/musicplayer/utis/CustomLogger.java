package com.joel.musicplayer.utis;

import android.os.Looper;
import android.util.Log;

public class CustomLogger {
    private static final String TAG = "CUSTOM_LOGGER";

    public static void logD(String message) {
        String threadType;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            threadType = "Main Thread";
        } else {
            threadType = "Worker Thread";
        }
        Log.d(TAG, "[" + threadType + "] " + message);
    }
}
