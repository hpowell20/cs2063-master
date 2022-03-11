package mobiledev.unb.ca.labexam.helper

import android.content.SharedPreferences

class SharedPreferencesHelper
/**
 * Constructor with dependency injection.
 * @param sharedPreferences The [SharedPreferences] that will be used
 */(
    // The injected SharedPreferences implementation to use for persistence
    private val sharedPreferences: SharedPreferences,
) {
    /**
     * Saves the given values
     *
     * @param sharedPreferencesEntry contains data to save to [SharedPreferences].
     * @return `true` if writing to [SharedPreferences] succeeded. `false`
     * otherwise.
     */
    fun saveCheckedState(sharedPreferencesEntry: SharedPreferencesEntry): Boolean {
        // TODO: SharedPreferences
        //  Get a SharedPreferences.Editor for SharedPreferences
        //  Hint: https://developer.android.com/reference/android/content/SharedPreferences.html#edit()
        val editor = sharedPreferences.edit()

        // TODO: Shared Preferences
        //  Set the value stored in SharedPreferences for the id for this GamesInfo to be
        //  the value of isChecked
        //  Hint: https://developer.android.com/reference/android/content/SharedPreferences.Editor.html#putBoolean(java.lang.String,%20boolean)
        editor.putBoolean(sharedPreferencesEntry.number, sharedPreferencesEntry.checked)

        // TODO: SharedPreferences
        //  Apply the changes from this editor
        //  Hint: https://developer.android.com/reference/android/content/SharedPreferences.Editor.html#commit()
        return editor.commit()
    }

    /**
     * Retrieves the latest stored values
     *
     * @return the stored values [SharedPreferencesEntry].
     */
    fun readCheckedState(number: String?): SharedPreferencesEntry {
        val checked = sharedPreferences.getBoolean(number, false)
        return SharedPreferencesEntry(number!!, checked)
    }
}