package com.lilee.hookcprovider;

import android.app.Application;
import android.content.Context;

import java.io.File;

public class HookApp extends Application {

    private static final String pluginName = "zeusPlugin";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            File apkFile = getFileStreamPath(pluginName + ".apk");
            if (!apkFile.exists()) {
                Utils.extractAssets(base, pluginName + ".apk");
            }

            File odexFile = getFileStreamPath(pluginName + ".odex");

            // Hook ClassLoader, 让插件中的类能够被成功加载
            BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), apkFile, odexFile);

            //安装插件中的Providers
            ProviderHelper.installProviders(base, getFileStreamPath(pluginName + ".apk"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
