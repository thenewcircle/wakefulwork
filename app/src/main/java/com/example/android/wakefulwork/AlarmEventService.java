package com.example.android.wakefulwork;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class AlarmEventService extends IntentService {
    private static final String TAG = AlarmEventService.class.getSimpleName();

    public AlarmEventService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Service Triggered...");

        //Execute the workload...
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Work completed...");
        //Signal to the receiver it can release the wakelock
        AlarmEventReceiver.completeWakefulIntent(intent);
    }
}
