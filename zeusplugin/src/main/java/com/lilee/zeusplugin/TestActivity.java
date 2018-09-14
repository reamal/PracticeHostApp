package com.lilee.zeusplugin;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lilee.zeuslib.ZeusBaseActivity;

public class TestActivity extends ZeusBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        setContentView(tv);
        tv.setText("testActivity");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();

                    String activityName = "com.lilee.zeusplugin.PluginActivity";
                    intent.setComponent(new ComponentName("com.lilee.zeusplugin", activityName));
                    intent.putExtra("info", "test activity intent");

                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent intent = new Intent();
//
//                    String activityName = "com.lilee.zeusplugin.PluginActivity";
//                    intent.setComponent(new ComponentName("com.lilee.zeusplugin", activityName));
//                    intent.putExtra("info", "test activity intent");
//
//                    startActivity(intent);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}
