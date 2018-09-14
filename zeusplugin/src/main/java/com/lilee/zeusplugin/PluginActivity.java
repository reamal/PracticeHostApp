package com.lilee.zeusplugin;

import android.os.Bundle;
import android.widget.TextView;

import com.lilee.zeuslib.ZeusBaseActivity;

public class PluginActivity extends ZeusBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        setContentView(tv);
        String info = getIntent().getStringExtra("info");
        tv.setText(info);
    }
}
