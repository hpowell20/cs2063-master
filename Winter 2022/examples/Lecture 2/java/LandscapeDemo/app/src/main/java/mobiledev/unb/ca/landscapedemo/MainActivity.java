package mobiledev.unb.ca.landscapedemo;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MainActivity extends AppCompatActivity {

    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        resources = getResources();

        button.setOnClickListener(src -> {
            Context context = getApplicationContext();

            if (resources.getConfiguration().orientation == ORIENTATION_PORTRAIT) {
                Toast.makeText(context,
                          "Running in Portrait Mode",
                               Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context,
                          "Running in Landscape Mode",
                               Toast.LENGTH_SHORT).show();
            }
        });
    }
}
