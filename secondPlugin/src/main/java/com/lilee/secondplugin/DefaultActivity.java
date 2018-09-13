package com.lilee.secondplugin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lilee.pluginlib.ZeusBaseActivity;

public class DefaultActivity extends ZeusBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
    }
}
