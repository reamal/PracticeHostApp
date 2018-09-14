package com.lilee.zeus14;

import android.app.Application;
import android.content.Context;

import com.lilee.zeus14.amshook.AMSHookHelper;
import com.lilee.zeuslib.PluginManager;

import java.util.HashMap;

public class ZeusApp extends Application {

    public static HashMap<String, String> pluginActivies = new HashMap<String, String>();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.init(this);
        //get json data from server
        mockData();

        try {
            AMSHookHelper.hookAMN();
            AMSHookHelper.hookActivityThread();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    void mockData() {
        pluginActivies.put("com.lilee.zeusplugin.PluginActivity", "com.lilee.zeus14.SingleTopActivity1");
        pluginActivies.put("com.lilee.zeusplugin.TestActivity", "com.lilee.zeus14.SingleTaskActivity1");
    }
}
