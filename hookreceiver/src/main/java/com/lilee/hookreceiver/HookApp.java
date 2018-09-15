package com.lilee.hookreceiver;

import android.app.Application;
import android.content.Context;

import java.io.File;

public class HookApp extends Application {

    String apkName = "zeusPlugin.apk";
    String dexName = "zeusPlugin.dex";

    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        context = this;
        //解压到本地
        Utils.extractAssets(this, apkName);

        File dexFile = getFileStreamPath(apkName);
        File optDexFile = getFileStreamPath(dexName);

        try {
            BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), dexFile, optDexFile);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        File testPlugin = getFileStreamPath(apkName);
        ReceiverHelper.preLoadReceiver(this, testPlugin);
    }

    public static Context getContext() {
        return context;
    }
}
