package course.examples.services.musicservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.IBinder;

public class MusicService extends Service {

    private static final int NOTIFICATION_ID = 1;
    private MediaPlayer mediaPlayer;
    private int mStartId;

    @Override
    public void onCreate() {
        super.onCreate();

        // Set up the Media Player
        mediaPlayer = MediaPlayer.create(this, R.raw.badnews);

        if (null != mediaPlayer) {
            mediaPlayer.setLooping(false);

            // Stop Service when music has finished playing
            mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // stop Service if it was started with this ID
                    // Otherwise let other start commands proceed
                    stopSelf(mStartId);
                }
            });
        }

        Context context = getApplicationContext();
        String channelId = context.getPackageName() + ".channel_01";

        //Notification notification = buildNotification(context, channelId);
        createNotificationChannel(context, channelId);

        // Create a notification area notification so the user
        // can get back to the MusicServiceClient
        startBackgroundService(context, channelId);
    }

    private void createNotificationChannel(Context context, String channelId) {
        // Only create the NotificationChannel if the using API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Set the user visible channel name
            CharSequence name = context.getString(R.string.channel_name);

            // Set the user visible channel description
            String description = context.getString(R.string.channel_description);

            // Set the channel importance
            int importance = NotificationManager.IMPORTANCE_NONE;

            // Create the NotificationChannel object
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            channel.enableLights(true);
            channel.enableVibration(false);

            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            channel.setLightColor(Color.GREEN);

            // Register the channel with the system
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void startBackgroundService(Context context, String channelId) {
        final Intent notificationIntent = new Intent(context,
                MusicServiceClient.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        final Notification notification = new Notification.Builder(
                context, channelId)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setOngoing(true).setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setContentIntent(pendingIntent).build();

        // Put this Service in a foreground state, so it won't
        // readily be killed by the system
        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null != mediaPlayer) {
            // ID for this start command
            mStartId = startId;

            if (mediaPlayer.isPlaying()) {
                // Rewind to beginning of song
                mediaPlayer.seekTo(0);
            } else {
                // Start playing song
                mediaPlayer.start();
            }
        }

        // Don't automatically restart this Service if it is killed
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (null != mediaPlayer) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    // Can't bind to this Service
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
