package com.lilee.zeusplugin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class PluginReceiver2 extends BroadcastReceiver {

    private static final String TAG = "liTag";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,
                "接收到200万" + intent.getAction() + intent.getStringExtra("msg"),
                Toast.LENGTH_LONG).show();
        Log.d(TAG,"PluginReceiver2 onReceive ");

        context.sendBroadcast(new Intent("plugin_broadcast"));
    }
}
