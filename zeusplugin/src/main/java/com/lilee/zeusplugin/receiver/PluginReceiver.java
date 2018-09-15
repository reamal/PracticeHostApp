package com.lilee.zeusplugin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class PluginReceiver extends BroadcastReceiver {
    private static final String TAG = "liTag";

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context,
//                "接收到100万" + intent.getAction() + intent.getStringExtra("msg"),
//                Toast.LENGTH_LONG).show();
        Log.d(TAG,"PluginReceiver onReceive ");
        context.sendBroadcast(new Intent("plugin_receiver2"));
    }
}
