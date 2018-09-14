package com.lilee.zeuslib;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;

/**
 * 基础的activity
 * Created by huangjian on 2016/6/21.
 */
public class ZeusBaseActivity extends Activity {

    private static final String TAG = "liTag";

    @Override
    public Resources getResources() {
        Log.d(TAG, "ZeusBaseActivity getResources : " + (PluginManager.mNowResources == null));
        return PluginManager.mNowResources;
    }
}
