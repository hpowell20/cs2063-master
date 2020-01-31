package mobiledev.unb.ca.lab4skeleton;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Log receipt of the Intent with timestamp
        Log.i(TAG,context.getString(R.string.logging_at_string, DateFormat.getDateTimeInstance().format(new Date())));
    }
}
