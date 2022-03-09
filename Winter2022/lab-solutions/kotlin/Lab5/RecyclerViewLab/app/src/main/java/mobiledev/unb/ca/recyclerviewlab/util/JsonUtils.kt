package mobiledev.unb.ca.recyclerviewlab.util

import android.content.Context
import mobiledev.unb.ca.recyclerviewlab.model.Course
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.ArrayList

class JsonUtils(context: Context) {
    // Getter method for courses ArrayList
    private var courses: ArrayList<Course>? = null

    private fun processJSON(context: Context) {
        courses = ArrayList()
        try {
            // Create a JSON Object from file contents String
            val jsonObject = JSONObject(loadJSONFromAssets(context).toString())
            
            // Create a JSON Array from the JSON Object
            // This array is the "courses" array mentioned in the lab write-up
            val jsonArray = jsonObject.getJSONArray(KEY_COURSES)
            for (i in 0 until jsonArray.length()) {
                // TODO 1:
                //  Using the JSON array update coursesArray
                //  1. Retrieve the current object by index
                //  2. Add new Course to courses ArrayList

                // Create a JSON Object from individual JSON Array element
                val elementObject = jsonArray.getJSONObject(i)

                // Get data from individual JSON Object
                val course = Course.Builder()
                    .id(elementObject.getString(KEY_COURSE_ID))
                    .name(elementObject.getString(KEY_NAME))
                    .description(elementObject.getString(KEY_DESCRIPTION))
                    .build()

                // Add new Course to courses ArrayList
                courses!!.add(course)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun loadJSONFromAssets(context: Context): String? {
        // TODO 2:
        //  1. Obtain an instance of the AssetManager class from the referenced context
        //    (https://developer.android.com/reference/android/content/Context#getAssets())
        //  2. Open the CS_JSON_FILE from the assets folder
        //     (https://developer.android.com/reference/android/content/res/AssetManager)
        //  3. Process the file using an InputStream
        try {
            context.assets.open(CS_JSON_FILE).use { `is` ->
                val buffer = ByteArray(`is`.available())
                `is`.read(buffer)
                return String(buffer, StandardCharsets.UTF_8)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }

    // Getter method for courses ArrayList
    fun getCourses(): ArrayList<Course>? {
        return courses
    }

    companion object {
        private const val CS_JSON_FILE = "CS.json"
        private const val KEY_COURSES = "courses"
        private const val KEY_COURSE_ID = "courseID"
        private const val KEY_NAME = "name"
        private const val KEY_DESCRIPTION = "description"
    }

    // Initializer to read our data source (JSON file) into an array of course objects
    init {
        processJSON(context)
    }
}