package mobiledev.unb.ca.datastore

import android.app.Activity
import android.content.Context
import android.widget.TextView
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.datastore.SaveGameInfo
import java.util.*

class MainActivity : Activity() {
    // Preferences data store
    private lateinit var gameInfo: SaveGameInfo

    private lateinit var gameScoreTextView: TextView
    private lateinit var highScoreTextView: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Initialize the preferences data store
        initSharedPreferences(applicationContext)

        // High Score
        highScoreTextView = findViewById(R.id.high_score_text)
        //var gameInfoScore = gameInfo.scoreFlow
        //Log.i("Test", gameInfoScore)
        highScoreTextView.text = gameInfo.scoreFlow.toString()
//        highScoreTextView.text = gameInfo.scoreFlow.collectAsState(0).value
        // readHighScoreFromSharedPreferences().toString()
//        gameInfo.scoreFlow.toString()

        //Game Score
        gameScoreTextView = findViewById(R.id.game_score_text)
        if (null == savedInstanceState) {
            gameScoreTextView.text = INITIAL_HIGH_SCORE
        } else {
            gameScoreTextView.text = savedInstanceState.getString(GAME_SCORE_KEY)
        }

        // Play Button
        val playButton = findViewById<Button>(R.id.play_button)
        playButton.setOnClickListener {
            val r = Random()
            val `val` = r.nextInt(1000)
            gameScoreTextView.text = `val`.toString()

            val currentHighScore = read
            // Get Stored High Score
//            val currHighScore = readHighScoreFromSharedPreferences()
//            if (`val` > currHighScore) {
//                // Get and edit high score
//                writeHighScoreToSharedPreferences(`val`)
//                highScoreTextView.text = `val`.toString()
//            }
        }

        // Reset Button
        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            // Reset the high score
//            writeHighScoreToSharedPreferences(0)
            highScoreTextView.text = INITIAL_HIGH_SCORE
            gameScoreTextView.text = INITIAL_HIGH_SCORE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(GAME_SCORE_KEY, gameScoreTextView.text.toString())
    }

    // Private Helper Methods
    private fun initSharedPreferences(context: Context) {
        gameInfo = SaveGameInfo(context)
    }

//    private fun writeHighScoreToSharedPreferences(score: Int) {
//        val editor = prefs!!.edit()
//        editor.putInt(HIGH_SCORE_KEY, score)
//        // commit saves immediately, apply handles it in the background
//        editor.apply()
//    }
//
    private fun readHighScoreFromSharedPreferences(): Int {
        return gameInfo.scoreFlow
        // return prefs!!.getInt(HIGH_SCORE_KEY, 0)
    }
//
//    suspend fun readHighScore() {
//        context.dataStore.edit { settings ->
//            val currentCounterValue = settings[HIGH_SCORE_KEY] ?: 0
//            settings[HIGH_SCORE_KEY] = currentCounterValue + 1
//        }
//    }

    companion object {
//        private const val PREFS_FILE_NAME = "AppPrefs"
//        private const val HIGH_SCORE_KEY = "HIGH_SCORE_KEY"
        private const val GAME_SCORE_KEY = "GAME_SCORE_KEY"
        private const val INITIAL_HIGH_SCORE = "0"
    }
}