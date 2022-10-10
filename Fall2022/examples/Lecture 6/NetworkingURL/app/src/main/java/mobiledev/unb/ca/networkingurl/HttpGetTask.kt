package mobiledev.unb.ca.networkingurl

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executors

internal class HttpGetTask(retainedFragment: RetainedFragment?) {
    private var listenerFragment: RetainedFragment? = null

    init {
        this.listenerFragment = retainedFragment
    }

    fun execute() {
        // Perform background call to read the information from the URL
        Executors.newSingleThreadExecutor().execute {
            val handler = Handler(Looper.getMainLooper())
            val jsonString: String? = loadJsonFromUrl()
            handler.post { updateDisplay(jsonString) }
        }
    }

    private fun updateDisplay(jsonString: String?) {
        listenerFragment?.onDownloadFinished(jsonString)
    }

    private fun loadJsonFromUrl(): String? {
        var httpUrlConnection: HttpURLConnection? = null
        return try {
            // 1. Get connection. 2. Prepare request (URI)
            httpUrlConnection = URL(URL).openConnection() as HttpURLConnection

            // 3. This app does not use a request body.  4. Read the response
            val `in`: InputStream = BufferedInputStream(httpUrlConnection.inputStream)
            convertStreamToString(`in`)
        } catch (exception: MalformedURLException) {
            Log.e(TAG, "MalformedURLException in loadJsonFromUrl; error: " + exception.localizedMessage)
            null
        } catch (exception: IOException) {
            Log.e(TAG, "IOException in loadJsonFromUrl; error: " + exception.localizedMessage)
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            httpUrlConnection?.disconnect()
        }
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
            Log.e(TAG, "IOException in convertStreamToString; error: " + e.localizedMessage)
        }
        return data.toString()
    }

    companion object {
        private const val TAG = "HttpGetTask"

        // Get your own user name at http://www.geonames.org/login
        private const val USER_NAME = "aporter"
        private const val HOST = "api.geonames.org"
        private const val URL =
            ("http://" + HOST + "/earthquakesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&username="
                    + USER_NAME)
    }
}