package com.example.android.wakefulwork;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScheduledJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        //Trigger another event
        Intent refreshIntent = new Intent(this, AlarmEventService.class);
        startService(refreshIntent);

        //We don't want JobScheduler to track this job, we're "done"
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        //This method will not be called since we don't declare asynchronous work
        return false;
    }
}
