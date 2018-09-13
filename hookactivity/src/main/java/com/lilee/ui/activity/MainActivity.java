package com.lilee.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lilee.hook.ams.AMSHookHelper;
import com.lilee.hook.classloader.LoadedApkClassLoaderHookHelper;
import com.lilee.hook.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "liTag";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            Utils.extractAssets(newBase, "hookPlugin.apk");

            LoadedApkClassLoaderHookHelper.hookLoadedApkInActivityThread(HookActApp.getContext(),
                    getFileStreamPath("hookPlugin.apk"));

            AMSHookHelper.hookAMN();
            AMSHookHelper.hookActivityThread();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button t = new Button(this);
        t.setText("test button");

        setContentView(t);

        Log.d(TAG, "context classloader: " + getApplicationContext().getClassLoader());
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent t = new Intent();
                    t.setComponent(
                            new ComponentName("com.lilee.hook.plugin",
                                    "com.lilee.hook.plugin.PluginActivity"));

                    startActivity(t);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
