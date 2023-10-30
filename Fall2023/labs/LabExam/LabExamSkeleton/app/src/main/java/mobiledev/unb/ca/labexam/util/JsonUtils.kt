package mobiledev.unb.ca.labexam.util

import android.content.Context
import mobiledev.unb.ca.labexam.model.EventInfo
import org.json.JSONObject
import org.json.JSONException
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.ArrayList

class JsonUtils(context: Context) {
    private lateinit var hostNations: ArrayList<EventInfo>

    private fun processJSON(context: Context) {
        hostNations = ArrayList()
        try {
            // Create a JSON Object from file contents String
            val jsonObject = JSONObject(loadJSONFromAssets(context).toString())

            // Create a JSON Array from the JSON Object
            val jsonArray = jsonObject.getJSONArray(KEY_HOST_NATIONS)
            for (i in 0 until jsonArray.length()) {
                // Create a JSON Object from individual JSON Array element
                val elementObject = jsonArray.getJSONObject(i)

                // Get data from individual JSON Object
                val nation = EventInfo.Builder(elementObject.getString(KEY_YEAR),
                    elementObject.getString(KEY_NUMBER),
                    elementObject.getString(KEY_HOST_NATION),
                    elementObject.getString(KEY_DATES),
                    elementObject.getString(KEY_WIKIPEDIA_LINK))
                    .build()

                // Add new EventInfo object to hostNations ArrayList
                hostNations.add(nation)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun getHostNations(): ArrayList<EventInfo> {
        return hostNations
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
        private const val INPUT_JSON_FILE = "world_cups.json"
        private const val KEY_HOST_NATIONS = "host_nations"
        private const val KEY_YEAR = "year"
        private const val KEY_NUMBER = "number"
        private const val KEY_HOST_NATION = "host_nation"
        private const val KEY_DATES = "dates"
        private const val KEY_WIKIPEDIA_LINK = "wikipedia_link"
    }

    // Initializer to read our data source (JSON file) into an array of host city objects
    init {
        processJSON(context)
    }
}