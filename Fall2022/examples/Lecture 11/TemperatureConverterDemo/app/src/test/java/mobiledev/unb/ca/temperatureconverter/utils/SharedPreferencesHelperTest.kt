package mobiledev.unb.ca.temperatureconverter.utils

import org.mockito.Mock
import android.content.SharedPreferences
import org.hamcrest.MatcherAssert
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.ArgumentMatchers
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SharedPreferencesHelperTest {
    private var sharedPreferencesEntry: SharedPreferencesEntry? = null

    @Mock
    var mockSharedPreferences: SharedPreferences? = null

    @Mock
    var mockBrokenSharedPreferences: SharedPreferences? = null

    @Mock
    var mockEditor: SharedPreferences.Editor? = null

    @Mock
    var mockBrokenEditor: SharedPreferences.Editor? = null

    /**
     * Method that is run prior to every test
     */
    @Before
    fun initMocks() {
        // Create SharedPreferencesEntry for the System Under Test (SUT)
        sharedPreferencesEntry = SharedPreferencesEntry(TEST_CELSIUS, TEST_FAHRENHEIT)
    }

    @Test
    fun sharedPreferencesHelper_WhenSaveLastConversionSuccessful() {
        // Setup the SUT
        val mockSharedPreferencesHelper = createMockSharedPreference()

        // Save the personal information to SharedPreferences
        val success = mockSharedPreferencesHelper.saveLastConversion(sharedPreferencesEntry!!)
        MatcherAssert.assertThat("SharedPreferenceEntry.save returns true",
            success,
            CoreMatchers.`is`(true))

        // Read the saved details from SharedPreferences
        val savedSharedPreferenceEntry = mockSharedPreferencesHelper.lastConversion

        // Make sure both written and retrieved information are equal
        MatcherAssert.assertThat("Verify celsius value has been persisted and read correctly",
            sharedPreferencesEntry!!.celsius,
            CoreMatchers.`is`(CoreMatchers.equalTo(savedSharedPreferenceEntry.celsius)))
        MatcherAssert.assertThat("Checking fahrenheit value has been persisted and read correctly",
            sharedPreferencesEntry!!.fahrenheit,
            CoreMatchers.`is`(CoreMatchers.equalTo(savedSharedPreferenceEntry.fahrenheit)))
    }

    @Test
    fun sharedPreferencesHelper_WhenSaveLastConversionFails() {
        // Setup the SUT
        val mockBrokenSharedPreferencesHelper = createBrokenMockSharedPreference()

        // Read personal information from a broken SharedPreferencesHelper
        val success = mockBrokenSharedPreferencesHelper.saveLastConversion(sharedPreferencesEntry!!)
        MatcherAssert.assertThat("Should return false upon writing",
            success,
            CoreMatchers.`is`(false))
    }

    /**
     * Create a mocked SharedPreferences
     */
    private fun createMockSharedPreference(): SharedPreferencesHelper {
        // Mocked SharedPreferences object
        Mockito.`when`(mockSharedPreferences!!.getFloat(ArgumentMatchers.eq(SharedPreferencesHelper.KEY_CELSIUS),
            ArgumentMatchers.anyFloat())).thenReturn(sharedPreferencesEntry!!.celsius)
        Mockito.`when`(mockSharedPreferences!!.getFloat(ArgumentMatchers.eq(SharedPreferencesHelper.KEY_FAHRENHEIT),
            ArgumentMatchers.anyFloat())).thenReturn(sharedPreferencesEntry!!.fahrenheit)

        // Return the MockEditor when requesting it.
        Mockito.`when`(mockEditor!!.commit()).thenReturn(true)
        Mockito.`when`(mockSharedPreferences!!.edit()).thenReturn(mockEditor)
        return SharedPreferencesHelper(mockSharedPreferences!!)
    }

    /**
     * Create a mocked SharedPreferences object that fails when writing
     */
    private fun createBrokenMockSharedPreference(): SharedPreferencesHelper {
        // Mock a failure on commit
        Mockito.`when`(mockBrokenEditor!!.commit()).thenReturn(false)

        // Returns an instance of the MockEditor when requested
        Mockito.`when`(mockBrokenSharedPreferences!!.edit()).thenReturn(mockBrokenEditor)
        return SharedPreferencesHelper(mockBrokenSharedPreferences!!)
    }

    companion object {
        private const val TEST_CELSIUS = 4.44f
        private const val TEST_FAHRENHEIT = 40.0f
    }
}