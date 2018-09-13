package com.lilee.firstplugin;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.lilee.pluginlib.ZeusBaseActivity;

public class DefaultActivity extends ZeusBaseActivity {

    private static final String TAG = "liTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        Log.e(TAG,"first default activity onCreate");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                com.lilee.host.eight.HostActivity()
                Intent intent = new Intent();

                String activityName = "com.lilee.host.eight.HostActivity";
                intent.setComponent(new ComponentName("com.lilee.host.eight", activityName));

                startActivity(intent);
            }
        },2000);
    }
}
