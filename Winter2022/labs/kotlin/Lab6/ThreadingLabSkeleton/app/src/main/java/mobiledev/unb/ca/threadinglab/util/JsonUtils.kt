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
import java.util.ArrayList

class JsonUtils {
    // Getter method for courses ArrayList
    var geoDataArray: ArrayList<GeoData>? = null
        private set

    private fun processJSON() {
        geoDataArray = ArrayList()
        val jsonString = loadJSONFromURL()
        try {
            val parser = Json.createParser(StringReader(jsonString))
            var titleTrigger = false
            var coordTrigger = false
            var count = 0
            var coordCount = 0

            while (parser.hasNext()) {
                val event = parser.next()
                when (event) {
                    JsonParser.Event.KEY_NAME -> if (parser.string == JSON_KEY_TITLE) {
                        titleTrigger = true
                    } else if (parser.string == JSON_KEY_COORDINATES) {
                        coordTrigger = true
                    }
                    JsonParser.Event.VALUE_STRING -> if (titleTrigger && parser.string.startsWith("M")) {
                        val geoData = GeoData()
                        geoData.title = parser.string
                        geoDataArray!!.add(geoData)
                        titleTrigger = false
                    }
                    JsonParser.Event.VALUE_NUMBER -> {
                        if (coordTrigger && coordCount == 0) {
                            val geoData = geoDataArray!![count]
                            geoData.longitude = parser.string
                            coordCount++
                        } else if (!coordTrigger && coordCount == 1) {
                            val geoData = geoDataArray!![count]
                            geoData.latitude = parser.string
                            coordCount = 0
                            count++
                        }
                        coordTrigger = false
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadJSONFromURL(): String? {
        val connection: HttpURLConnection? = null
        return try {
            // TODO
            //  Establish an HttpURLConnection to REQUEST_URL (defined as a constant)
            //  Hint: See https://github.com/hpowell20/cs2063-winter-2022-examples/tree/master/Lecture6/NetworkingURL
            //  for an example of how to do this
            //  Also see documentation here:
            //  http://developer.android.com/training/basics/network-ops/connecting.html
            convertStreamToString(connection!!.inputStream)
        } catch (exception: MalformedURLException) {
            Log.e(TAG, "MalformedURLException")
            null
        } catch (exception: IOException) {
            Log.e(TAG, "IOException")
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            connection?.disconnect()
        }
    }

    private fun convertStreamToString(`is`: InputStream): String {
        var reader: BufferedReader? = null
        val sb = StringBuilder()
        try {
            reader = BufferedReader(InputStreamReader(`is`))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return sb.toString()
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