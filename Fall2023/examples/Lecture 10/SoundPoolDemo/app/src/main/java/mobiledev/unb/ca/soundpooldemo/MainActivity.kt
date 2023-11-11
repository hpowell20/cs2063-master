package mobiledev.unb.ca.soundpooldemo

import androidx.appcompat.app.AppCompatActivity
import android.media.SoundPool
import android.os.Bundle
import android.media.AudioAttributes
import android.view.View

class MainActivity : AppCompatActivity() {
    private var soundPool: SoundPool? = null
    private var sound1 = 0
    private var sound2 = 0
    private var sound3 = 0
    private var sound4 = 0
    private var sound5 = 0
    private var sound6 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createSoundPool()

        sound1 = soundPool!!.load(this, R.raw.crackling_fireplace, 1)
        sound2 = soundPool!!.load(this, R.raw.thunder, 1)
        sound3 = soundPool!!.load(this, R.raw.formula1, 1)
        sound4 = soundPool!!.load(this, R.raw.airplane_landing, 1)
        sound5 = soundPool!!.load(this, R.raw.steam_train_whistle, 1)
        sound6 = soundPool!!.load(this, R.raw.tolling_bell, 1)
    }

    fun playSound(v: View) {
        when (v.id) {
            R.id.button_sound1 -> {
                soundPool!!.autoPause() // Pause all other sounds
                soundPool!!.play(sound1, 1f, 1f, 0, 0, 1f)
            }
            R.id.button_sound2 -> {
                soundPool!!.play(sound2, 1f, 1f, 0, 0, 1f)
            }
            R.id.button_sound3 -> {
                soundPool!!.play(sound3, 1f, 1f, 0, 0, 1f)
            }
            R.id.button_sound4 -> {
                soundPool!!.play(sound4, 1f, 1f, 0, 0, 1f)
            }
            R.id.button_sound5 -> {
                soundPool!!.play(sound5, 1f, 1f, 0, 0, 1f)
            }
            else -> {
                soundPool!!.play(sound6, 1f, 1f, 0, 0, 1f)
            }
        }
    }

    private fun createSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(SOUND_POOL_MAX_STREAMS)
            .setAudioAttributes(audioAttributes)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool!!.release()
        soundPool = null
    }

    companion object {
        private const val SOUND_POOL_MAX_STREAMS = 6
    }
}