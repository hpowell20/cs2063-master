package mobiledev.unb.ca.lab4skeleton;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.DateFormat;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Create the notification channel
        String channelId = context.getPackageName() + ".channel_01";
        createNotificationChannel(context, channelId);

        // Set the tap action
        Intent tapIntent = new Intent(context, MainActivity.class);
        // FLAG_ACTIVITY_NEW_TASK - This flag is used to create a new task and launch an activity into it
        // FLAG_ACTIVITY_CLEAR_TASK - If set in an Intent passed to startActivity()
        //     this flag will cause a newly launching task to be placed on top of the current
        //     home activity task (if there is one)
        // This combination sets the Activity to start in a new empty task
        tapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingTapIntent = PendingIntent.getActivity(context, 0, tapIntent, 0);

        // Create the Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingTapIntent)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_text));

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

        // Log occurrence of notify() call
        Log.i(TAG, context.getString(R.string.send_notification_at, DateFormat.getDateTimeInstance().format(new Date())));
    }

    private void createNotificationChannel(Context context, String channelId) {
        // Only create the NotificationChannel if the using API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Set the user visible channel name
            CharSequence name = context.getString(R.string.channel_name);

            // Set the user visible channel description
            String description = context.getString(R.string.channel_description);

            // Set the channel importance
            int importance = NotificationManager.IMPORTANCE_HIGH;

            // Create the NotificationChannel object
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);

            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);

            // Register the channel with the system
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
