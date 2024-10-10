package mobiledev.unb.ca.lab4skeleton

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.text.DateFormat
import java.util.Date

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Handle sending of the notification
        NotificationHelper().handleNotification(context)

        // Log occurrence of notify() call
        val logMessage = String.format("Sending notification at: %S", DateFormat.getDateTimeInstance().format(Date()))
        Log.i(TAG, logMessage)
    }

    companion object {
        private const val TAG = "AlarmReceiver"
    }
}