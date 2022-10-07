package mobiledev.unb.ca.lab4skeleton

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.DateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        // Set the tap action
        // FLAG_ACTIVITY_NEW_TASK - This flag is used to create a new task and launch an activity into it
        // FLAG_ACTIVITY_CLEAR_TASK - If set in an Intent passed to startActivity()
        //     this flag will cause a newly launching task to be placed on top of the current
        //     home activity task (if there is one)
        // This combination sets the Activity to start in a new empty task
        val tapIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingTapIntent: PendingIntent = PendingIntent.getActivity(context,
            0,
            tapIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        // Create the Notification
        val builder = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentIntent(pendingTapIntent)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_text))

        // Show the notification
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFICATION_ID, builder.build())
        }

        // Log occurrence of notify() call
        Log.i(
            TAG, context.getString(
                R.string.send_notification_at,
                DateFormat.getDateTimeInstance().format(Date())
            )
        )
    }

    companion object {
        private const val TAG = "AlarmReceiver"
        private const val NOTIFICATION_ID = 1
    }
}