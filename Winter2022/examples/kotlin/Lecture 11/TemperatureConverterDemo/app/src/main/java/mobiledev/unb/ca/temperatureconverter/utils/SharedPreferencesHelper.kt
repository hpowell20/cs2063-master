package mobiledev.unb.ca.temperatureconverter.utils

import android.content.SharedPreferences

class SharedPreferencesHelper
/**
 * Constructor with dependency injection.
 * @param sharedPreferences The [SharedPreferences] that will be used
 */(
    // The injected SharedPreferences implementation to use for persistence
    private val sharedPreferences: SharedPreferences
) {
    /**
     * Saves the given values
     *
     * @param sharedPreferencesEntry contains data to save to [SharedPreferences].
     * @return `true` if writing to [SharedPreferences] succeeded. `false`
     * otherwise.
     */
    fun saveLastConversion(sharedPreferencesEntry: SharedPreferencesEntry): Boolean {
        val editor = sharedPreferences.edit()
        editor.putFloat(KEY_CELSIUS, sharedPreferencesEntry.celsius)
        editor.putFloat(KEY_FAHRENHEIT, sharedPreferencesEntry.fahrenheit)
        return editor.commit()
    }

    /**
     * Retrieves the latest stored values
     *
     * @return the stored values [SharedPreferencesEntry].
     */
    val lastConversion: SharedPreferencesEntry
        get() {
            // Get data from the SharedPreferences
            val celsius = sharedPreferences.getFloat(KEY_CELSIUS, 0.0f)
            val fahrenheit = sharedPreferences.getFloat(KEY_FAHRENHEIT, 0.0f)
            return SharedPreferencesEntry(celsius, fahrenheit)
        }

    companion object {
        // Keys for saving values in SharedPreferences.
        const val KEY_CELSIUS = "key_celsius"
        const val KEY_FAHRENHEIT = "key_fahrenheit"
    }
}