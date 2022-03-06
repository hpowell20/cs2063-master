package mobiledev.unb.ca.labexam.helper;

import android.content.SharedPreferences;

public class SharedPreferencesHelper {
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
    public boolean saveCheckedState(SharedPreferencesEntry sharedPreferencesEntry) {
        // TODO: SharedPreferences
        //  Get a SharedPreferences.Editor for SharedPreferences
        //  Hint: https://developer.android.com/reference/android/content/SharedPreferences.html#edit()
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // TODO: Shared Preferences
        //  Set the value stored in SharedPreferences for the id for this GamesInfo to be
        //  the value of isChecked
        //  Hint: https://developer.android.com/reference/android/content/SharedPreferences.Editor.html#putBoolean(java.lang.String,%20boolean)
        editor.putBoolean(sharedPreferencesEntry.getNumber(), sharedPreferencesEntry.getChecked());

        // TODO: SharedPreferences
        //  Apply the changes from this editor
        //  Hint: https://developer.android.com/reference/android/content/SharedPreferences.Editor.html#commit()
        return editor.commit();
    }

    /**
     * Retrieves the latest stored values
     *
     * @return the stored values {@link SharedPreferencesEntry}.
     */
    public SharedPreferencesEntry readCheckedState(String number) {
        boolean checked = sharedPreferences.getBoolean(number, false);
        return new SharedPreferencesEntry(number, checked);
    }
}