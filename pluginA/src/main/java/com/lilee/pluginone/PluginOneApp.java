package com.lilee.pluginone;

import android.app.Application;
import android.util.Log;

public class PluginOneApp extends Application {

    final String TAG = "Lilee";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"PluginOneApp onCreate");
    }
}
