package mobiledev.unb.ca.threadinglab.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import javax.json.Json;
import javax.json.stream.JsonParser;

import mobiledev.unb.ca.threadinglab.model.GeoData;

public class JsonUtils {
    private static final String TAG = "JsonUtils";
    private static final String REQUEST_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson";
    private static final String JSON_KEY_TITLE = "title";
    private static final String JSON_KEY_COORDINATES = "coordinates";

    private ArrayList<GeoData> geoDataArray;

    // Initializer to read our data source (JSON file) into an array of course objects
    public JsonUtils() {
        processJSON();
    }

    private void processJSON() {
        geoDataArray = new ArrayList<>();

        // Create a JSON Object from file contents String
        try {
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(loadJSONFromURL()));
            Iterator<String> keys= jsonObject.keys();
            while (keys.hasNext())
            {
                String keyValue = keys.next();
                String valueString = jsonObject.getString(keyValue);
                Log.w(TAG, valueString);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonString = loadJSONFromURL();

        try {
            JsonParser parser = Json.createParser(new StringReader(jsonString));
            boolean titleTrigger = false;
            boolean coordinateTrigger = false;
            int count = 0;
            int coordinateCount = 0;

            while(parser.hasNext()) {
                JsonParser.Event event = parser.next();
                switch (event) {
                    case KEY_NAME:
                        if(parser.getString().equals(JSON_KEY_TITLE)) {
                            titleTrigger = true;
                        } else if (parser.getString().equals(JSON_KEY_COORDINATES)) {
                            coordinateTrigger = true;
                        }
                        break;
                    case VALUE_STRING:
                        if(titleTrigger && parser.getString().startsWith("M")) {
                            GeoData geoData = new GeoData();
                            geoData.setTitle(parser.getString());
                            geoDataArray.add(geoData);
                            titleTrigger = false;
                        }
                        break;
                    case VALUE_NUMBER:
                        if(coordinateTrigger && (coordinateCount == 0)) {
                            GeoData geoData = geoDataArray.get(count);
                            geoData.setLongitude(parser.getString());
                            coordinateCount++;
                        } else if(!coordinateTrigger && (coordinateCount == 1)) {
                            GeoData geoData = geoDataArray.get(count);
                            geoData.setLatitude(parser.getString());
                            coordinateCount = 0;
                            count++;
                        }
                        coordinateTrigger = false;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String loadJSONFromURL() {
        HttpURLConnection connection = null;

        try {
            // TODO
            //  Establish an HttpURLConnection to REQUEST_URL (defined as a constant)
            //  Hint: See https://github.com/hpowell20/cs2063-winter-2022-examples/tree/master/Lecture6/NetworkingURL
            //  for an example of how to do this
            //  Also see documentation here:
            //  http://developer.android.com/training/basics/network-ops/connecting.html
            connection = (HttpURLConnection) new URL(REQUEST_URL).openConnection();
            return convertStreamToString(connection.getInputStream());
        } catch (MalformedURLException exception) {
            Log.e(TAG, "MalformedURLException");
            return null;
        } catch (IOException exception) {
            Log.e(TAG, "IOException");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != connection)
                connection.disconnect();
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    // Getter method for courses ArrayList
    public ArrayList<GeoData> getGeoData() {
        return geoDataArray;
    }
}
