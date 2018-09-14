package com.lilee.zeusplugin;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lilee.zeuslib.PluginManager;
import com.lilee.zeuslib.ZeusBaseActivity;

public class TestActivity extends ZeusBaseActivity {

    private static final String TAG = "liTag";

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


        View view = getLayoutInflater().inflate(R.layout.activity_test, null);
        Log.d(TAG, "TestActivity view : " + (view == null));
        Button btn = view.findViewById(R.id.btn);
        Log.d(TAG, "TestActivity btn : " + (btn == null));

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
