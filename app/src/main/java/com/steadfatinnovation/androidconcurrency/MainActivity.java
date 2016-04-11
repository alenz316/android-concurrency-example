package com.steadfatinnovation.androidconcurrency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String THREAD_TAG = "THREADERS";

    public static final String SIMPLE_ACTION = "com.steadfatinnovation.androidconcurrency.simple";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(THREAD_TAG, this + " - " + getThreadString());

        View v = findViewById(R.id.start_service);

        if (v != null) {
            v.setOnClickListener(new View.OnClickListener() {
                int count = 0;

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, SimpleService.class);
                    i.putExtra(SimpleService.EXTRA_INT, count++);
                    startService(i);
                }
            });
        }

        v = findViewById(R.id.start_other_proc_service);
        if (v != null) {
            v.setOnClickListener(new View.OnClickListener() {
                int count = 0;

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, OtherProcessService.class);
                    i.putExtra(SimpleService.EXTRA_INT, count++);
                    startService(i);
                }
            });
        }

        v = findViewById(R.id.send_to_receiver);
        if (v != null) {
            v.setOnClickListener(new View.OnClickListener() {
                int count = 0;

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(SIMPLE_ACTION);
                    i.putExtra(SimpleBroadCastReceiver.EXTRA_INT, count++);
                    sendBroadcast(i);
                }
            });
        }
    }

    public static String getThreadString() {
        return "Thread - " + Thread.currentThread() + " ThreadObj 0x" + System.identityHashCode(Thread.currentThread());
    }
}
