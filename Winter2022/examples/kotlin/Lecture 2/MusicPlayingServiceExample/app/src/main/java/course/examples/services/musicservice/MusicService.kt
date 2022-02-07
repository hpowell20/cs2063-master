package course.examples.services.musicservice

import android.app.*
import android.media.MediaPlayer
import android.os.Build
import android.content.Intent
import android.content.Context
import android.graphics.Color
import android.os.IBinder

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var mStartId = 0
    override fun onCreate() {
        super.onCreate()

        // Set up the Media Player
        mediaPlayer = MediaPlayer.create(this, R.raw.badnews)
        if (null != mediaPlayer) {
            mediaPlayer!!.isLooping = false

            // Stop Service when music has finished playing
            mediaPlayer!!.setOnCompletionListener { // stop Service if it was started with this ID
                // Otherwise let other start commands proceed
                stopSelf(mStartId)
            }
        }
        val context = applicationContext
        val channelId = context.packageName + ".channel_01"

        //Notification notification = buildNotification(context, channelId);
        createNotificationChannel(context, channelId)

        // Create a notification area notification so the user
        // can get back to the MusicServiceClient
        startBackgroundService(context, channelId)
    }

    private fun createNotificationChannel(context: Context, channelId: String) {
        // Only create the NotificationChannel if the using API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Set the user visible channel name
            val name: CharSequence = context.getString(R.string.channel_name)

            // Set the user visible channel description
            val description = context.getString(R.string.channel_description)

            // Set the channel importance
            val importance = NotificationManager.IMPORTANCE_NONE

            // Create the NotificationChannel object
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            channel.enableLights(true)
            channel.enableVibration(false)

            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            channel.lightColor = Color.GREEN

            // Register the channel with the system
            val notificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startBackgroundService(context: Context, channelId: String) {
        val notificationIntent = Intent(
            context,
            MusicServiceClient::class.java
        )
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = Notification.Builder(
            context, channelId
        )
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setOngoing(true).setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_text))
            .setContentIntent(pendingIntent).build()

        // Put this Service in a foreground state, so it won't
        // readily be killed by the system
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (null != mediaPlayer) {
            // ID for this start command
            mStartId = startId
            if (mediaPlayer!!.isPlaying) {
                // Rewind to beginning of song
                mediaPlayer!!.seekTo(0)
            } else {
                // Start playing song
                mediaPlayer!!.start()
            }
        }

        // Don't automatically restart this Service if it is killed
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        if (null != mediaPlayer) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
        }
    }

    // Can't bind to this Service
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}