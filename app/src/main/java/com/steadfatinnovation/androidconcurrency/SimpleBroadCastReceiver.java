package com.steadfatinnovation.androidconcurrency;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SimpleBroadCastReceiver extends BroadcastReceiver {

    public static final String EXTRA_INT = "extra_int";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(MainActivity.THREAD_TAG, this + " - " + MainActivity.getThreadString());
        Log.e(MainActivity.THREAD_TAG, "Extra Int " + intent.getIntExtra(EXTRA_INT, -1));
    }

}
