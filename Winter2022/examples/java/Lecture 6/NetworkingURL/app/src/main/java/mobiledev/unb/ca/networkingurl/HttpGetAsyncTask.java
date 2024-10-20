package mobiledev.unb.ca.networkingurl;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class HttpGetAsyncTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = "HttpGetTask";

    // Get your own user name at http://www.geonames.org/login
    private static final String USER_NAME = "aporter";

    private static final String HOST = "api.geonames.org";
    private static final String URL = "http://" + HOST + "/earthquakesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&username="
            + USER_NAME;

    private final WeakReference<RetainedFragment> mListener;

    HttpGetAsyncTask(RetainedFragment retainedFragment) {
        mListener = new WeakReference<>(retainedFragment);
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpURLConnection httpUrlConnection = null;

        try {
            // 1. Get connection. 2. Prepare request (URI)
            httpUrlConnection = (HttpURLConnection) new URL(URL).openConnection();

            // 3. This app does not use a request body
            // 4. Read the response
            InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());

            return readStream(in);
        } catch (MalformedURLException exception) {
            Log.e(TAG, "MalformedURLException");
        } catch (IOException exception) {
            Log.e(TAG, "IOException");
        } finally {
            if (null != httpUrlConnection) {
                // 5. Disconnect
                httpUrlConnection.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (null != mListener.get()) {
            mListener.get().onDownloadFinished(result);
        }
    }

    private String readStream(InputStream in) {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException");
        }

        return data.toString();
    }
}