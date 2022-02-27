package mobiledev.unb.ca.threadinglab.util

import android.util.Log
import mobiledev.unb.ca.threadinglab.model.GeoData
import javax.json.stream.JsonParser
import javax.json.Json
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList

class JsonUtils {
    // Getter method for courses ArrayList
    private var geoDataArray: ArrayList<GeoData>? = null

    private fun processJSON() {
        geoDataArray = ArrayList()
        val jsonString = loadJSONFromURL()
        try {
            val parser = Json.createParser(StringReader(jsonString))
            var titleTrigger = false
            var coordinateTrigger = false
            var count = 0
            var coordinateCount = 0

            while (parser.hasNext()) {
                when (parser.next()) {
                    JsonParser.Event.KEY_NAME -> if (parser.string == JSON_KEY_TITLE) {
                        titleTrigger = true
                    } else if (parser.string == JSON_KEY_COORDINATES) {
                        coordinateTrigger = true
                    }
                    JsonParser.Event.VALUE_STRING -> if (titleTrigger && parser.string.startsWith("M")) {
                        val geoData = GeoData()
                        geoData.title = parser.string
                        geoDataArray!!.add(geoData)
                        titleTrigger = false
                    }
                    JsonParser.Event.VALUE_NUMBER -> {
                        if (coordinateTrigger && coordinateCount == 0) {
                            val geoData = geoDataArray!![count]
                            geoData.longitude = parser.string
                            coordinateCount++
                        } else if (!coordinateTrigger && coordinateCount == 1) {
                            val geoData = geoDataArray!![count]
                            geoData.latitude = parser.string
                            coordinateCount = 0
                            count++
                        }
                        coordinateTrigger = false
                    }
                    else -> {}
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadJSONFromURL(): String? {
        var connection: HttpURLConnection? = null
        try {
            // TODO
            //  Establish an HttpURLConnection to REQUEST_URL (defined as a constant)
            //  Hint: See https://github.com/hpowell20/cs2063-winter-2022-examples/tree/master/Lecture6/NetworkingURL
            //  for an example of how to do this
            //  Also see documentation here:
            //  http://developer.android.com/training/basics/network-ops/connecting.html
            connection = URL(REQUEST_URL).openConnection() as HttpURLConnection
            val `in`: InputStream = BufferedInputStream(connection.inputStream)
            return convertStreamToString(`in`)
        } catch (exception: MalformedURLException) {
            Log.e(TAG, "MalformedURLException")
        } catch (exception: IOException) {
            Log.e(TAG, "IOException")
        } finally {
            connection?.disconnect()
        }
        return null
    }

    private fun convertStreamToString(`in`: InputStream): String {
        val data = StringBuilder()
        try {
            BufferedReader(InputStreamReader(`in`)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    data.append(line)
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "IOException")
        }
        return data.toString()
    }

    fun getGeoData(): ArrayList<GeoData>? {
        return geoDataArray
    }

    companion object {
        private const val TAG = "JsonUtils"
        private const val REQUEST_URL =
            "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson"
        private const val JSON_KEY_TITLE = "title"
        private const val JSON_KEY_COORDINATES = "coordinates"
    }

    // Initializer to read our data source (JSON file) into an array of course objects
    init {
        processJSON()
    }
}