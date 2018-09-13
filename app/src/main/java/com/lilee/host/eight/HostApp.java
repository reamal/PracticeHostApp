package com.lilee.host.eight;

import android.app.Application;
import android.content.Context;

import com.lilee.pluginlib.PluginManager;

public class HostApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.init(this);
    }
}
