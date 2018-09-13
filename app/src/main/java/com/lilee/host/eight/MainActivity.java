package com.lilee.host.eight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lilee.pluginlib.PluginManager;

import static com.lilee.pluginlib.PluginManager.FIRST_PLUGIN_NAME;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    String actName = PluginManager.plugins.get(FIRST_PLUGIN_NAME).packageInfo.packageName + ".DefaultActivity";
                    intent.setClass(MainActivity.this, Class.forName(actName));
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
