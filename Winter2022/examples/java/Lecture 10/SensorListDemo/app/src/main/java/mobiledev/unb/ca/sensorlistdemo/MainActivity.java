package mobiledev.unb.ca.sensorlistdemo;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView sensorListText = findViewById(R.id.sensors_list);

        // Retrieve a list of the supported sensors on the devi ce
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor currSensor : sensorList) {
            sensorListText.append(currSensor.getName() + "\n");
        }

        sensorListText.setVisibility(View.VISIBLE);
    }
}
