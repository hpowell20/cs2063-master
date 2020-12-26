package mobiledev.unb.ca.temperatureconverter.utils;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SharedPreferencesHelperTest {

    private static final float TEST_CELSIUS = 4.44f;
    private static final float TEST_FAHRENHEIT = 40.0f;

    private SharedPreferencesEntry sharedPreferencesEntry;
    private SharedPreferencesHelper mockSharedPreferencesHelper;
    private SharedPreferencesHelper mockBrokenSharedPreferencesHelper;

    @Mock
    SharedPreferences mockSharedPreferences;

    @Mock
    SharedPreferences mockBrokenSharedPreferences;

    @Mock
    SharedPreferences.Editor mockEditor;

    @Mock
    SharedPreferences.Editor mockBrokenEditor;

    @Before
    /**
     * Method that is run prior to every test
     */
    public void initMocks() {
        // Create SharedPreferencesEntry for the System Under Test (SUT)
        sharedPreferencesEntry = new SharedPreferencesEntry(TEST_CELSIUS, TEST_FAHRENHEIT);
    }

    @Test
    public void sharedPreferencesHelper_WhenSaveLastConversionSuccessful() {
        // Setup the SUT
        mockSharedPreferencesHelper = createMockSharedPreference();

        // Save the personal information to SharedPreferences
        boolean success = mockSharedPreferencesHelper.saveLastConversion(sharedPreferencesEntry);

        assertThat("SharedPreferenceEntry.save returns true", success, is(true));

        // Read the saved details from SharedPreferences
        SharedPreferencesEntry savedSharedPreferenceEntry = mockSharedPreferencesHelper.getLastConversion();

        // Make sure both written and retrieved information are equal
        assertThat("Verify celsius value has been persisted and read correctly",
                sharedPreferencesEntry.getCelsius(),
                is(equalTo(savedSharedPreferenceEntry.getCelsius())));
        assertThat("Checking fahrenheit value has been persisted and read correctly",
                sharedPreferencesEntry.getFahrenheit(),
                is(equalTo(savedSharedPreferenceEntry.getFahrenheit())));
    }

    @Test
    public void sharedPreferencesHelper_WhenSaveLastConversionFails() {
        // Setup the SUT
        mockBrokenSharedPreferencesHelper = createBrokenMockSharedPreference();

        // Read personal information from a broken SharedPreferencesHelper
        boolean success = mockBrokenSharedPreferencesHelper.saveLastConversion(sharedPreferencesEntry);
        assertThat("Should return false upon writing", success, is(false));
    }

    /**
     * Create a mocked SharedPreferences
     */
    private SharedPreferencesHelper createMockSharedPreference() {
        // Mocked SharedPreferences object
        when(mockSharedPreferences.getFloat(eq(SharedPreferencesHelper.KEY_CELSIUS),
                anyFloat())).thenReturn(sharedPreferencesEntry.getCelsius());
        when(mockSharedPreferences.getFloat(eq(SharedPreferencesHelper.KEY_FAHRENHEIT),
                anyFloat())).thenReturn(sharedPreferencesEntry.getFahrenheit());

        // Return the MockEditor when requesting it.
        when(mockEditor.commit()).thenReturn(true);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);

        return new SharedPreferencesHelper(mockSharedPreferences);
    }

    /**
     * Create a mocked SharedPreferences object that fails when writing
     */
    private SharedPreferencesHelper createBrokenMockSharedPreference() {
        // Mock a failure on commit
        when(mockBrokenEditor.commit()).thenReturn(false);

        // Returns an instance of the MockEditor when requested
        when(mockBrokenSharedPreferences.edit()).thenReturn(mockBrokenEditor);

        return new SharedPreferencesHelper(mockBrokenSharedPreferences);
    }
}
