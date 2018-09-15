package com.lilee.dhostapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.lilee.dhostapp.utils.BaseDexClassLoaderHookHelper;
import com.lilee.plugin.utils.PluginItem;
import com.lilee.plugin.utils.PluginManager;

import java.io.File;

public class HostApp extends Application {

    final String TAG = "Lilee";

    private static final String apkName = "firstPlugin.apk";
    private static final String dexName = "classes.dex";

    private static HostApp instance;

    public static HostApp getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        PluginManager.init(this);

//        File dexFile = getFileStreamPath(apkName);
//        File optDexFile = getFileStreamPath(dexName);
//        Log.e(TAG, "patchClassLoader apkName :" + dexFile.getAbsolutePath());
//        Log.e(TAG, "patchClassLoader dexName :" + optDexFile.getAbsolutePath() + "， getTotalSpace : " + optDexFile.getTotalSpace());
//
//        try {
//            BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), dexFile, optDexFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(TAG, "patchClassLoader error :" + e.getMessage());
//        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        pluginsOnCreate();
    }

    private void pluginsOnCreate() {
        for (PluginItem pluginItem : PluginManager.plugins) {
            try {
                //com.lilee.pluginone.PluginOneApp
                Class clazz = PluginManager.mNowClassLoader.loadClass(pluginItem.applicationName);
                Application application = (Application) clazz.newInstance();
                if (application == null) {
                    continue;
                }
                application.onCreate();
            } catch (ClassNotFoundException e) {

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
