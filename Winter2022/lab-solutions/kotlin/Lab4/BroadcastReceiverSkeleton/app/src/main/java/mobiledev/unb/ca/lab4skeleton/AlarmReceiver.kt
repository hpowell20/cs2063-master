package mobiledev.unb.ca.lab4skeleton

import android.content.BroadcastReceiver
import android.content.Intent
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.os.Build
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import android.graphics.Color
import android.util.Log
import java.text.DateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Create the notification channel
        val channelId = context.packageName + ".channel_01"
        createNotificationChannel(context, channelId)

        // Set the tap action
        val tapIntent = Intent(context, MainActivity::class.java)
        // FLAG_ACTIVITY_NEW_TASK - This flag is used to create a new task and launch an activity into it
        // FLAG_ACTIVITY_CLEAR_TASK - If set in an Intent passed to startActivity()
        //     this flag will cause a newly launching task to be placed on top of the current
        //     home activity task (if there is one)
        // This combination sets the Activity to start in a new empty task
        tapIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingTapIntent = PendingIntent.getActivity(
            context,
            0,
            tapIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Create the Notification
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentIntent(pendingTapIntent)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_text))

        // Show the notification
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID, builder.build())

        // Log occurrence of notify() call
        Log.i(
            TAG, context.getString(
                R.string.send_notification_at,
                DateFormat.getDateTimeInstance().format(Date())
            )
        )
    }

    private fun createNotificationChannel(context: Context, channelId: String) {
        // Only create the NotificationChannel if the using API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Set the user visible channel name
            val name: CharSequence = context.getString(R.string.channel_name)

            // Set the user visible channel description
            val description = context.getString(R.string.channel_description)

            // Set the channel importance
            val importance = NotificationManager.IMPORTANCE_HIGH

            // Create the NotificationChannel object
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.enableLights(true)

            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            channel.lightColor = Color.GREEN
            channel.enableVibration(true)

            // Register the channel with the system
            val notificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val TAG = "AlarmReceiver"
        private const val NOTIFICATION_ID = 1
    }
}