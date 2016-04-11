package com.steadfatinnovation.androidconcurrency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LauncherActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);

        View v = findViewById(R.id.launch_main);

        if (v != null) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                }
            });
        }

        v = findViewById(R.id.launch_calc_main);

        if (v != null) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LauncherActivity.this, CalcMainActivity.class));
                }
            });
        }

        v = findViewById(R.id.launch_calc_threaded);

        if (v != null) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LauncherActivity.this, CalcThreadedActivity.class));
                }
            });
        }

        v = findViewById(R.id.launch_calc_threaded_right);

        if (v != null) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LauncherActivity.this, CalcThreadedRightActivity.class));
                }
            });
        }
    }
}
