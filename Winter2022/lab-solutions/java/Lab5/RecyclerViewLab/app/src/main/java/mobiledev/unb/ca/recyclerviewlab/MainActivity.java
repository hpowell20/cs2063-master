package mobiledev.unb.ca.recyclerviewlab;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mobiledev.unb.ca.recyclerviewlab.model.Course;
import mobiledev.unb.ca.recyclerviewlab.util.JsonUtils;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO 1
        //  Get the ArrayList of Courses from the JsonUtils class
        //  (Ideally we would do this loading off of the main thread. We'll get to that
        //  in the next lab. Today we're focusing on displaying scrolling lists.)
        JsonUtils jsonUtils = new JsonUtils(getApplicationContext());
        ArrayList<Course> courses = jsonUtils.getCourses();

        // TODO 2
        //  Get a reference to the RecyclerView and set its adapter
        //  to be an instance of MyAdapter, which you will need to create
        //  using the ArrayList of courses from above.
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        MyAdapter myAdapter = new MyAdapter(courses, this);
        recyclerView.setAdapter(myAdapter);
    }
}
