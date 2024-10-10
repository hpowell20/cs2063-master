package mobiledev.unb.ca.lab4skeleton

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class NotificationHelper {
    fun handleNotification(context: Context) {
        // TODO: Create the notification channel
        createNotificationChannel(context)

        // TODO: Create the intent
        val pendingTapIntent: PendingIntent = setTagPendingIntent(context)

        // TODO: Create the notification
        val builder = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentIntent(pendingTapIntent)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_text))

        // Show the notification
        showNotification(context, builder)
    }

    private fun createNotificationChannel(context: Context) {
        // Only create the NotificationChannel if the using API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Set the user visible channel name
            val name: CharSequence = context.getString(R.string.channel_name)

            // Set the user visible channel description
            val description = context.getString(R.string.channel_description)

            // Set the channel importance
            val importance = NotificationManager.IMPORTANCE_HIGH

            // Create the NotificationChannel object
            val channel = NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, name, importance)
            channel.description = description
            channel.enableLights(true)

            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            channel.lightColor = Color.GREEN
            channel.enableVibration(true)

            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setTagPendingIntent(context: Context): PendingIntent {
        // Set the tap action
        // FLAG_ACTIVITY_NEW_TASK - This flag is used to create a new task and launch an activity into it
        // FLAG_ACTIVITY_CLEAR_TASK - If set in an Intent passed to startActivity()
        //     this flag will cause a newly launching task to be placed on top of the current
        //     home activity task (if there is one)
        // This combination sets the Activity to start in a new empty task
        val tapIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        return  PendingIntent.getActivity(context,
            0,
            tapIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun showNotification(context: Context, builder: NotificationCompat.Builder) {
        with(NotificationManagerCompat.from(context)) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(Constants.NOTIFICATION_REQUEST_ID, builder.build())
        }
    }
}