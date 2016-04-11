package com.steadfatinnovation.androidconcurrency;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class OtherProcessService extends Service {
    public static final String EXTRA_INT = "extra_int";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(MainActivity.THREAD_TAG, this + " - " + MainActivity.getThreadString());
        Log.e(MainActivity.THREAD_TAG, "Extra Int " + intent.getIntExtra(EXTRA_INT, -1));

        if (intent.getIntExtra(EXTRA_INT, -1) == 4) {
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
