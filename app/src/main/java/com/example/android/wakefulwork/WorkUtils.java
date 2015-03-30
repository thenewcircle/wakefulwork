package com.example.android.wakefulwork;


import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class WorkUtils {
    private static final String TAG = WorkUtils.class.getSimpleName();
    private static final int WORK_JOB_ID = 42;

    //Handler for scheduling alarms on older platforms
    public static void scheduleWorkLegacy(Context context, long intervalMillis) {
        AlarmManager manager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent broadcast = new Intent(context, AlarmEventReceiver.class);
        PendingIntent trigger = PendingIntent.getBroadcast(context, 0, broadcast, 0);

        if (intervalMillis == 0) {
            //Clear the periodic alarm
            manager.cancel(trigger);
            Log.d(TAG, "Canceling periodic alarm...");
        } else {
            long now = System.currentTimeMillis();
            //Start an inexact alarm, with the first trigger one interval from now
            manager.setInexactRepeating(AlarmManager.RTC,
                    now + intervalMillis,
                    intervalMillis,
                    trigger);
            Log.d(TAG, "Scheduling periodic alarm for " + intervalMillis);
        }
    }

    //Handler for scheduling jobs on Lollipop+
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void scheduleWorkLollipop(Context context, long intervalMillis) {
        JobScheduler jobScheduler =
                (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        if (intervalMillis == 0) {
            //Cancel the periodic job
            jobScheduler.cancel(WORK_JOB_ID);
            Log.d(TAG, "Canceling periodic job");
        } else {
            JobInfo job = new JobInfo.Builder(WORK_JOB_ID,
                    new ComponentName(context.getPackageName(),
                            ScheduledJobService.class.getName()))
                    //Let the framework reschedule our job on device reboots
                    .setPersisted(true)
                    //Set the trigger interval
                    .setPeriodic(intervalMillis)
                    .build();

            jobScheduler.schedule(job);
            Log.d(TAG, "Scheduling job for " + intervalMillis);
        }
    }
}
