package com.lilee.plugintwo;

import android.app.Application;
import android.util.Log;

public class SecondPluginApp extends Application{

    final String TAG = "Lilee";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"SecondPluginApp onCreate");
    }
}
