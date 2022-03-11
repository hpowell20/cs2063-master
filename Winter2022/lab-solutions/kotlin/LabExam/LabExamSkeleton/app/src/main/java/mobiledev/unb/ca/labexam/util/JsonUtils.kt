package mobiledev.unb.ca.labexam.util

import android.content.Context
import mobiledev.unb.ca.labexam.model.GamesInfo
import org.json.JSONObject
import org.json.JSONException
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.ArrayList

class JsonUtils(context: Context) {
    var hostCities: ArrayList<GamesInfo>? = null
        private set

    private fun processJSON(context: Context) {
        hostCities = ArrayList()
        try {
            // Create a JSON Object from file contents String
            val jsonObject = JSONObject(loadJSONFromAssets(context).toString())

            // This array is the "gamesinfo" array mentioned in the lab write-up
            val jsonArray = jsonObject.getJSONArray(KEY_HOST_CITIES)
            for (i in 0 until jsonArray.length()) {
                // Create a JSON Object from individual JSON Array element
                val elementObject = jsonArray.getJSONObject(i)

                // Get data from individual JSON Object
                val city = GamesInfo.Builder(elementObject.getString(KEY_YEAR),
                    elementObject.getString(KEY_NUMBER),
                    elementObject.getString(KEY_HOST_CITY),
                    elementObject.getString(KEY_DATES),
                    elementObject.getString(KEY_WIKIPEDIA_LINK))
                    .build()

                // Add new GamesInfo to courses ArrayList
                hostCities!!.add(city)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun loadJSONFromAssets(context: Context): String? {
        try {
            context.assets.open(INPUT_JSON_FILE).use { `is` ->
                val buffer = ByteArray(`is`.available())
                `is`.read(buffer)
                return String(buffer, StandardCharsets.UTF_8)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }

    companion object {
        private const val INPUT_JSON_FILE = "winter_games.json"
        private const val KEY_HOST_CITIES = "host_cities"
        private const val KEY_YEAR = "year"
        private const val KEY_NUMBER = "number"
        private const val KEY_HOST_CITY = "host_city"
        private const val KEY_DATES = "dates"
        private const val KEY_WIKIPEDIA_LINK = "wikipedia_link"
    }

    // Initializer to read our data source (JSON file) into an array of host city objects
    init {
        processJSON(context)
    }
}