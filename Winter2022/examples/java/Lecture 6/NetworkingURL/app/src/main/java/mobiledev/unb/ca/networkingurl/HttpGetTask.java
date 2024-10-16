package mobiledev.unb.ca.networkingurl;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;

public class HttpGetTask {
    private static final String TAG = "HttpGetTask";

    // Get your own user name at http://www.geonames.org/login
    private static final String USER_NAME = "aporter";

    private static final String HOST = "api.geonames.org";
    private static final String URL = "http://" + HOST + "/earthquakesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&username="
            + USER_NAME;

    private final RetainedFragment listenerFragment;

    public HttpGetTask(RetainedFragment listenerFragment) {
        this.listenerFragment = listenerFragment;
    }

    public void execute() {
        // Perform background call to read the information from the URL
        Executors.newSingleThreadExecutor().execute(() -> {
            Handler handler = new Handler(Looper.getMainLooper());
            String jsonString = loadJsonFromUrl();

            handler.post(() -> updateDisplay(jsonString));
        });
    }

    private void updateDisplay(String jsonString) {
        listenerFragment.onDownloadFinished(jsonString);
    }

    private String loadJsonFromUrl() {
        HttpURLConnection httpUrlConnection = null;

        try {
            // Get the connection and prepare the request
            httpUrlConnection = (HttpURLConnection) new URL(URL).openConnection();

            // Process the response
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
