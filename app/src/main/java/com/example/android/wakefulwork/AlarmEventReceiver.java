package com.example.android.wakefulwork;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class AlarmEventReceiver extends WakefulBroadcastReceiver {
    private static final String TAG = AlarmEventReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Event Received...");

        Intent workIntent = new Intent(context, AlarmEventService.class);
        startWakefulService(context, workIntent);
    }
}
