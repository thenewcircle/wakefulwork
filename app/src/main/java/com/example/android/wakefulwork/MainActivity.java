package com.example.android.wakefulwork;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class MainActivity extends ActionBarActivity {

    public static final String KEY_INTERVAL = "selectedInterval";
    public static final String KEY_INDEX = "selectedIndex";

    private static final String[] CHOICES = {
            "Disabled",
            "15 seconds",
            "1 minute",
            "5 minutes",
            "30 minutes",
            "1 hours"
    };

    private static final long[] VALUES = {
            0,
            15000,
            60000,
            300000,
            1800000,
            3600000
    };

    private Spinner mIntervalSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIntervalSpinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_spinner_item, CHOICES);

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        mIntervalSpinner.setAdapter(adapter);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int savedIndex = prefs.getInt(KEY_INDEX, 0);
        mIntervalSpinner.setSelection(savedIndex);
    }

    public void onScheduleClick(View v) {
        //Get the selected value
        final int selectedIndex = mIntervalSpinner.getSelectedItemPosition();
        final long selectedInterval = VALUES[selectedIndex];
        //Persist the current choice
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putInt(KEY_INDEX, selectedIndex)
                .putLong(KEY_INTERVAL, selectedInterval)
                .apply();

        //Schedule the periodic work
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WorkUtils.scheduleWorkLollipop(this, selectedInterval);
        } else {
            WorkUtils.scheduleWorkLegacy(this, selectedInterval);
        }
    }
}
