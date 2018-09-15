package com.lilee.hookservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.lilee.IMyInterface;
import com.lilee.hookservice.amshook.AMSHookHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String apkName = "zeusPlugin.apk";
    public static final String TAG = "liTag";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            Utils.extractAssets(newBase, apkName);

            File dexFile = getFileStreamPath(apkName);
            File optDexFile = getFileStreamPath("zeusPlugin.dex");
            BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), dexFile, optDexFile);

            AMSHookHelper.hookAMN();
            AMSHookHelper.hookActivityThread();

            String strJSON = Utils.readZipFileString(dexFile.getAbsolutePath(), "assets/plugin_config.json");
            if (strJSON != null && !TextUtils.isEmpty(strJSON)) {
                JSONObject jObject = new JSONObject(strJSON.replaceAll("\r|\n", ""));
                JSONArray jsonArray = jObject.getJSONArray("plugins");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    HookApp.pluginServices.put(
                            jsonObject.optString("PluginService"),
                            jsonObject.optString("StubService"));
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View view) {
        Intent intent = new Intent();
        intent.setComponent(
                new ComponentName("com.lilee.zeusplugin",
                        "com.lilee.zeusplugin.TestService"));

        startService(intent);
    }

    public void bindService(View view) {
        final Intent intent = new Intent();
        intent.setComponent(
                new ComponentName("com.lilee.zeusplugin",
                        "com.lilee.zeusplugin.TestService"));
        bindService(intent,conn, Service.BIND_AUTO_CREATE);
    }

    public void unbindService(View view) {
        unbindService(conn);
    }

    public void stopService(View view) {
        final Intent intent = new Intent();
        intent.setComponent(
                new ComponentName("com.lilee.zeusplugin",
                        "com.lilee.zeusplugin.TestService"));
        stopService(intent);
    }


    ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            IMyInterface a = (IMyInterface) service;
            int result = a.getCount();
            Log.e(TAG, String.valueOf(result));
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };


}
