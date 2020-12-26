package mobiledev.unb.ca.temperatureconverter.utils;

import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    // Keys for saving values in SharedPreferences.
    protected static final String KEY_CELSIUS = "key_celsius";
    protected static final String KEY_FAHRENHEIT = "key_fahrenheit";

    // The injected SharedPreferences implementation to use for persistence
    private final SharedPreferences sharedPreferences;

    /**
     * Constructor with dependency injection.
     * @param sharedPreferences The {@link SharedPreferences} that will be used
     */
    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    /**
     * Saves the given values
     *
     * @param sharedPreferencesEntry contains data to save to {@link SharedPreferences}.
     * @return {@code true} if writing to {@link SharedPreferences} succeeded. {@code false}
     *         otherwise.
     */
    public boolean saveLastConversion(SharedPreferencesEntry sharedPreferencesEntry) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(KEY_CELSIUS, sharedPreferencesEntry.getCelsius());
        editor.putFloat(KEY_FAHRENHEIT, sharedPreferencesEntry.getFahrenheit());

        return editor.commit();
    }

    /**
     * Retrieves the latest stored values
     *
     * @return the stored values {@link SharedPreferencesEntry}.
     */
    public SharedPreferencesEntry getLastConversion() {
        // Get data from the SharedPreferences
        float celsius = sharedPreferences.getFloat(KEY_CELSIUS, 0.0f);
        float fahrenheit = sharedPreferences.getFloat(KEY_FAHRENHEIT, 0.0f);

        return new SharedPreferencesEntry(celsius, fahrenheit);
    }
}