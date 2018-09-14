package com.lilee.zeus14;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lilee.zeuslib.PluginManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService1InPlugin1(View view) {
        try {
            Intent intent = new Intent();
            String serviceName = PluginManager.plugins.get(0).packageInfo.packageName + ".TestService";
            intent.setClass(this, getClassLoader().loadClass(serviceName));
            startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startActivityInPlugin1(View view) {
        try {
            Intent intent = new Intent();
            String activityName = PluginManager.plugins.get(0).packageInfo.packageName + ".TestActivity";
            intent.setComponent(new ComponentName(PluginManager.plugins.get(0).packageInfo.packageName, activityName));

            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
