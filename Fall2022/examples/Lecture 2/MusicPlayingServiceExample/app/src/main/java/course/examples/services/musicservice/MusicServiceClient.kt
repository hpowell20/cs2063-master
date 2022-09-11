package course.examples.services.musicservice

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button

class MusicServiceClient : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        // Intent used for starting the MusicService
        val musicServiceIntent = Intent(
            applicationContext,
            MusicService::class.java
        )
        val startButton = findViewById<View>(R.id.start_button) as Button
        startButton.setOnClickListener { // Start the MusicService using the Intent
            startService(musicServiceIntent)
        }
        val stopButton = findViewById<View>(R.id.stop_button) as Button
        stopButton.setOnClickListener { // Stop the MusicService using the Intent
            stopService(musicServiceIntent)
        }
    }
}