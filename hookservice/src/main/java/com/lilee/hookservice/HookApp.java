package com.lilee.hookservice;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;

public class HookApp extends Application {

    private static Context sContext;

    public static HashMap<String, String> pluginServices;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = base;

        pluginServices = new HashMap<String, String>();
    }

    public static Context getContext() {
        return sContext;
    }
}
