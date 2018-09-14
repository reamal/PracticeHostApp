package com.lilee.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lilee.hook.ams.AMSHookHelper;
import com.lilee.hook.classloader.BaseDexClassLoaderHookHelper;
import com.lilee.hook.utils.Utils;
import com.lilee.hook2act.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "liTag";
    private static final String apkName = "hookPlugin";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            Utils.extractAssets(newBase, apkName + ".apk");

            File dexFile = getFileStreamPath(apkName + ".apk");
            File optDexFile = getFileStreamPath(apkName + ".dex");
            BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), dexFile, optDexFile);

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
