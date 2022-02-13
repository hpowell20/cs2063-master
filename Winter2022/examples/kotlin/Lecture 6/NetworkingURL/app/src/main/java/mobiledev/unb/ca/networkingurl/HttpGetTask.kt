package mobiledev.unb.ca.networkingurl

import android.os.AsyncTask
import android.util.Log
import java.io.*
import java.lang.StringBuilder
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

internal class HttpGetTask(retainedFragment: RetainedFragment?) :
    AsyncTask<Void?, Void?, String?>() {
    private val mListener: WeakReference<RetainedFragment?> = WeakReference(retainedFragment)

    override fun doInBackground(vararg params: Void?): String? {
        var httpUrlConnection: HttpURLConnection? = null
        try {
            // 1. Get connection. 2. Prepare request (URI)
            httpUrlConnection = URL(URL).openConnection() as HttpURLConnection

            // 3. This app does not use a request body
            // 4. Read the response
            val `in`: InputStream = BufferedInputStream(httpUrlConnection.inputStream)
            return readStream(`in`)
        } catch (exception: MalformedURLException) {
            Log.e(TAG, "MalformedURLException")
        } catch (exception: IOException) {
            Log.e(TAG, "IOException")
        } finally {
            httpUrlConnection?.disconnect()
        }
        return null
    }

    override fun onPostExecute(result: String?) {
        if (null != mListener.get()) {
            mListener.get()!!.onDownloadFinished(result)
        }
    }

    private fun readStream(`in`: InputStream): String {
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