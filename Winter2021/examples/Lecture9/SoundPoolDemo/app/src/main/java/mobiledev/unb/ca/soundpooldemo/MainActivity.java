package mobiledev.unb.ca.soundpooldemo;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int SOUND_POOL_MAX_STREAMS = 6;

    private SoundPool soundPool;
    private int sound1, sound2, sound3, sound4, sound5, sound6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }

        sound1 = soundPool.load(this, R.raw.crackling_fireplace, 1);
        sound2 = soundPool.load(this, R.raw.thunder, 1);
        sound3 = soundPool.load(this, R.raw.formula1, 1);
        sound4 = soundPool.load(this, R.raw.airplane_landing, 1);
        sound5 = soundPool.load(this, R.raw.steam_train_whistle, 1);
        sound6 = soundPool.load(this, R.raw.tolling_bell, 1);
    }

    public void playSound(View v) {
        switch (v.getId()) {
            case R.id.button_sound1:
                soundPool.autoPause(); // Pause all other sounds
                soundPool.play(sound1, 1, 1, 0, 0, 1);
                break;
            case R.id.button_sound2:
                soundPool.play(sound2, 1, 1, 0, 0, 1);
                break;
            case R.id.button_sound3:
                soundPool.play(sound3, 1, 1, 0, 0, 1);
                break;
            case R.id.button_sound4:
                soundPool.play(sound4, 1, 1, 0, 0, 1);
                break;
            case R.id.button_sound5:
                soundPool.play(sound5, 1, 1, 0, 0, 1);
                break;
            case R.id.button_sound6:
                soundPool.play(sound6, 1, 1, 0, 0, 1);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool(){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(SOUND_POOL_MAX_STREAMS)
                .setAudioAttributes(audioAttributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool(){
        soundPool = new SoundPool(SOUND_POOL_MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}
