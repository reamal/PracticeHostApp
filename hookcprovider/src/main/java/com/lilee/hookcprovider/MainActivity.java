package com.lilee.hookcprovider;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // demo ContentProvider 的URI
    private static Uri URI = Uri.parse("content://Lilee");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button insert = new Button(this);
        insert.setText("delete");
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                Integer count = getContentResolver().delete(URI, "where", null);
                Toast.makeText(MainActivity.this, String.valueOf(count), Toast.LENGTH_LONG).show();
            }
        });

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(insert);

        setContentView(layout);
    }
}
