package course.examples.services.musicservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MusicServiceClient extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		
		// Intent used for starting the MusicService
		final Intent musicServiceIntent = new Intent(getApplicationContext(),
				MusicService.class);

		final Button startButton = findViewById(R.id.start_button);
		startButton.setOnClickListener(src -> {
			// Start the MusicService using the Intent
			startService(musicServiceIntent);
		});

		final Button stopButton = findViewById(R.id.stop_button);
		stopButton.setOnClickListener(src -> {
			// Stop the MusicService using the Intent
			stopService(musicServiceIntent);
		});
	}
}