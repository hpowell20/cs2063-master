package mobiledev.unb.ca.recyclerviewlab.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import mobiledev.unb.ca.recyclerviewlab.model.Course;

public class JsonUtils {

    private static final String CS_JSON_FILE = "CS.json";

    private static final String KEY_COURSES = "courses";
    private static final String KEY_COURSE_ID = "courseID";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";

    private ArrayList<Course> coursesArray;

    // Initializer to read our data source (JSON file) into an array of course objects
    public JsonUtils(Context context) {
        processJSON(context);
    }

    private void processJSON(Context context) {
        coursesArray = new ArrayList<>();

        try {
            // Create a JSON Object from file contents String
            JSONObject jsonObject = new JSONObject(loadJSONFromAssets(context));

            // Create a JSON Array from the JSON Object
            // This array is the "courses" array mentioned in the lab write-up
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_COURSES);

            for (int i=0; i < jsonArray.length(); i++) {
                // Create a JSON Object from individual JSON Array element
                JSONObject elementObject = jsonArray.getJSONObject(i);

                // Get data from individual JSON Object
                Course course = new Course.CourseBuilder()
                        .id(elementObject.getString(KEY_COURSE_ID))
                        .name(elementObject.getString(KEY_NAME))
                        .description(elementObject.getString(KEY_DESCRIPTION))
                        .build();

                // Add new Course to courses ArrayList
                coursesArray.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String loadJSONFromAssets(Context context) {
        try {
            InputStream is = context.getAssets().open(CS_JSON_FILE);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /*private void getJSON() {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();

        try {
            br = new BufferedReader(new InputStreamReader(assetManager.open(CS_JSON_FILE)));
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        processJSON(sb);
    }

    /*private void processJSON(StringBuffer sb) {
        String jsonString = sb.toString();

        // Finally, parse resulting JSON object
        try {

            // Create a JSON Object from file contents String
            JSONObject jsonObject = new JSONObject(jsonString);

            // Create a JSON Array from the JSON Object
            // This array is the "courses" array mentioned in the lab write-up
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_COURSES);

            for (int i=0; i < jsonArray.length(); i++) {

                // Create a JSON Object from individual JSON Array element
                JSONObject elementObject = jsonArray.getJSONObject(i);

                // Get data from individual JSON Object
                String id = elementObject.getString("courseID");
                String name = elementObject.getString("name");
                String desc = elementObject.getString("description");

                // Create a Course Object to contain JSON Object data
                Course course = new Course(id, name, desc);

                // Add new Course to courses ArrayList
                coursesArray.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    // Getter method for courses ArrayList
    public ArrayList<Course> getCourses() {
        return coursesArray;
    }
}
