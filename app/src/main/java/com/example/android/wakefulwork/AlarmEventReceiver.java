package com.example.android.wakefulwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class AlarmEventReceiver extends WakefulBroadcastReceiver {
    private static final String TAG = AlarmEventReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Event Received...");
        //If this is a fresh boot on a legacy system, we need to reschedule the alarm
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            long savedInterval = prefs.getLong(MainActivity.KEY_INTERVAL, 0);
            WorkUtils.scheduleWorkLegacy(context, savedInterval);

            return;
        }

        Intent workIntent = new Intent(context, AlarmEventService.class);
        startWakefulService(context, workIntent);
    }
}
