package mobiledev.unb.ca.sharedpreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class ReadWriteActivity extends Activity {
    private static final String PREFS_FILE_NAME = "AppPrefs";
    private static final String HIGH_SCORE_KEY = "HIGH_SCORE_KEY";
    private static final String GAME_SCORE_KEY = "GAME_SCORE_KEY";
    private static final String INITIAL_HIGH_SCORE = "0";

    private SharedPreferences prefs;
    private TextView mGameScore, mHighScore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initSharedPreferences();

        // High Score
        mHighScore = findViewById(R.id.high_score_text);
        mHighScore.setText(String.valueOf(readHighScoreFromSharedPreferences()));

        //Game Score
        mGameScore = findViewById(R.id.game_score_text);
        if (null == savedInstanceState) {
            mGameScore.setText(INITIAL_HIGH_SCORE);
        } else {
            mGameScore.setText(savedInstanceState.getString(GAME_SCORE_KEY));
        }

        // Play Button
        final Button playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int val = r.nextInt(1000);
                mGameScore.setText(String.valueOf(val));

                // Get Stored High Score
                int currHighScore = readHighScoreFromSharedPreferences();
                if (val > currHighScore) {
                    // Get and edit high score
                    writeHighScoreToSharedPreferences(val);
                    mHighScore.setText(String.valueOf(val));
                }
            }
        });

        // Reset Button
        final Button resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the high score
                writeHighScoreToSharedPreferences(0);
                mHighScore.setText(INITIAL_HIGH_SCORE);
                mGameScore.setText(INITIAL_HIGH_SCORE);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_SCORE_KEY, mGameScore.getText().toString());
    }

    // Private Helper Methods
    private void initSharedPreferences() {
        prefs = getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    private void writeHighScoreToSharedPreferences(int score) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(HIGH_SCORE_KEY, score);
        editor.commit();
    }

    private int readHighScoreFromSharedPreferences() {
        return prefs.getInt(HIGH_SCORE_KEY, 0);
    }
}