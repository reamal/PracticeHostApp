package com.lilee.dhostapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lilee.dhostapp.bean.PluginInfo;
import com.lilee.plugin.utils.DLUtils;
import com.lilee.plugin.utils.Utils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import dalvik.system.DexClassLoader;

public class BaseActivity extends AppCompatActivity {

    final String TAG = "Lilee";
    final String firstApkName = "firstPlugin.apk";
    final String secondApkName = "secondPlugin.apk";
    protected HashMap<String, PluginInfo> plugins = new HashMap<String, PluginInfo>();


    private AssetManager mAssetManager;
    private boolean pluginRes;
    private Resources mResources;
    private Resources.Theme mTheme;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        //把Assets里面得文件复制到 /data/data/files 目录下
        Utils.extractAssets(newBase, firstApkName);
        Utils.extractAssets(newBase, secondApkName);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generatePluginInfo(firstApkName);
        generatePluginInfo(secondApkName);
    }

    private void generatePluginInfo(String apkName) {
        //加载插件apk重的dex
        File extractFile = this.getFileStreamPath(apkName);
        String dexPath = extractFile.getPath();

        File fileRelease = getDir("dex", Context.MODE_PRIVATE);

        Log.d(TAG, "dexPath : " + dexPath);
        Log.d(TAG, "fileRelease.getAbsolutePath() : " + fileRelease.getAbsolutePath());

        DexClassLoader dexClassLoader = new DexClassLoader(dexPath, fileRelease.getAbsolutePath(),
                null, getClassLoader());

        String dexAbsolutePath = extractFile.getAbsolutePath();
        PackageInfo packageInfo = DLUtils.getPackageInfo(this, dexAbsolutePath);

        plugins.put(apkName, new PluginInfo(dexPath, dexClassLoader, packageInfo));
    }


    //加载plugin apk 中的 assetManager resources theme .
    public void loadResources(String dexPath, Runnable runnable) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, dexPath);
            mAssetManager = assetManager;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        mResources = new Resources(mAssetManager, super.getResources().getDisplayMetrics(), super.getResources().getConfiguration());

//        mTheme = mResources.newTheme();
//        mTheme.setTo(super.getTheme());

        pluginRes = true;
        runnable.run();
        pluginRes = false;
    }


    @Override
    public AssetManager getAssets() {
        if (pluginRes) {
            return mAssetManager == null ? super.getAssets() : mAssetManager;
        } else {
            return super.getAssets();
        }
    }

    @Override
    public Resources getResources() {
        if (pluginRes) {
            return mResources == null ? super.getResources() : mResources;
        } else {
            return super.getResources();
        }
    }

    @Override
    public Resources.Theme getTheme() {
        if (pluginRes) {
            return mTheme == null ? super.getTheme() : mTheme;
        } else {
            return super.getTheme();
        }
    }
}
