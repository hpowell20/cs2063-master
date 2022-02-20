package mobiledev.unb.ca.sharedpreferences

import android.app.Activity
import android.content.SharedPreferences
import android.widget.TextView
import android.os.Bundle
import android.widget.Button
import java.util.*

class ReadWriteActivity : Activity() {
    private var prefs: SharedPreferences? = null
    private lateinit var mGameScore: TextView
    private lateinit var mHighScore: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        initSharedPreferences()

        // High Score
        mHighScore = findViewById(R.id.high_score_text)
        mHighScore.text = readHighScoreFromSharedPreferences().toString()

        //Game Score
        mGameScore = findViewById(R.id.game_score_text)
        if (null == savedInstanceState) {
            mGameScore.text = INITIAL_HIGH_SCORE
        } else {
            mGameScore.text = savedInstanceState.getString(GAME_SCORE_KEY)
        }

        // Play Button
        val playButton = findViewById<Button>(R.id.play_button)
        playButton.setOnClickListener {
            val r = Random()
            val `val` = r.nextInt(1000)
            mGameScore.text = `val`.toString()

            // Get Stored High Score
            val currHighScore = readHighScoreFromSharedPreferences()
            if (`val` > currHighScore) {
                // Get and edit high score
                writeHighScoreToSharedPreferences(`val`)
                mHighScore.text = `val`.toString()
            }
        }

        // Reset Button
        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            // Reset the high score
            writeHighScoreToSharedPreferences(0)
            mHighScore.text = INITIAL_HIGH_SCORE
            mGameScore.text = INITIAL_HIGH_SCORE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(GAME_SCORE_KEY, mGameScore.text.toString())
    }

    // Private Helper Methods
    private fun initSharedPreferences() {
        prefs = getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE)
    }

    private fun writeHighScoreToSharedPreferences(score: Int) {
        val editor = prefs!!.edit()
        editor.putInt(HIGH_SCORE_KEY, score)
        // commit saves immediately, apply handles it in the background
        editor.apply()
    }

    private fun readHighScoreFromSharedPreferences(): Int {
        return prefs!!.getInt(HIGH_SCORE_KEY, 0)
    }

    companion object {
        private const val PREFS_FILE_NAME = "AppPrefs"
        private const val HIGH_SCORE_KEY = "HIGH_SCORE_KEY"
        private const val GAME_SCORE_KEY = "GAME_SCORE_KEY"
        private const val INITIAL_HIGH_SCORE = "0"
    }
}