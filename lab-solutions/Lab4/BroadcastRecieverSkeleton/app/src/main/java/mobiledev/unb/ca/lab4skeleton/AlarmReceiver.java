package mobiledev.unb.ca.lab4skeleton;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";
    private static final int NOTIFICATION_ID = 1;
    //private static final String CHANNEL_ID = BuildConfig.APPLICATION_ID + ".channel_01";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Log receipt of the Intent with timestamp
        Log.i(TAG, context.getString(R.string.logging_at_string, DateFormat.getDateTimeInstance().format(new Date())));

        // Create the notification channel
        String channelId = context.getPackageName() + ".channel_01";
        createNotificationChannel(context, channelId);

        // Set the tap action
        Intent tapIntent = new Intent(context, MainActivity.class);
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
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
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
